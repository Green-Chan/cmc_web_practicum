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
@Table(name = "tasks")
public class Task {

  @Id
  @Column(name = "task_id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "service_id", nullable = false)
  private Service service;

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  @Column(name = "description", nullable = false)
  private String description;

  public Task() {
  }

  public Task(Service service, Employee employee, String description) {
    this.service = service;
    this.employee = employee;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Service getService() {
    return service;
  }

  public void setService(Service service) {
    this.service = service;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if ((oth == null && this != null) || getClass() != oth.getClass()) {
      return false;
    }

    Task other = (Task) oth;

    return id == other.id && service.getId() == other.service.getId() && employee.getId() == other.employee.getId()
        && description.equals(other.description);
  }
}
