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

  public Employee(String name, String education, String position) {
    this.name = name;
    this.education = education;
    this.position = position;
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

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  @Override
  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if ((oth == null && this != null) || getClass() != oth.getClass()) {
      return false;
    }

    Employee other = (Employee) oth;

    if (tasks != null && tasks.size() > 0) {
      if (other.tasks == null || !(other.tasks.containsAll(tasks))) {
        return false;
      }
    }
    if (other.tasks != null && other.tasks.size() > 0) {
      if (tasks == null || !(tasks.containsAll(other.tasks))) {
        return false;
      }
    }

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

    return id == other.id && name.equals(other.name) && education.equals(other.education)
        && position.equals(other.position);
  }
}
