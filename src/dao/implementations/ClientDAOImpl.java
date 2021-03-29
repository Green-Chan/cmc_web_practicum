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
import dao.interfaces.ClientDAO;
import objects.Client;
import utils.HibernateSessionFactory;

public class ClientDAOImpl extends CommonDAOImpl<Client, Integer> implements ClientDAO {
  public List<Client> findByAll(ClientSearchForm clientForm, List<ServiceSearchForm> services,
      List<EmployeeSearchForm> employees) {

    String queryString = "SELECT DISTINCT cl";
    Map<String, Object> parameters = new HashMap<>();

    if (clientForm == null) {
      queryString += " FROM Client cl";
    } else {
      queryString += clientForm.getQueryString("", parameters, false);
    }

    // No parameters means no conditions were created in
    // clientForm.getQueryString(...)
    boolean firstCondition = (parameters.size() == 0);

    if (services != null) {
      int i = 0;
      for (ServiceSearchForm service : services) {
        if (firstCondition) {
          queryString += " WHERE";
          firstCondition = false;
        } else {
          queryString += " AND";
        }
        queryString += " cl in (SELECT serv.client";
        queryString += service.getQueryString(String.valueOf(i), parameters, false);
        queryString += ")";
        i++;
      }
    }

    if (employees != null) {
      int i = 0;
      for (EmployeeSearchForm employee : employees) {
        if (firstCondition) {
          queryString += " WHERE";
          firstCondition = false;
        } else {
          queryString += " AND";
        }
        queryString += " cl in (SELECT task.id.service.client";
        queryString += employee.getQueryString(String.valueOf(i), parameters, true);
        queryString += ")";
        i++;
      }
    }

    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    Query<Client> query = session.createQuery(queryString, Client.class);
    for (Entry<String, Object> parameter : parameters.entrySet()) {
      query.setParameter(parameter.getKey(), parameter.getValue());
    }
    List<Client> clients = query.list();

    transaction.commit();
    session.close();

    return clients;
  }
}
