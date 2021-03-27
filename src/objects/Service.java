package objects;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "services")
public class Service {
  @Id
  @Column(name = "service_id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @ManyToOne
  @JoinColumn(name = "service_type_id", nullable = false)
  private ServiceType serviceType;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "service_id")
  private List<Task> tasks;

  @Column(name = "service_begin")
  private Date beginTime;

  @Column(name = "service_end")
  private Date endTime;

  @Column(name = "price")
  @Type(type = "big_decimal")
  private BigDecimal price;

  public Service() {
  }

  public Service(Client client, ServiceType serviceType, Date beginTime, Date endTime, BigDecimal price) {
    this.client = client;
    this.serviceType = serviceType;
    this.beginTime = beginTime;
    this.endTime = endTime;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public ServiceType getServiceType() {
    return serviceType;
  }

  public void setServiceType(ServiceType serviceType) {
    this.serviceType = serviceType;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  private boolean datesEquals(Date date1, Date date2) {
    if (date1 == null) {
      return date2 == null;
    }
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTimeInMillis(date1.getTime());

    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTimeInMillis(date2.getTime());

    return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
        && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
        && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
  }

  @Override
  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if ((oth == null && this != null) || getClass() != oth.getClass()) {
      return false;
    }

    Service other = (Service) oth;

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

    if (price == null) {
      if (other.price != null) {
        return false;
      }
    } else {
      if (!price.equals(other.price)) {
        return false;
      }
    }

    return id == other.id && client.getId() == other.client.getId()
        && serviceType.getId().equals(other.serviceType.getId()) && datesEquals(beginTime, other.beginTime)
        && datesEquals(endTime, other.endTime);
  }

}
