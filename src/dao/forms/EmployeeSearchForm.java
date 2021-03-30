package dao.forms;

import java.util.Map;

import types.ContactType;

public class EmployeeSearchForm {
  private String name;
  private String position;
  private String education;
  private ContactType contactType;
  private String contact;

  public EmployeeSearchForm(String name, String position, String education, ContactType contactType, String contact) {
    super();
    this.name = name;
    this.position = position;
    this.education = education;
    this.contactType = contactType;
    this.contact = contact;
  }

  public String getQueryString(String postfix, Map<String, Object> parameters, boolean selectTask) {
    String queryString = "";
    if (selectTask) {
      queryString += " FROM Task task JOIN task.id.employee empl";
    } else {
      queryString += " FROM Employee empl";
    }
    boolean firstCondition = true;

    if (name != null) {
      queryString += " WHERE";
      firstCondition = false;

      queryString += " empl.name LIKE :serv_name_param" + postfix;
      parameters.put("serv_name_param" + postfix, "%" + name + "%");
    }

    if (position != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " empl.position LIKE :serv_position_param" + postfix;
      parameters.put("serv_position_param" + postfix, "%" + position + "%");
    }

    if (education != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " empl.education LIKE :serv_education_param" + postfix;
      parameters.put("serv_education_param" + postfix, "%" + education + "%");
    }

    if (contactType != null || contact != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " empl in (SELECT con.employee FROM EmployeeContact con WHERE";

      if (contactType != null) {
        queryString += " con.contactType = :empl_con_type_param" + postfix;
        parameters.put("empl_con_type_param" + postfix, contactType);
        if (contact != null) {
          queryString += " AND";
        }
      }
      if (contact != null) {
        queryString += " con.contact LIKE :empl_con_contact_param" + postfix;
        parameters.put("empl_con_contact_param" + postfix, "%" + contact + "%");
      }
      queryString += ")";
    }

    return queryString;
  }

}
