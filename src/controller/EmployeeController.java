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
import dao.implementations.EmployeeDAOImpl;
import dao.interfaces.EmployeeDAO;
import objects.Employee;
import objects.EmployeeContact;
import types.ContactType;

@Controller
public class EmployeeController extends CommonController<Employee, Integer, EmployeeDAO> {

  EmployeeController() {
    dao = new EmployeeDAOImpl();
  }

  @RequestMapping(value = "/employees", method = RequestMethod.GET)
  public ModelAndView employees(@RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "position", required = false) String position,
      @RequestParam(name = "education", required = false) String education,
      @RequestParam(name = "employeeContact", required = false) String employeeContact,
      @RequestParam(name = "serviceTypeId", required = false) String serviceTypeId,
      @RequestParam(name = "clientName", required = false) String clientName,
      @RequestParam(name = "beginLowerTime", required = false) String beginLowerTimeStr,
      @RequestParam(name = "endUpperTime", required = false) String endUpperTimeStr) {

    name = prepareString(name);
    position = prepareString(position);
    education = prepareString(education);
    employeeContact = prepareString(employeeContact);
    serviceTypeId = prepareString(serviceTypeId);
    clientName = prepareString(clientName);

    Date beginLowerDate = prepareDate(beginLowerTimeStr);
    Date endUpperDate = prepareDate(endUpperTimeStr);

    ModelAndView modelAndView = new ModelAndView();

    EmployeeSearchForm employeeForm = new EmployeeSearchForm(name, position, education, null, employeeContact);
    ClientSearchForm clientForm = new ClientSearchForm(null, clientName, null, null, null, null);
    ServiceSearchForm serviceForm = new ServiceSearchForm(serviceTypeId, null, beginLowerDate, null, null, endUpperDate,
        null, null);

    modelAndView.addObject("employees",
        dao.findByAll(employeeForm, Arrays.asList(serviceForm), Arrays.asList(clientForm)));
    modelAndView.addObject("name", name);
    modelAndView.addObject("position", position);
    modelAndView.addObject("education", education);
    modelAndView.addObject("employeeContact", employeeContact);
    modelAndView.addObject("serviceTypeId", serviceTypeId);
    modelAndView.addObject("clientName", clientName);
    modelAndView.addObject("beginLowerTimeStr", beginLowerTimeStr);
    modelAndView.addObject("endUpperTimeStr", endUpperTimeStr);
    modelAndView.setViewName("employees");
    return modelAndView;
  }

  @RequestMapping(value = "/employee", method = RequestMethod.GET)
  public ModelAndView employee(@RequestParam(name = "id", required = true) Integer id) {
    return findById(id, "employee");
  }

  @RequestMapping(value = "/edit_employee", method = RequestMethod.GET)
  public ModelAndView editEmployee(@RequestParam(name = "id", required = false) Integer id) {
    if (id == null) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("theObject", new Employee());
      modelAndView.setViewName("editEmployee");
      return modelAndView;
    } else {
      return findById(id, "editEmployee");
    }
  }

  @RequestMapping(value = "/save_employee", method = RequestMethod.POST)
  public ModelAndView saveEmployee(@RequestParam(name = "id", required = true) Integer id,
      @RequestParam(name = "name", required = true) String name,
      @RequestParam(name = "position", required = true) String position,
      @RequestParam(name = "education", required = true) String education,
      @RequestParam(name = "contactNum", required = true) int contactNum,
      @RequestParam Map<String, String> allQueryParams) {
    name = prepareString(name);
    position = prepareString(position);
    education = prepareString(education);
    List<EmployeeContact> contacts = new ArrayList<EmployeeContact>(Arrays.asList());
    for (int i = 0; i < contactNum; i++) {
      String strType = allQueryParams.get("contactType" + i);
      if (!strType.equals("null")) {
        contacts.add(new EmployeeContact(null, ContactType.valueOf(strType),
            prepareString(allQueryParams.get("contactValue" + i))));
      }
    }

    Employee employee;
    if (id == 0) {
      employee = new Employee();
    } else {
      employee = dao.findById(id);
    }
    employee.setName(name);
    employee.setPosition(position);
    employee.setEducation(education);
    for (EmployeeContact contact : contacts) {
      contact.setEmployee(employee);
    }
    employee.setContacts(contacts);

    if (id == 0) {
      save(employee);
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("redirect:employee?id=" + employee.getId());
      return modelAndView;
    } else {
      employee.setId(id);
      return update(employee, "redirect:employee?id=" + id);
    }
  }

  @RequestMapping(value = "/delete_employee", method = RequestMethod.POST)
  public ModelAndView deleteEmployee(@RequestParam(name = "id", required = true) int id) {
    return delete(id, "redirect:/");
  }

}