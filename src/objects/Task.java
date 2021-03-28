package objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tasks")
public class Task {

  @EmbeddedId
  private TaskId id;

  @Column(name = "description", nullable = false)
  private String description;

  public Task() {
  }

  public Task(Service service, Employee employee, String description) {
    this.id = new TaskId(service, employee);
    this.description = description;
  }

  public TaskId getId() {
    return id;
  }

  public void setId(TaskId id) {
    this.id = id;
  }

  @Transient
  public Service getService() {
    return id.getService();
  }

  public void setService(Service service) {
    id.setService(service);
  }

  @Transient
  public Employee getEmployee() {
    return id.getEmployee();
  }

  public void setEmployee(Employee employee) {
    id.setEmployee(employee);
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

    return id.equals(other.id) && description.equals(other.description);
  }
}
