package dao.interfaces;

import java.util.List;

import objects.ServiceType;

public interface ServiceTypeDAO extends CommonDAO<ServiceType, String> {
  public List<ServiceType> findByAll(String name, String info);
}
