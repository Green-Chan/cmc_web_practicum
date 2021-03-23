package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import dao.implementations.ClientDAOImpl;
import dao.interfaces.ClientDAO;
import objects.Client;
import objects.ClientContact;
import objects.ClientContactPerson;
import types.ClientType;
import types.ContactType;

public class TestClient {

  private ClientDAO clientDAO;

  public TestClient() {
    clientDAO = new ClientDAOImpl();
  }

  @Test
  public void saveClient() {
    Client client = new Client(ClientType.person, "Masha");

    clientDAO.save(client);
    Client loadedClient = clientDAO.findById(client.getId());

    Assert.assertEquals(client.getId(), loadedClient.getId());
    Assert.assertEquals(client.getName(), loadedClient.getName());
    Assert.assertEquals(client.getType(), loadedClient.getType());
    Assert.assertEquals(loadedClient.getContacts().size(), 0);
    Assert.assertEquals(loadedClient.getContactPersons().size(), 0);
    Assert.assertEquals(loadedClient.getServices().size(), 0);

    // ----------------//

    client = new Client(ClientType.organization, "Medved");
    ClientContact contact1 = new ClientContact(client, ContactType.email, "info@medved.ru");
    ClientContact contact2 = new ClientContact(client, ContactType.phone, "+7 (495) 230-01-80");
    client.setContacts(List.of(contact1, contact2));
    ClientContactPerson cp = new ClientContactPerson(client, "Masha", "+7 (495) 201-10-47");
    client.setContactPersons(List.of(cp));

    clientDAO.save(client);
    loadedClient = clientDAO.findById(client.getId());

    Assert.assertEquals(client.getId(), loadedClient.getId());
    Assert.assertEquals(client.getName(), loadedClient.getName());
    Assert.assertEquals(client.getType(), loadedClient.getType());
    Assert.assertEquals(loadedClient.getContacts().size(), 2);
    Assert.assertTrue(loadedClient.getContacts().contains(contact1));
    Assert.assertTrue(loadedClient.getContacts().contains(contact2));
    Assert.assertEquals(loadedClient.getContactPersons().size(), 1);
    Assert.assertTrue(loadedClient.getContactPersons().contains(cp));
    Assert.assertEquals(loadedClient.getServices().size(), 0);
  }

  @Test
  public void updateClient() {
    Client client = new Client(ClientType.person, "Masha");
    clientDAO.save(client);

    ClientContact contact = new ClientContact(client, ContactType.phone, "+7 (495) 201-10-47");
    client.setContacts(List.of(contact));

    clientDAO.update(client);

    Client loadedClient = clientDAO.findById(client.getId());

    Assert.assertEquals(client.getId(), loadedClient.getId());
    Assert.assertEquals(client.getName(), loadedClient.getName());
    Assert.assertEquals(client.getType(), loadedClient.getType());
    Assert.assertEquals(loadedClient.getContacts().size(), 1);
    Assert.assertTrue(loadedClient.getContacts().contains(contact));

  }
}
