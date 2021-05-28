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
import dao.interfaces.EmployeeDAO;
import objects.Employee;
import utils.HibernateSessionFactory;

public class EmployeeDAOImpl extends CommonDAOImpl<Employee, Integer> implements EmployeeDAO {
  public List<Employee> findByAll(EmployeeSearchForm employeeForm, List<ServiceSearchForm> services,
      List<ClientSearchForm> clients) {

    String queryString = "SELECT DISTINCT empl";
    Map<String, Object> parameters = new HashMap<>();

    String employeeString = employeeForm.getQueryString("", parameters, false);
    if (employeeForm == null || employeeString.isEmpty()) {
      queryString += " FROM Employee empl";
    } else {
      queryString += employeeString;
    }

    // No parameters means no conditions were created in
    // clientForm.getQueryString(...)
    boolean firstCondition = (parameters.size() == 0);

    if (services != null) {
      int i = 0;
      for (ServiceSearchForm service : services) {
        String queryStr = service.getQueryString(String.valueOf(i), parameters, true);
        if (!queryStr.isEmpty()) {
          if (firstCondition) {
            queryString += " WHERE";
            firstCondition = false;
          } else {
            queryString += " AND";
          }
          queryString += " empl in (SELECT task.id.employee";
          queryString += queryStr;
          queryString += ")";
          i++;
        }
      }
    }

    if (clients != null) {
      int i = 0;
      for (ClientSearchForm client : clients) {
        String queryStr = client.getQueryString(String.valueOf(i), parameters, true);
        if (!queryStr.isEmpty()) {
          if (firstCondition) {
            queryString += " WHERE";
            firstCondition = false;
          } else {
            queryString += " AND";
          }
          queryString += " empl in (SELECT task.id.employee";
          queryString += queryStr;
          queryString += ")";
          i++;
        }
      }
    }

    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    Query<Employee> query = session.createQuery(queryString, Employee.class);
    for (Entry<String, Object> parameter : parameters.entrySet()) {
      query.setParameter(parameter.getKey(), parameter.getValue());
    }
    List<Employee> employees = query.list();

    transaction.commit();
    session.close();

    return employees;
  }
}
