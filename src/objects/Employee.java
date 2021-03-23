package objects;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @Column(name = "employee_id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "employee_name", nullable = false)
  private String name;

  @Column(name = "education", nullable = false)
  private String education;

  @Column(name = "position", nullable = false)
  private String position;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "employee_id")
  private List<EmployeeContact> contacts;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "employee_id")
  private List<Task> tasks;

  public Employee() {
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

  public String getEducation() {
    return education;
  }

  public void setEducation(String education) {
    this.education = education;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public List<EmployeeContact> getContacts() {
    return contacts;
  }

  public void setContacts(List<EmployeeContact> contacts) {
    this.contacts = contacts;
  }
}
