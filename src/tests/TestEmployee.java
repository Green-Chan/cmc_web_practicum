package tests;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
import objects.Employee;
import objects.EmployeeContact;
import objects.Service;
import objects.ServiceType;
import objects.Task;
import types.ClientType;
import types.ContactType;

public class TestEmployee {
  private EmployeeDAO employeeDAO;
  private ServiceDAO serviceDAO;
  private ClientDAO clientDAO;
  private ServiceTypeDAO serviceTypeDAO;

  public TestEmployee() {
    this.employeeDAO = new EmployeeDAOImpl();
    this.serviceDAO = new ServiceDAOImpl();
    this.clientDAO = new ClientDAOImpl();
    this.serviceTypeDAO = new ServiceTypeDAOImpl();
  }

  @Test
  public void saveAndDeleteEmployee() {
    // -------- Create test Employee --------- //
    // (save and load it)
    Employee testEmployee = new Employee("test emp", "test emp", "test emp");
    EmployeeContact contact1 = new EmployeeContact(testEmployee, ContactType.phone, "test employee phone");
    EmployeeContact contact2 = new EmployeeContact(testEmployee, ContactType.email, "test employee email");
    testEmployee.setContacts(List.of(contact1, contact2));
    employeeDAO.save(testEmployee);
    Employee loadedEmployee = employeeDAO.findById(testEmployee.getId());
    Assert.assertEquals(loadedEmployee, testEmployee);

    // -------- Create test Service --------- //
    ServiceType testServiceType = new ServiceType("TSTforSDE", "Test service type for save and delete Employee test",
        null);
    serviceTypeDAO.save(testServiceType);
    Client testClient = new Client(ClientType.organization, "Test org");
    clientDAO.save(testClient);
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2020);
    calendar.set(Calendar.MONTH, Calendar.OCTOBER);
    calendar.set(Calendar.DAY_OF_MONTH, 15);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    Date beginTime = calendar.getTime();
    calendar.set(Calendar.YEAR, 2020);
    calendar.set(Calendar.MONTH, Calendar.OCTOBER);
    calendar.set(Calendar.DAY_OF_MONTH, 31);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    Date endTime = calendar.getTime();
    BigDecimal price = new BigDecimal("1500.00");

    Service testService = new Service(testClient, testServiceType, beginTime, endTime, price);

    // -------- Create test Task ----------- //
    Task testTask = new Task(testService, testEmployee, "test description");
    testService.setTasks(List.of(testTask));
    serviceDAO.save(testService);
    loadedEmployee = employeeDAO.findById(testEmployee.getId());
    Assert.assertNotEquals(testEmployee, loadedEmployee);
    testEmployee.setTasks(List.of(testTask));
    Assert.assertEquals(loadedEmployee, testEmployee);

    // -------- Find ----------//
    EmployeeSearchForm employeeForm = new EmployeeSearchForm(testEmployee.getName(), null, testEmployee.getEducation(),
        contact1.getContactType(), contact1.getContact());
    List<Employee> loadedEmployees = employeeDAO.findByAll(employeeForm, null, null);
    Assert.assertTrue(loadedEmployees.contains(testEmployee));
    employeeForm = new EmployeeSearchForm(testEmployee.getName(), "wrong", testEmployee.getEducation(),
        contact1.getContactType(), contact1.getContact());
    loadedEmployees = employeeDAO.findByAll(employeeForm, null, null);
    Assert.assertFalse(loadedEmployees.contains(testEmployee));
    employeeForm = new EmployeeSearchForm(null, "wrong", testEmployee.getEducation(), contact1.getContactType(),
        contact1.getContact());
    loadedEmployees = employeeDAO.findByAll(employeeForm, null, null);
    Assert.assertFalse(loadedEmployees.contains(testEmployee));
    employeeForm = new EmployeeSearchForm(null, null, testEmployee.getEducation(), contact1.getContactType(), null);
    loadedEmployees = employeeDAO.findByAll(employeeForm, null, null);
    Assert.assertTrue(loadedEmployees.contains(testEmployee));

