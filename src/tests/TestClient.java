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

  @Test
  public void getByName() {
    Client maria = new Client(ClientType.person, "Иванова Мария Ивановна");
    clientDAO.save(maria);

    Client ivanna = new Client(ClientType.person, "Иванова Иванна Сергеевна");
    clientDAO.save(ivanna);

    List<Client> foundClients = clientDAO.findByName("Иванова");
    boolean containsMaria = false;
    boolean containsIvanna = false;
    for (Client c : foundClients) {
      if (c.getId() == maria.getId()) {
        containsMaria = true;
      }
      if (c.getId() == ivanna.getId()) {
        containsIvanna = true;
      }
    }
    Assert.assertTrue(containsIvanna);
    Assert.assertTrue(containsMaria);

    foundClients = clientDAO.findByName("Мария");
    containsMaria = false;
    containsIvanna = false;
    for (Client c : foundClients) {
      if (c.getId() == maria.getId()) {
        containsMaria = true;
      }
      if (c.getId() == ivanna.getId()) {
        containsIvanna = true;
      }
    }
    Assert.assertTrue(!containsIvanna);
    Assert.assertTrue(containsMaria);
  }

  @Test
  public void getByContact() {
    Client client = new Client(ClientType.person, "Masha");
    ClientContact contact = new ClientContact(client, ContactType.phone, "+7 (495) 201-10-47");
    client.setContacts(List.of(contact));
    clientDAO.save(client);

    List<Client> foundClients = clientDAO.findByContact(ContactType.phone, "201-10-47");
    boolean containsMasha = false;
    for (Client c : foundClients) {
      if (c.getContacts().contains(contact)) {
        containsMasha = true;
      }
    }
    Assert.assertTrue(containsMasha);

    foundClients = clientDAO.findByContact(ContactType.email, "201-10-47");
    containsMasha = false;
    for (Client c : foundClients) {
      if (c.getContacts().contains(contact)) {
        containsMasha = true;
      }
    }
    Assert.assertTrue(!containsMasha);
  }
}
