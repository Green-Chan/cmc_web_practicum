package dao.implementations;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.interfaces.CommonDAO;
import utils.HibernateSessionFactory;

public class CommonDAOImpl<T, K extends Serializable> implements CommonDAO<T, K> {
  private final Class<T> type;

  @SuppressWarnings("unchecked")
  public CommonDAOImpl() {
    type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public T findById(K id) {
    Session session = null;
    T object = null;

    session = HibernateSessionFactory.getSessionFactory().openSession();
    object = (T) session.get(type, id);

    session.close();
    return object;
  }

  @Override
  public void save(T object) {
    Session session = null;

    session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.save(object);
    transaction.commit();

    session.close();
  }

  @Override
  public void delete(T object) {
    Session session = null;

    session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.delete(object);
    transaction.commit();

    session.close();
  }

  @Override
  public void update(T object) {
    Session session = null;

    session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    session.update(object);
    transaction.commit();

    session.close();
  }

  @Override
  public List<T> findAll() {
    Session session = null;
    List<T> objects = new ArrayList<T>();

    session = HibernateSessionFactory.getSessionFactory().openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<T> criteria = builder.createQuery(type);
    Root<T> root = criteria.from(type);
    criteria.select(root);
    Query<T> query = session.createQuery(criteria);
    objects = (List<T>) query.getResultList();

    session.close();
    return objects;
  }
}
