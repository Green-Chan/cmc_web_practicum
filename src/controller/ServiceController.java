package controller;

import java.math.BigDecimal;
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
import dao.implementations.EmployeeDAOImpl;
import dao.implementations.ServiceDAOImpl;
import dao.implementations.ServiceTypeDAOImpl;
import dao.interfaces.ClientDAO;
import dao.interfaces.EmployeeDAO;
import dao.interfaces.ServiceDAO;
import dao.interfaces.ServiceTypeDAO;
import objects.Client;
import objects.Employee;
import objects.Service;
import objects.ServiceType;
import objects.Task;

@Controller
public class ServiceController extends CommonController<Service, Integer, ServiceDAO> {

  ServiceController() {
    dao = new ServiceDAOImpl();
  }

  @RequestMapping(value = "/services", method = RequestMethod.GET)
  public ModelAndView services(@RequestParam(name = "serviceTypeId", required = false) String serviceTypeId,
      @RequestParam(name = "clientName", required = false) String clientName,
      @RequestParam(name = "employeeName", required = false) String employeeName,
      @RequestParam(name = "beginLowerTime", required = false) String beginLowerTimeStr,
      @RequestParam(name = "beginUpperTime", required = false) String beginUpperTimeStr,
      @RequestParam(name = "endLowerTime", required = false) String endLowerTimeStr,
      @RequestParam(name = "endUpperTime", required = false) String endUpperTimeStr,
      @RequestParam(name = "priceLower", required = false) BigDecimal priceLower,
      @RequestParam(name = "priceUpper", required = false) BigDecimal priceUpper) {

    serviceTypeId = prepareString(serviceTypeId);
    clientName = prepareString(clientName);
    employeeName = prepareString(employeeName);
    Date beginLowerTime = prepareDate(beginLowerTimeStr);
    Date beginUpperTime = prepareDate(beginUpperTimeStr);
    Date endLowerTime = prepareDate(endLowerTimeStr);
    Date endUpperTime = prepareDate(endUpperTimeStr);

    ModelAndView modelAndView = new ModelAndView();

    ServiceSearchForm serviceForm = new ServiceSearchForm(serviceTypeId, null, beginLowerTime, beginUpperTime,
        endLowerTime, endUpperTime, priceLower, priceUpper);
    ClientSearchForm clientForm = new ClientSearchForm(null, clientName, null, null, null, null);
    EmployeeSearchForm employeeForm = new EmployeeSearchForm(employeeName, null, null, null, null);

    List<Service> services = dao.findByAll(serviceForm, Arrays.asList(employeeForm), Arrays.asList(clientForm));
    modelAndView.addObject("serviceTypeId", serviceTypeId);
    modelAndView.addObject("services", services);
    modelAndView.addObject("clientName", clientName);
    modelAndView.addObject("employeeName", employeeName);
    modelAndView.addObject("beginLowerTime", beginLowerTime);
    modelAndView.addObject("beginUpperTime", beginUpperTime);
    modelAndView.addObject("endLowerTime", endLowerTime);
    modelAndView.addObject("endUpperTime", endUpperTime);
    modelAndView.addObject("priceLower", priceLower);
    modelAndView.addObject("priceUpper", priceUpper);

    List<String> beginDateStr = new ArrayList<String>(Arrays.asList());
    List<String> endDateStr = new ArrayList<String>(Arrays.asList());
    for (Service service : services) {
      beginDateStr.add(dateToString(service.getBeginTime()));
      endDateStr.add(dateToString(service.getEndTime()));
    }
    modelAndView.addObject("beginDateStr", beginDateStr);
    modelAndView.addObject("endDateStr", endDateStr);

    modelAndView.setViewName("services");
    return modelAndView;
  }

  @RequestMapping(value = "/service", method = RequestMethod.GET)
  public ModelAndView service(@RequestParam(name = "id", required = true) Integer id) {
    return findById(id, "service");
  }

  @RequestMapping(value = "/edit_service", method = RequestMethod.GET)
  public ModelAndView editService(@RequestParam(name = "id", required = false) Integer id) {
    if (id == null) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("theObject", new Service());
      modelAndView.setViewName("editService");
      return modelAndView;
    } else {
      return findById(id, "editService");
    }
  }

  @RequestMapping(value = "/save_service", method = RequestMethod.POST)
  public ModelAndView saveService(@RequestParam(name = "id", required = true) Integer id,
      @RequestParam(name = "clientId", required = true) Integer clientId,
      @RequestParam(name = "serviceTypeId", required = true) String serviceTypeId,
      @RequestParam(name = "beginTimeStr", required = false) String beginTimeStr,
      @RequestParam(name = "endTimeStr", required = false) String endTimeStr,
      @RequestParam(name = "price", required = false) BigDecimal price,
      @RequestParam(name = "tasksNum", required = true) int tasksNum,
      @RequestParam Map<String, String> allQueryParams) {
    serviceTypeId = prepareString(serviceTypeId);
    Date beginTime = prepareDate(beginTimeStr);
    Date endTime = prepareDate(endTimeStr);

    List<Task> tasks = new ArrayList<Task>(Arrays.asList());
    EmployeeDAO edao = new EmployeeDAOImpl();
    ClientDAO cdao = new ClientDAOImpl();
    ServiceTypeDAO stdao = new ServiceTypeDAOImpl();
    Client client = cdao.findById(clientId);
    ServiceType serviceType = stdao.findById(serviceTypeId);
    for (int i = 0; i < tasksNum; i++) {
      String employeeIdStr = allQueryParams.get("employeeId" + i);
      Employee employee = null;
      if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
        Integer employeeId = Integer.decode(employeeIdStr);
        employee = edao.findById(employeeId);
      }
      if (employee != null) {
        tasks.add(new Task(null, employee, prepareString(allQueryParams.get("description" + i))));
      }
    }
    assert (tasks.size() == 0);

    Service service;
    if (id == 0) {
      service = new Service();
    } else {
      service = dao.findById(id);
    }
    service.setClient(client);
    service.setServiceType(serviceType);
    service.setBeginTime(beginTime);
    service.setEndTime(endTime);
    service.setPrice(price);
    for (Task task : tasks) {
      task.setService(service);
    }
    service.setTasks(tasks);

    if (id == 0) {
      save(service);
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("redirect:service?id=" + service.getId());
      return modelAndView;
    } else {
      service.setId(id);
      return update(service, "redirect:service?id=" + id);
    }
  }

  @RequestMapping(value = "/delete_service", method = RequestMethod.POST)
  public ModelAndView deleteService(@RequestParam(name = "id", required = true) int id) {
    return delete(id, "redirect:/");
  }

}