    ServiceSearchForm serviceForm = new ServiceSearchForm(null, " service type for save", null, beginTime, endTime,
        endTime, new BigDecimal("1200.00"), new BigDecimal("2000.00"));
    loadedEmployees = employeeDAO.findByAll(null, List.of(serviceForm), null);
    Assert.assertTrue(loadedEmployees.contains(testEmployee));
    serviceForm = new ServiceSearchForm(null, " service type for save", beginTime, null, endTime, null,
        new BigDecimal("1200.00"), new BigDecimal("1200.00"));
    loadedEmployees = employeeDAO.findByAll(employeeForm, List.of(serviceForm), null);
    Assert.assertFalse(loadedEmployees.contains(testEmployee));
    serviceForm = new ServiceSearchForm(null, " service type for save", beginTime, beginTime, null, endTime,
        new BigDecimal("1200.00"), new BigDecimal("1200.00"));
    loadedEmployees = employeeDAO.findByAll(employeeForm, List.of(serviceForm), null);
    Assert.assertFalse(loadedEmployees.contains(testEmployee));
    serviceForm = new ServiceSearchForm(null, null, beginTime, beginTime, null, endTime, new BigDecimal("1200.00"),
        new BigDecimal("1200.00"));
    loadedEmployees = employeeDAO.findByAll(employeeForm, List.of(serviceForm), null);
    Assert.assertFalse(loadedEmployees.contains(testEmployee));
    serviceForm = new ServiceSearchForm(null, null, null, beginTime, null, endTime, new BigDecimal("1200.00"),
        new BigDecimal("1200.00"));
    loadedEmployees = employeeDAO.findByAll(employeeForm, List.of(serviceForm), null);
    Assert.assertFalse(loadedEmployees.contains(testEmployee));
    serviceForm = new ServiceSearchForm(null, null, null, null, null, endTime, new BigDecimal("1200.00"),
        new BigDecimal("1200.00"));
    loadedEmployees = employeeDAO.findByAll(employeeForm, List.of(serviceForm), null);
    Assert.assertFalse(loadedEmployees.contains(testEmployee));
    serviceForm = new ServiceSearchForm(null, null, null, null, endTime, endTime, new BigDecimal("1200.00"),
        new BigDecimal("1200.00"));
    loadedEmployees = employeeDAO.findByAll(employeeForm, null, null);
    Assert.assertTrue(loadedEmployees.contains(testEmployee));

    loadedEmployees = employeeDAO.findByAll(employeeForm, List.of(serviceForm), null);
    Assert.assertFalse(loadedEmployees.contains(testEmployee));

    ClientSearchForm clientForm = new ClientSearchForm(testClient.getType(), null, null, null, null, null);
    loadedEmployees = employeeDAO.findByAll(null, null, List.of(clientForm));
    Assert.assertTrue(loadedEmployees.contains(testEmployee));
    clientForm = new ClientSearchForm(null, (testClient.getName() + "5"), null, null, null, null);
    loadedEmployees = employeeDAO.findByAll(employeeForm, null, null);
    Assert.assertTrue(loadedEmployees.contains(testEmployee));

    loadedEmployees = employeeDAO.findByAll(employeeForm, null, List.of(clientForm));

    // -------- Delete -------- //
    serviceDAO.delete(testService);
    employeeDAO.delete(testEmployee);
    loadedEmployee = employeeDAO.findById(testEmployee.getId());
    Assert.assertEquals(loadedEmployee, null);
  }

  @Test
  public void updateEmployee() {
    Employee testEmployee = new Employee("test emp", "test emp", "test emp");
    EmployeeContact contact = new EmployeeContact(testEmployee, ContactType.phone, "test employee phone");
    testEmployee.setContacts(List.of(contact));
    employeeDAO.save(testEmployee);

    testEmployee.setContacts(null);
    employeeDAO.update(testEmployee);

    Employee loadedEmployee = employeeDAO.findById(testEmployee.getId());
    Assert.assertEquals(loadedEmployee, testEmployee);
  }

}
