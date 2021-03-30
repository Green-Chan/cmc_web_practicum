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
import dao.implementations.ServiceDAOImpl;
import dao.implementations.ServiceTypeDAOImpl;
import dao.interfaces.ClientDAO;
import dao.interfaces.ServiceDAO;
import dao.interfaces.ServiceTypeDAO;
import objects.Client;
import objects.Service;
import objects.ServiceType;
import types.ClientType;

public class TestService {
  private ServiceDAO serviceDAO;
  private ClientDAO clientDAO;
  private ServiceTypeDAO serviceTypeDAO;

  public TestService() {
    this.serviceDAO = new ServiceDAOImpl();
    this.clientDAO = new ClientDAOImpl();
    this.serviceTypeDAO = new ServiceTypeDAOImpl();
  }

  @Test
  public void saveFindAndDeleteService() {
    ServiceType testServiceType = new ServiceType("TSTforSDS", "Test service type for save and delete Service test",
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

    serviceDAO.save(testService);
    Service loadedService = serviceDAO.findById(testService.getId());

    Assert.assertEquals(testService, loadedService);

    // ---- Find ----- //

    ServiceSearchForm serviceForm = new ServiceSearchForm(null, " service type for save", null, null, endTime, endTime,
        new BigDecimal("1200.00"), new BigDecimal("2000.00"));
    List<Service> loadedServices = serviceDAO.findByAll(serviceForm, null, null);
    Assert.assertTrue(loadedServices.contains(testService));
    serviceForm = new ServiceSearchForm(testServiceType.getId(), " service type for save", null, null, endTime, endTime,
        null, new BigDecimal("2000.00"));
    loadedServices = serviceDAO.findByAll(serviceForm, null, null);
    Assert.assertTrue(loadedServices.contains(testService));
    serviceForm = new ServiceSearchForm(null, null, null, null, null, null, new BigDecimal("1200.00"), null);
    loadedServices = serviceDAO.findByAll(serviceForm, null, null);
    Assert.assertTrue(loadedServices.contains(testService));
    serviceForm = new ServiceSearchForm(null, null, null, null, null, null, null, new BigDecimal("2000.00"));
    loadedServices = serviceDAO.findByAll(serviceForm, null, null);
    Assert.assertTrue(loadedServices.contains(testService));

    ClientSearchForm clientForm = new ClientSearchForm(null, testClient.getName(), null, null, null, null);
    loadedServices = serviceDAO.findByAll(null, null, List.of(clientForm));
    Assert.assertTrue(loadedServices.contains(testService));

    EmployeeSearchForm employeeForm = new EmployeeSearchForm("", "", null, null, null);
    loadedServices = serviceDAO.findByAll(null, List.of(employeeForm), List.of(clientForm));
    Assert.assertFalse(loadedServices.contains(testService));

    loadedServices = serviceDAO.findByAll(serviceForm, List.of(employeeForm), null);

    // ---- Delete ----- //

    serviceDAO.delete(testService);
    loadedService = serviceDAO.findById(testService.getId());

    Assert.assertEquals(loadedService, null);
  }

  @Test
  public void updateService() {
    ServiceType testServiceType = new ServiceType("TSTforUS", "Test service type for update Service test", null);
    serviceTypeDAO.save(testServiceType);
    Client testClient = new Client(ClientType.person, "Test pers");
    clientDAO.save(testClient);
    BigDecimal price = new BigDecimal("1500.00");

    Service testService = new Service(testClient, testServiceType, null, null, null);
    Service newTestService = new Service(testClient, testServiceType, null, null, price);
    serviceDAO.save(testService);
    newTestService.setId(testService.getId());
    Assert.assertNotEquals(testService, newTestService);
    serviceDAO.update(newTestService);
    Service loadedSrvice = serviceDAO.findById(testService.getId());
    Assert.assertEquals(newTestService, loadedSrvice);
  }
}
