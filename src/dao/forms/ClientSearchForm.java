package dao.forms;

import java.util.Map;

import types.ClientType;
import types.ContactType;

public class ClientSearchForm {
  private ClientType type;
  private String name;
  private ContactType contactType;
  private String contact;
  private String contactPersonName;
  private String contactPersonPhone;

  public ClientSearchForm(ClientType type, String name, ContactType contactType, String contact,
      String contactPersonName, String contactPersonPhone) {
    super();
    this.type = type;
    this.name = name;
    this.contactType = contactType;
    this.contact = contact;
    this.contactPersonName = contactPersonName;
    this.contactPersonPhone = contactPersonPhone;
  }

  public ClientType getType() {
    return type;
  }

  public void setType(ClientType type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getContactPersonName() {
    return contactPersonName;
  }

  public void setContactPersonName(String contactPersonName) {
    this.contactPersonName = contactPersonName;
  }

  public String getContactPersonPhone() {
    return contactPersonPhone;
  }

  public void setContactPersonPhone(String contactPersonPhone) {
    this.contactPersonPhone = contactPersonPhone;
  }

  public String getQueryString(String postfix, Map<String, Object> parameters, boolean selectTask) {
    String queryString = "";
    if (selectTask) {
      queryString += " FROM Task task JOIN task.id.service.client cl";
    } else {
      queryString += " FROM Client cl";
    }

    boolean firstCondition = true;

    if (type != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " cl.type = :cl_type_param" + postfix;
      parameters.put("cl_type_param" + postfix, type);
    }

    if (name != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " cl.name LIKE :cl_name_param" + postfix;
      parameters.put("cl_name_param" + postfix, "%" + name + "%");
    }

    if (contactType != null || contact != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " cl in (SELECT con.client FROM ClientContact con WHERE";

      if (contactType != null) {
        queryString += " con.contactType = :cl_con_type_param" + postfix;
        parameters.put("cl_con_type_param" + postfix, contactType);
        if (contact != null) {
          queryString += " AND";
        }
      }
      if (contact != null) {
        queryString += " con.contact LIKE :cl_con_contact_param" + postfix;
        parameters.put("cl_con_contact_param" + postfix, "%" + contact + "%");
      }
      queryString += ")";
    }

    if (contactPersonName != null || contactPersonPhone != null) {
      if (firstCondition) {
        queryString += " WHERE";
        firstCondition = false;
      } else {
        queryString += " AND";
      }
      queryString += " cl in (SELECT cp.client FROM ClientContactPerson cp WHERE";

      if (contactPersonName != null) {
        queryString += " cp.name LIKE :cp_name_param" + postfix;
        parameters.put("cp_name_param" + postfix, "%" + contactPersonName + "%");
        if (contactPersonPhone != null) {
          queryString += " AND";
        }
      }
      if (contactPersonPhone != null) {
        queryString += " cp.phone LIKE :cp_phone_param" + postfix;
        parameters.put("cp_phone_param" + postfix, "%" + contactPersonPhone + "%");
      }
      queryString += ")";
    }

    return queryString;
  }

}
