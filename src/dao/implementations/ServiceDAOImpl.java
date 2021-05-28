package dao.implementations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.forms.ClientSearchForm;
import dao.forms.EmployeeSearchForm;
import dao.forms.ServiceSearchForm;
import dao.interfaces.ServiceDAO;
import objects.Service;
import utils.HibernateSessionFactory;

public class ServiceDAOImpl extends CommonDAOImpl<Service, Integer> implements ServiceDAO {

  public List<Service> findByAll(ServiceSearchForm serviceForm, List<EmployeeSearchForm> employees,
      List<ClientSearchForm> clients) {

    String queryString = "SELECT DISTINCT serv";
    Map<String, Object> parameters = new HashMap<>();

    String serviceString = serviceForm.getQueryString("", parameters, false);
    if (serviceForm == null || serviceString.isEmpty()) {
      queryString += " FROM Service serv";
    } else {
      queryString += serviceString;
    }

    // No parameters means no conditions were created in
    // clientForm.getQueryString(...)
    boolean firstCondition = (parameters.size() == 0);

    if (employees != null) {
      int i = 0;
      for (EmployeeSearchForm employee : employees) {
        String queryStr = employee.getQueryString(String.valueOf(i), parameters, true);
        if (!queryStr.isEmpty()) {
          if (firstCondition) {
            queryString += " WHERE";
            firstCondition = false;
          } else {
            queryString += " AND";
          }
          queryString += " serv in (SELECT task.id.service";
          queryString += queryStr;
          queryString += ")";
          i++;
        }
      }
    }

    if (clients != null) {
      int i = 0;
      for (ClientSearchForm client : clients) {
        String queryStr = client.getQueryString(String.valueOf(i), parameters, false);
        if (!queryStr.isEmpty()) {
          if (firstCondition) {
            queryString += " WHERE";
            firstCondition = false;
          } else {
            queryString += " AND";
          }
          queryString += " serv.client in (SELECT cl";
          queryString += queryStr;
          queryString += ")";
          i++;
        }
      }
    }

    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    Query<Service> query = session.createQuery(queryString, Service.class);
    for (Entry<String, Object> parameter : parameters.entrySet()) {
      query.setParameter(parameter.getKey(), parameter.getValue());
    }
    List<Service> services = query.list();

    transaction.commit();
    session.close();

    return services;
  }
}