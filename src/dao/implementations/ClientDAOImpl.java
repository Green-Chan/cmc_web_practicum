package dao.implementations;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.interfaces.ClientDAO;
import objects.Client;
import types.ContactType;
import utils.HibernateSessionFactory;

public class ClientDAOImpl extends CommonDAOImpl<Client, Integer> implements ClientDAO {
  public List<Client> findByName(String name) {
    Session session = null;
    List<Client> clients = new ArrayList<Client>();

    session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    Query<Client> query = session.createQuery("FROM Client WHERE client_name LIKE :name_param", Client.class);
    query.setParameter("name_param", '%' + name + '%');
    clients = query.list();

    transaction.commit();
    session.close();

    return clients;
  }

  public List<Client> findByContact(ContactType contactType, String contact) {
    Session session = null;
    List<Client> clients = new ArrayList<Client>();

    session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    Query<Client> query = session.createQuery(
        "SELECT cl FROM Client cl JOIN cl.contacts cont WHERE cont.contactType = :type_param AND cont.contact LIKE :contact_param",
        Client.class);
    query.setParameter("type_param", contactType);
    query.setParameter("contact_param", '%' + contact + '%');
    clients = query.list();

    transaction.commit();
    session.close();

    return clients;
  }

  public List<Client> findByContactPerson(String name, String phone) {
    Session session = null;
    List<Client> clients = new ArrayList<Client>();

    session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    Query<Client> query = session.createQuery(
        "SELECT cl FROM Client cl JOIN cl.contactPersons p WHERE p.name LIKE :name_param AND p.phone LIKE :phone_param",
        Client.class);
    query.setParameter("name_param", '%' + name + '%');
    query.setParameter("phone_param", '%' + phone + '%');
    clients = query.list();

    transaction.commit();
    session.close();

    return clients;
  }
}
