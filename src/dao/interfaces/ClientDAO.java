package dao.interfaces;

import java.util.List;

import dao.forms.ClientSearchForm;
import dao.forms.EmployeeSearchForm;
import dao.forms.ServiceSearchForm;
import objects.Client;

public interface ClientDAO extends CommonDAO<Client, Integer> {
  public List<Client> findByAll(ClientSearchForm clientForm, List<ServiceSearchForm> services,
      List<EmployeeSearchForm> employees);
}
