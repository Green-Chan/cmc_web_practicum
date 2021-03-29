package dao.interfaces;

import java.util.List;

import dao.forms.ClientSearchForm;
import dao.forms.EmployeeSearchForm;
import dao.forms.ServiceSearchForm;
import objects.Employee;

public interface EmployeeDAO extends CommonDAO<Employee, Integer> {
  public List<Employee> findByAll(EmployeeSearchForm employeeForm, List<ServiceSearchForm> services,
      List<ClientSearchForm> clients);
}
