package tests;

import java.math.BigDecimal;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import dao.forms.ClientSearchForm;
import dao.forms.EmployeeSearchForm;
import dao.forms.ServiceSearchForm;
import dao.implementations.ClientDAOImpl;
import dao.implementations.EmployeeDAOImpl;
import dao.implementations.ServiceDAOImpl;
import dao.implementations.ServiceTypeDAOImpl;
import dao.interfaces.ClientDAO;
import dao.interfaces.EmployeeDAO;
import dao.interfaces.ServiceDAO;
import dao.interfaces.ServiceTypeDAO;
import objects.Client;
import objects.ClientContact;
import objects.ClientContactPerson;
import objects.Employee;
import objects.EmployeeContact;
import objects.Service;
import objects.ServiceType;
import objects.Task;
import types.ClientType;
import types.ContactType;

public class TestClient {

  private ServiceDAO serviceDAO;
  private ClientDAO clientDAO;
  private ServiceTypeDAO serviceTypeDAO;
  private EmployeeDAO employeeDAO;

  public TestClient() {
    this.serviceDAO = new ServiceDAOImpl();
    this.clientDAO = new ClientDAOImpl();
    this.serviceTypeDAO = new ServiceTypeDAOImpl();
    this.employeeDAO = new EmployeeDAOImpl();
  }

  @Test
  public void saveClient() {
    Client client = new Client(ClientType.person, "Masha");

    clientDAO.save(client);
    Client loadedClient = clientDAO.findById(client.getId());

    Assert.assertEquals(client, loadedClient);

    // ----------------//

    client = new Client(ClientType.organization, "Medved");
    ClientContact contact1 = new ClientContact(client, ContactType.email, "info@medved.ru");
    ClientContact contact2 = new ClientContact(client, ContactType.phone, "+7 (495) 230-01-80");
    client.setContacts(List.of(contact1, contact2));
    ClientContactPerson cp = new ClientContactPerson(client, "Masha", "+7 (495) 201-10-47");
    client.setContactPersons(List.of(cp));

    clientDAO.save(client);
    loadedClient = clientDAO.findById(client.getId());
    Assert.assertEquals(client, loadedClient);
  }

  @Test
  public void updateClient() {
    Client client = new Client(ClientType.person, "Masha");
    clientDAO.save(client);

    ClientContact contact = new ClientContact(client, ContactType.phone, "+7 (495) 201-10-47");
    client.setContacts(List.of(contact));

    clientDAO.update(client);

    Client loadedClient = clientDAO.findById(client.getId());
    Assert.assertEquals(client, loadedClient);
  }

  @Test
  public void deleteClient() {
    Client client = new Client(ClientType.person, "deleted client");
    clientDAO.save(client);
    clientDAO.delete(client);
    Client loadedClient = clientDAO.findById(client.getId());
    Assert.assertEquals(loadedClient, null);
  }

  @Test
  public void findTest() {
    Client gbaTestClient = new Client(ClientType.person, "gba client");
    ClientContact contact1 = new ClientContact(gbaTestClient, ContactType.phone, "+74952011047");
    ClientContact contact2 = new ClientContact(gbaTestClient, ContactType.email, "gbaclient@cs.msu.ru");
    gbaTestClient.setContacts(List.of(contact1, contact2));
    ClientContactPerson cp1 = new ClientContactPerson(gbaTestClient, "Contact1", "88005553535");
    ClientContactPerson cp2 = new ClientContactPerson(gbaTestClient, "Contact2", "+78005553535");
    gbaTestClient.setContactPersons(List.of(cp1, cp2));
    clientDAO.save(gbaTestClient);

    // ----- Create service ------ //
    ServiceType testServiceType = new ServiceType("TSTforGBAC", "Test service type for GBA Client test", null);
    serviceTypeDAO.save(testServiceType);
    Service serv = new Service(gbaTestClient, testServiceType, null, null, new BigDecimal("1500.00"));
    gbaTestClient.setServices(List.of(serv));
    serviceDAO.save(serv);
    // ----- //

    // ----- Create employee and task ----- //
    Employee testEmployee = new Employee("test GBAC emp", "test GBAC emp", "test GBAC emp");
    EmployeeContact empContact = new EmployeeContact(testEmployee, ContactType.phone, "test GBAC employee phone");
    testEmployee.setContacts(List.of(empContact));

    Task testTask = new Task(serv, testEmployee, "test description");
    serv.setTasks(List.of(testTask));
    testEmployee.setTasks(List.of(testTask));

    employeeDAO.save(testEmployee);
    // ------ //

    ServiceSearchForm serviceForm = new ServiceSearchForm(null, "GBA C", null, null, null, null,
        new BigDecimal("1000.00"), null);
    ClientSearchForm clientForm = new ClientSearchForm(gbaTestClient.getType(), gbaTestClient.getName(),
        contact2.getContactType(), contact2.getContact(), cp1.getName(), cp1.getPhone());
    List<Client> foundClients = clientDAO.findByAll(clientForm, List.of(serviceForm), null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    serviceForm = new ServiceSearchForm("TSTforGBAC", null, null, null, null, null, new BigDecimal("2000.00"), null);
    foundClients = clientDAO.findByAll(clientForm, List.of(serviceForm), null);
    Assert.assertFalse(foundClients.contains(gbaTestClient));

    foundClients = clientDAO.findByAll(null, List.of(serviceForm), null);

    // ------- //

    EmployeeSearchForm employeeForm = new EmployeeSearchForm(null, null, null, null, "GBAC");

    foundClients = clientDAO.findByAll(clientForm, null, List.of(employeeForm));
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    EmployeeSearchForm employeeForm2 = new EmployeeSearchForm(null, null, null, ContactType.email, null);
    foundClients = clientDAO.findByAll(clientForm, null, List.of(employeeForm, employeeForm2));
    Assert.assertFalse(foundClients.contains(gbaTestClient));

    foundClients = clientDAO.findByAll(null, null, List.of(employeeForm, employeeForm2));

    // ------- //

    clientForm = new ClientSearchForm(null, gbaTestClient.getName(), contact1.getContactType(), contact1.getContact(),
        null, null);
    foundClients = clientDAO.findByAll(clientForm, null, null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    clientForm = new ClientSearchForm(gbaTestClient.getType(), gbaTestClient.getName(), contact1.getContactType(),
        contact1.getContact(), null, null);
    foundClients = clientDAO.findByAll(clientForm, null, null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    clientForm = new ClientSearchForm(null, null, contact2.getContactType(), null, null, null);
    foundClients = clientDAO.findByAll(clientForm, null, null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    clientForm = new ClientSearchForm(null, null, contact2.getContactType(), null, null, null);
    foundClients = clientDAO.findByAll(clientForm, null, null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    foundClients = clientDAO.findByAll(null, null, null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    clientForm = new ClientSearchForm(gbaTestClient.getType(), null, null, "wrong contact", null, null);
    foundClients = clientDAO.findByAll(clientForm, null, null);
    Assert.assertTrue(!foundClients.contains(gbaTestClient));

    clientForm = new ClientSearchForm(null, "ent", null, null, "tact", null);
    foundClients = clientDAO.findByAll(clientForm, null, null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    clientForm = new ClientSearchForm(null, "ent", null, null, "tact", "");
    foundClients = clientDAO.findByAll(clientForm, null, null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

    clientForm = new ClientSearchForm(null, null, null, null, null, "");
    foundClients = clientDAO.findByAll(clientForm, null, null);
    Assert.assertTrue(foundClients.contains(gbaTestClient));

  }
}
