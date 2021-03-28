package objects;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TaskId implements Serializable {

  private static final long serialVersionUID = 6923360565294186914L;

  @ManyToOne
  @JoinColumn(name = "service_id", nullable = false)
  private Service service;

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  public TaskId() {
  }

  TaskId(Service service, Employee employee) {
    this.service = service;
    this.employee = employee;
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

  @Override
  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if ((oth == null && this != null) || getClass() != oth.getClass()) {
      return false;
    }

    TaskId other = (TaskId) oth;

    return service.getId() == other.service.getId() && employee.getId() == other.employee.getId();
  }
}
