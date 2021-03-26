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
  public void deleteClient() {
    Client client = new Client(ClientType.person, "deleted client");
    clientDAO.save(client);
    clientDAO.delete(client);
    Client loadedClient = clientDAO.findById(client.getId());
    Assert.assertEquals(loadedClient, null);
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

  @Test
  public void getByAll() {
    Client gbaTestClient = new Client(ClientType.person, "gba client");
    ClientContact contact1 = new ClientContact(gbaTestClient, ContactType.phone, "+74952011047");
    ClientContact contact2 = new ClientContact(gbaTestClient, ContactType.email, "gbaclient@cs.msu.ru");
    gbaTestClient.setContacts(List.of(contact1, contact2));
    ClientContactPerson cp1 = new ClientContactPerson(gbaTestClient, "Contact1", "88005553535");
    ClientContactPerson cp2 = new ClientContactPerson(gbaTestClient, "Contact2", "+78005553535");
    gbaTestClient.setContactPersons(List.of(cp1, cp2));

    clientDAO.save(gbaTestClient);

    contact1.setId(0);
    contact2.setId(0);
    cp1.setId(0);
    cp2.setId(0);
    List<Client> foundClients = clientDAO.findByAll(gbaTestClient.getType(), gbaTestClient.getName(), contact2, cp1,
        null);
    boolean found = false;
    for (Client cl : foundClients) {
      if (cl.getId() == gbaTestClient.getId()) {
        found = true;
      }
    }
    Assert.assertTrue(found);

    foundClients = clientDAO.findByAll(gbaTestClient.getType(), null, contact1, null, null);
    found = false;
    for (Client cl : foundClients) {
      if (cl.getId() == gbaTestClient.getId()) {
        found = true;
      }
    }
    Assert.assertTrue(found);

    foundClients = clientDAO.findByAll(null, null, null, cp2, null);
    found = false;
    for (Client cl : foundClients) {
      if (cl.getId() == gbaTestClient.getId()) {
        found = true;
      }
    }
    Assert.assertTrue(found);

    foundClients = clientDAO.findByAll(null, null, null, null, null);
    found = false;
    for (Client cl : foundClients) {
      if (cl.getId() == gbaTestClient.getId()) {
        found = true;
      }
    }
    Assert.assertTrue(found);

    foundClients = clientDAO.findByAll(gbaTestClient.getType(), null, new ClientContact(null, null, "wrong contact"),
        null, null);
    found = false;
    for (Client cl : foundClients) {
      if (cl.getId() == gbaTestClient.getId()) {
        found = true;
      }
    }
    Assert.assertTrue(!found);

    foundClients = clientDAO.findByAll(null, "ent", null, new ClientContactPerson(null, "tact", null), null);
    found = false;
    for (Client cl : foundClients) {
      if (cl.getId() == gbaTestClient.getId()) {
        found = true;
      }
    }
    Assert.assertTrue(found);

  }
}
