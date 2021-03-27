package objects;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import types.ClientType;
import utils.PostgreSQLEnumType;

@Entity
@Table(name = "clients")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Client {

  @Id
  @Column(name = "client_id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.STRING)
  @Column(name = "client_type", nullable = false, columnDefinition = "client_type_t")
  @Type(type = "pgsql_enum")
  private ClientType type;

  @Column(name = "client_name", nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "client_id")
  private List<ClientContact> contacts;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "client_id")
  private List<ClientContactPerson> contactPersons;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "client_id")
  private List<Service> services;

  public Client() {
  }

  public Client(ClientType type, String name) {
    this.type = type;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int newId) {
    id = newId;
  }

  public ClientType getType() {
    return type;
  }

  public void setType(ClientType newType) {
    type = newType;
  }

  public String getName() {
    return name;
  }

  public void setName(String newName) {
    name = newName;
  }

  public List<ClientContact> getContacts() {
    return contacts;
  }

  public void setContacts(List<ClientContact> newContacts) {
    contacts = newContacts;
  }

  public List<ClientContactPerson> getContactPersons() {
    return contactPersons;
  }

  public void setContactPersons(List<ClientContactPerson> newContactPersons) {
    contactPersons = newContactPersons;
  }

  public List<Service> getServices() {
    return services;
  }

  public void setServices(List<Service> services) {
    this.services = services;
  }

  @Override
  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if (oth == null || getClass() != oth.getClass()) {
      return false;
    }

    Client other = (Client) oth;

    if (contacts != null && contacts.size() > 0) {
      if (other.contacts == null || !(other.contacts.containsAll(contacts))) {
        return false;
      }
    }

    if (other.contacts != null && other.contacts.size() > 0) {
      if (contacts == null || !(contacts.containsAll(other.contacts))) {
        return false;
      }
    }

    if (contactPersons != null && contactPersons.size() > 0) {
      if (other.contactPersons == null || !(other.contactPersons.containsAll(contactPersons))) {
        return false;
      }
    }
    if (other.contactPersons != null && other.contactPersons.size() > 0) {
      if (contactPersons == null || !(contactPersons.containsAll(other.contactPersons))) {
        return false;
      }
    }

    if (services != null && services.size() > 0) {
      if (other.services == null || !(other.services.containsAll(services))) {
        return false;
      }
    }
    if (other.services != null && other.services.size() > 0) {
      if (services == null || !(services.containsAll(other.services))) {
        return false;
      }
    }

    return id == other.id && type.equals(other.type) && name.equals(other.name);
  }
}
