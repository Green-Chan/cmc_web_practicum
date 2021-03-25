package dao.interfaces;

import java.util.List;

import objects.ServiceType;

public interface ServiceTypeDAO extends CommonDAO<ServiceType, String> {
  public List<ServiceType> findByOther(String name, String info);
}
