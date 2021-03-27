package objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_types")
public class ServiceType {
  @Id
  @Column(name = "service_type_id")
  private String id;

  @Column(name = "service_type_name", nullable = false)
  private String name;

  @Column(name = "service_info")
  private String info;

  public ServiceType() {

  }

  public ServiceType(String id, String name, String info) {
    this.id = id;
    this.name = name;
    this.info = info;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public boolean equals(Object oth) {
    if (this == oth) {
      return true;
    }

    if (oth == null || getClass() != oth.getClass()) {
      return false;
    }
    ServiceType other = (ServiceType) oth;

    if (info == null) {
      if (other.info != null) {
        return false;
      }
    } else {
      if (!info.equals(other.info)) {
        return false;
      }
    }
    return id.equals(other.id) && name.equals(other.name);
  }
}
