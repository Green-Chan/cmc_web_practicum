package dao.forms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ServiceSearchForm {
  private String serviceTypeId;
  private String serviceTypeName;
  private Date beginLower;
  private Date beginUpper;
  private Date endLower;
  private Date endUpper;
  private BigDecimal priceLower;
  private BigDecimal priceUpper;

  public ServiceSearchForm(String serviceTypeId, String serviceTypeName, Date beginLower, Date beginUpper,
      Date endLower, Date endUpper, BigDecimal priceLower, BigDecimal priceUpper) {
    super();
    this.serviceTypeId = serviceTypeId;
    this.serviceTypeName = serviceTypeName;
    this.beginLower = beginLower;
    this.beginUpper = beginUpper;
    this.endLower = endLower;
    this.endUpper = endUpper;
    this.priceLower = priceLower;
    this.priceUpper = priceUpper;
  }

  public String getServiceTypeId() {
    return serviceTypeId;
  }

  public void setServiceTypeId(String serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
  }

  public String getServiceTypeName() {
    return serviceTypeName;
  }

  public void setServiceTypeName(String serviceTypeName) {
    this.serviceTypeName = serviceTypeName;
  }

  public Date getBeginLower() {
    return beginLower;
  }

  public void setBeginLower(Date beginLower) {
    this.beginLower = beginLower;
  }

  public Date getBeginUpper() {
    return beginUpper;
  }

  public void setBeginUpper(Date beginUpper) {
    this.beginUpper = beginUpper;
  }

  public Date getEndLower() {
    return endLower;
  }

  public void setEndLower(Date endLower) {
    this.endLower = endLower;
  }

  public Date getEndUpper() {
    return endUpper;
  }

  public void setEndUpper(Date endUpper) {
    this.endUpper = endUpper;
  }

  public BigDecimal getPriceLower() {
    return priceLower;
  }

  public void setPriceLower(BigDecimal priceLower) {
    this.priceLower = priceLower;
  }

  public BigDecimal getPriceUpper() {
    return priceUpper;
  }

  public void setPriceUpper(BigDecimal priceUpper) {
    this.priceUpper = priceUpper;
  }

  public String getQueryString(String postfix, Map<String, Object> parameters, boolean selectTask) {
    String queryString = "";
    if (selectTask) {
      queryString += " FROM Task task JOIN task.id.service serv";
    } else {
      queryString += " FROM Service serv";
    }
    boolean firstCondition = true;

    if (serviceTypeId != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " serv.serviceType.id = :serv_type_id_param" + postfix;
      parameters.put("serv_type_id_param" + postfix, serviceTypeId);
    }
    if (serviceTypeName != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " serv.serviceType.name LIKE :serv_type_name_param" + postfix;
      parameters.put("serv_type_name_param" + postfix, "%" + serviceTypeName + "%");
    }

    if (beginLower != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " serv.beginTime >= :serv_begin_lower_param" + postfix;
      parameters.put("serv_begin_lower_param" + postfix, beginLower);
    }

    if (beginUpper != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " serv.beginTime <= :serv_begin_upper_param" + postfix;
      parameters.put("serv_begin_upper_param" + postfix, beginUpper);
    }

    if (endLower != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " serv.endTime >= :serv_end_lower_param" + postfix;
      parameters.put("serv_end_lower_param" + postfix, endLower);
    }

    if (endUpper != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " serv.endTime <= :serv_end_upper_param" + postfix;
      parameters.put("serv_end_upper_param" + postfix, endUpper);
    }

    if (priceLower != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " serv.price >= :serv_price_lower_param" + postfix;
      parameters.put("serv_price_lower_param" + postfix, priceLower);
    }

    if (priceUpper != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " serv.price <= :serv_price_upper_param" + postfix;
      parameters.put("serv_price_upper_param" + postfix, priceUpper);
    }

    return queryString;
  }

}
