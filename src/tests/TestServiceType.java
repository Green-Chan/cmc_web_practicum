package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import dao.implementations.ServiceTypeDAOImpl;
import dao.interfaces.ServiceTypeDAO;
import objects.ServiceType;

public class TestServiceType {
  private ServiceTypeDAO serviceTypeDAO;

  public TestServiceType() {
    this.serviceTypeDAO = new ServiceTypeDAOImpl();
  }

  @Test
  public void saveServiceType() {
    ServiceType testServiceType = new ServiceType("TST", "Test service type", "This is a service type for testing");
    serviceTypeDAO.save(testServiceType);
    ServiceType loadedServiceType = serviceTypeDAO.findById(testServiceType.getId());
    Assert.assertEquals(testServiceType, loadedServiceType);
  }

  @Test
  public void updateServiceType() {
    ServiceType testServiceType = new ServiceType("UTST", "Update test service type", null);
    serviceTypeDAO.save(testServiceType);
    testServiceType.setInfo("Update test");
    serviceTypeDAO.update(testServiceType);
    ServiceType loadedServiceType = serviceTypeDAO.findById(testServiceType.getId());
    Assert.assertEquals(testServiceType, loadedServiceType);
  }

  @Test
  public void deleteServiceType() {
    ServiceType testServiceType = new ServiceType("DTST", "Delete test service type", null);
    serviceTypeDAO.save(testServiceType);
    serviceTypeDAO.delete(testServiceType);
    ServiceType loadedServiceType = serviceTypeDAO.findById(testServiceType.getId());
    Assert.assertEquals(loadedServiceType, null);
  }

  @Test
  public void findServiceType() {
    ServiceType testServiceTypeMew = new ServiceType("FTSTC", "Find test service type with cat", "Have mew in info");
    ServiceType testServiceType = new ServiceType("FTST", "Find test service type", null);
    serviceTypeDAO.save(testServiceType);
    serviceTypeDAO.save(testServiceTypeMew);
    Assert.assertNotEquals(testServiceType, testServiceTypeMew);
    List<ServiceType> loadedServiceTypes = serviceTypeDAO.findByAll("test", null);
    Assert.assertTrue(loadedServiceTypes.contains(testServiceType) && loadedServiceTypes.contains(testServiceTypeMew));
    loadedServiceTypes = serviceTypeDAO.findByAll(null, "mew");
    Assert.assertTrue(!loadedServiceTypes.contains(testServiceType) && loadedServiceTypes.contains(testServiceTypeMew));
    loadedServiceTypes = serviceTypeDAO.findByAll("Find", "info");
    Assert.assertTrue(!loadedServiceTypes.contains(testServiceType) && loadedServiceTypes.contains(testServiceTypeMew));
  }
}
