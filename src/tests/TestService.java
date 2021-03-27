package tests;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

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
  public void saveAndDeleteService() {
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
