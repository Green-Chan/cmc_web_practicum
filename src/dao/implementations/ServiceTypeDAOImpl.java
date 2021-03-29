package dao.implementations;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.interfaces.ServiceTypeDAO;
import objects.ServiceType;
import utils.HibernateSessionFactory;

public class ServiceTypeDAOImpl extends CommonDAOImpl<ServiceType, String> implements ServiceTypeDAO {
  public List<ServiceType> findByAll(String name, String info) {
    Session session = null;
    List<ServiceType> serviceTypes = new ArrayList<ServiceType>();

    session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    String queryString = "FROM ServiceType";
    if (name != null || info != null) {
      queryString += " WHERE";
    }
    if (name != null) {
      queryString += " name LIKE :name_param";
      if (info != null) {
        queryString += " AND";
      }
    }
    if (info != null) {
      queryString += " info LIKE :info_param";
    }
    Query<ServiceType> query = session.createQuery(queryString, ServiceType.class);
    if (name != null) {
      query.setParameter("name_param", '%' + name + '%');
    }
    if (info != null) {
      query.setParameter("info_param", '%' + info + '%');
    }
    serviceTypes = query.list();

    transaction.commit();
    session.close();

    return serviceTypes;
  }
}
