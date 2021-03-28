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
@Table(name = "employee_contacts")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class EmployeeContact {

  @Id
  @Column(name = "employee_contact_id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  @Enumerated(EnumType.STRING)
  @Column(name = "employee_contact_type", nullable = false, columnDefinition = "contact_t")
  @Type(type = "pgsql_enum")
  private ContactType contactType;

  @Column(name = "employee_contact", nullable = false)
  private String contact;

  public EmployeeContact() {
  }

  public EmployeeContact(Employee employee, ContactType contactType, String contact) {
    this.employee = employee;
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

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Override
  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if (oth == null || getClass() != oth.getClass()) {
      return false;
    }

    EmployeeContact other = (EmployeeContact) oth;
    return id == other.id && employee.getId() == other.employee.getId() && contactType.equals(other.contactType)
        && contact.equals(other.contact);
  }
}
