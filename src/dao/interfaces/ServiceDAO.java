package dao.interfaces;

import java.util.List;

import dao.forms.ClientSearchForm;
import dao.forms.EmployeeSearchForm;
import dao.forms.ServiceSearchForm;
import objects.Service;

public interface ServiceDAO extends CommonDAO<Service, Integer> {
  public List<Service> findByAll(ServiceSearchForm serviceForm, List<EmployeeSearchForm> employees,
      List<ClientSearchForm> clients);
}
