package dao.interfaces;

import java.util.List;

import objects.Client;
import objects.ClientContact;
import objects.ClientContactPerson;
import objects.Service;
import types.ClientType;
import types.ContactType;

public interface ClientDAO extends CommonDAO<Client, Integer> {
  public List<Client> findByName(String name);

  public List<Client> findByContact(ContactType contactType, String contact);

  public List<Client> findByContactPerson(String name, String phone);

  public List<Client> findByAll(ClientType type, String name, ClientContact contact, ClientContactPerson contactPerson,
      List<Service> services);
}
