package objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "client_contact_persons")
public class ClientContactPerson {

  @Id
  @Column(name = "client_cp_id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Column(name = "client_cp_name", nullable = false)
  private String name;

  @Column(name = "client_cp_phone", nullable = false)
  private String phone;

  public ClientContactPerson() {
  }

  public ClientContactPerson(Client client, String name, String phone) {
    this.client = client;
    this.name = name;
    this.phone = phone;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

    ClientContactPerson other = (ClientContactPerson) oth;
    return id == other.id && client.getId() == other.client.getId() && name.equals(other.name)
        && phone.equals(other.phone);
  }
}
