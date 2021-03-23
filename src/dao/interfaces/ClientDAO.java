package dao.interfaces;

import java.util.List;

import objects.Client;
import types.ContactType;

public interface ClientDAO extends CommonDAO<Client, Integer> {
  public List<Client> findByName(String name);

  public List<Client> findByContact(ContactType contactType, String contact);

  public List<Client> findByContactPerson(String name, String phone);
}
