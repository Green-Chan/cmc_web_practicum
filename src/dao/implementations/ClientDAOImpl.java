package dao.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.interfaces.ClientDAO;
import objects.Client;
import objects.ClientContact;
import objects.ClientContactPerson;
import objects.Service;
import types.ClientType;
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

  /*
   * Find clients by their type and name, types and values of their contacts,
   * names and phones of their contact persons.
   */
  // TODO: services are not considered yet
  public List<Client> findByAll(ClientType type, String name, ClientContact contact, ClientContactPerson contactPerson,
      List<Service> services) {

    String queryString = "SELECT DISTINCT cl FROM Client cl";

    boolean firstCondition = true;
    Map<String, Object> parameters = new HashMap<>();

    if (type != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " cl.type = :type_param";
      parameters.put("type_param", type);
    }

    if (name != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " cl.name LIKE :name_param";
      parameters.put("name_param", "%" + name + "%");
    }

    if (contact != null && (contact.getContactType() != null || contact.getContact() != null)) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " cl in (SELECT con.client FROM ClientContact con WHERE";

      if (contact.getContactType() != null) {
        queryString += " con.contactType = :con_type_param";
        parameters.put("con_type_param", contact.getContactType());
        if (contact.getContact() != null) {
          queryString += " AND";
        }
      }
      if (contact.getContact() != null) {
        queryString += " con.contact LIKE :con_contact_param";
        parameters.put("con_contact_param", "%" + contact.getContact() + "%");
      }
      queryString += ")";
    }

    if (contactPerson != null && (contactPerson.getName() != null || contactPerson.getPhone() != null)) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " cl in (SELECT cp.client FROM ClientContactPerson cp WHERE";

      if (contactPerson.getName() != null) {
        queryString += " cp.name LIKE :cp_name_param";
        parameters.put("cp_name_param", "%" + contactPerson.getName() + "%");
        if (contactPerson.getPhone() != null) {
          queryString += " AND";
        }
      }
      if (contactPerson.getPhone() != null) {
        queryString += " cp.phone LIKE :cp_phone_param";
        parameters.put("cp_phone_param", "%" + contactPerson.getPhone() + "%");
      }
      queryString += ")";
    }

    System.out.println(queryString);

    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    Query<Client> query = session.createQuery(queryString, Client.class);
    for (Entry<String, Object> parameter : parameters.entrySet()) {
      query.setParameter(parameter.getKey(), parameter.getValue());
    }
    List<Client> clients = query.list();

    transaction.commit();
    session.close();

    return clients;
  }
}
