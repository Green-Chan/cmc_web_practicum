package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.forms.ClientSearchForm;
import dao.forms.EmployeeSearchForm;
import dao.forms.ServiceSearchForm;
import dao.implementations.ClientDAOImpl;
import dao.interfaces.ClientDAO;
import objects.Client;
import objects.ClientContact;
import objects.ClientContactPerson;
import types.ClientType;
import types.ContactType;

@Controller
public class ClientController extends CommonController<Client, Integer, ClientDAO> {

  ClientController() {
    dao = new ClientDAOImpl();
  }

  @RequestMapping(value = "/clients", method = RequestMethod.GET)
  public ModelAndView clients(@RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "clientTypeStr", required = false) String clientTypeStr,
      @RequestParam(name = "clientContact", required = false) String clientContact,
      @RequestParam(name = "serviceTypeId", required = false) String serviceTypeId,
      @RequestParam(name = "employeeName", required = false) String employeeName,
      @RequestParam(name = "beginLowerTime", required = false) String beginLowerTimeStr,
      @RequestParam(name = "endUpperTime", required = false) String endUpperTimeStr) {

    name = prepareString(name);
    clientContact = prepareString(clientContact);
    serviceTypeId = prepareString(serviceTypeId);
    employeeName = prepareString(employeeName);
    ClientType clientType;
    if (clientTypeStr == null || clientTypeStr.equals("both")) {
      clientType = null;
    } else {
      clientType = ClientType.valueOf(clientTypeStr);
    }

    Date beginLowerDate = prepareDate(beginLowerTimeStr);
    Date endUpperDate = prepareDate(endUpperTimeStr);

    ModelAndView modelAndView = new ModelAndView();

    ClientSearchForm clientForm = new ClientSearchForm(clientType, name, null, clientContact, null, null);
    EmployeeSearchForm employeeForm = new EmployeeSearchForm(employeeName, null, null, null, null);
    ServiceSearchForm serviceForm = new ServiceSearchForm(serviceTypeId, null, beginLowerDate, null, null, endUpperDate,
        null, null);

    modelAndView.addObject("clients",
        dao.findByAll(clientForm, Arrays.asList(serviceForm), Arrays.asList(employeeForm)));
    modelAndView.addObject("name", name);
    modelAndView.addObject("clientTypeStr", clientTypeStr);
    modelAndView.addObject("clientContact", clientContact);
    modelAndView.addObject("serviceTypeId", serviceTypeId);
    modelAndView.addObject("clientName", employeeName);
    modelAndView.addObject("beginLowerTimeStr", beginLowerTimeStr);
    modelAndView.addObject("endUpperTimeStr", endUpperTimeStr);
    modelAndView.setViewName("clients");
    return modelAndView;
  }

  @RequestMapping(value = "/client", method = RequestMethod.GET)
  public ModelAndView client(@RequestParam(name = "id", required = true) Integer id) {
    return findById(id, "client");
  }

  @RequestMapping(value = "/edit_client", method = RequestMethod.GET)
  public ModelAndView editClient(@RequestParam(name = "id", required = false) Integer id) {
    if (id == null) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("theObject", new Client());
      modelAndView.setViewName("editClient");
      return modelAndView;
    } else {
      return findById(id, "editClient");
    }
  }

  @RequestMapping(value = "/save_client", method = RequestMethod.POST)
  public ModelAndView saveClient(@RequestParam(name = "id", required = true) Integer id,
      @RequestParam(name = "name", required = true) String name,
      @RequestParam(name = "clientType", required = true) ClientType clientType,
      @RequestParam(name = "contactNum", required = true) int contactNum,
      @RequestParam(name = "cpNum", required = true) int cpNum, @RequestParam Map<String, String> allQueryParams) {
    name = prepareString(name);
    List<ClientContact> contacts = new ArrayList<ClientContact>(Arrays.asList());
    for (int i = 0; i < contactNum; i++) {
      String strType = allQueryParams.get("contactType" + i);
      if (!strType.equals("null")) {
        contacts.add(new ClientContact(null, ContactType.valueOf(strType),
            prepareString(allQueryParams.get("contactValue" + i))));
      }
    }
    List<ClientContactPerson> cps = new ArrayList<ClientContactPerson>(Arrays.asList());
    for (int i = 0; i < cpNum; i++) {
      String cpName = prepareString(allQueryParams.get("cpName" + i));
      String cpPhone = prepareString(allQueryParams.get("cpPhone" + i));
      if (cpName != null || cpPhone != null) {
        cps.add(new ClientContactPerson(null, cpName, cpPhone));
      }
    }
    Client client = new Client(clientType, name);
    for (ClientContact contact : contacts) {
      contact.setClient(client);
    }
    for (ClientContactPerson cp : cps) {
      cp.setClient(client);
    }
    client.setContacts(contacts);
    client.setContactPersons(cps);

    if (id == 0) {
      save(client);
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("redirect:client?id=" + client.getId());
      return modelAndView;
    } else {
      client.setId(id);
      return update(client, "redirect:client?id=" + id);
    }
  }

  @RequestMapping(value = "/delete_client", method = RequestMethod.POST)
  public ModelAndView deleteClient(@RequestParam(name = "id", required = true) int id) {
    return delete(id, "redirect:/");
  }

}