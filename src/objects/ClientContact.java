package objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import types.ContactType;
import utils.PostgreSQLEnumType;

@Entity
@Table(name = "client_contacts")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class ClientContact {

  public ClientContact() {
  }

  @Id
  @Column(name = "client_contact_id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Enumerated(EnumType.STRING)
  @Column(name = "client_contact_type", nullable = false, columnDefinition = "contact_t")
  @Type(type = "pgsql_enum")
  private ContactType contactType;

  @Column(name = "client_contact", nullable = false)
  private String contact;

  public ClientContact(Client client, ContactType contactType, String contact) {
    this.client = client;
    this.contactType = contactType;
    this.contact = contact;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ContactType getContactType() {
    return contactType;
  }

  public void setContactType(ContactType contactType) {
    this.contactType = contactType;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Override
  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if (oth == null || getClass() != oth.getClass()) {
      return false;
    }

    ClientContact other = (ClientContact) oth;
    return id == other.id && client.getId() == other.client.getId() && contactType.equals(other.contactType)
        && contact.equals(other.contact);
  }
}
