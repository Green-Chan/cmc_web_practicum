package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.implementations.ServiceTypeDAOImpl;
import dao.interfaces.ServiceTypeDAO;
import objects.ServiceType;

@Controller
public class ServiceTypeController extends CommonController<ServiceType, String, ServiceTypeDAO> {

  ServiceTypeController() {
    dao = new ServiceTypeDAOImpl();
  }

  @RequestMapping(value = "/service_types", method = RequestMethod.GET)
  public ModelAndView serviceTypes(@RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "info", required = false) String info) {

    name = prepareString(name);
    info = prepareString(info);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("serviceTypes", dao.findByAll(name, info));
    modelAndView.addObject("searchName", name);
    modelAndView.addObject("searchInfo", info);
    modelAndView.setViewName("serviceTypes");
    return modelAndView;
  }

  @RequestMapping(value = "/service_type", method = RequestMethod.GET)
  public ModelAndView serviceType(@RequestParam(name = "id", required = true) String id) {
    id = prepareString(id);
    return findById(id, "serviceType");
  }

  @RequestMapping(value = "/edit_service_type", method = RequestMethod.GET)
  public ModelAndView editServiceType(@RequestParam(name = "id", required = false) String id) {
    id = prepareString(id);
    if (id == null) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("theObject", new ServiceType());
      modelAndView.setViewName("editServiceType");
      return modelAndView;
    } else {
      return findById(id, "editServiceType");
    }
  }

  @RequestMapping(value = "/save_service_type", method = RequestMethod.POST)
  public ModelAndView saveServiceType(@RequestParam(name = "oldId", required = false) String oldId,
      @RequestParam(name = "id", required = true) String id, @RequestParam(name = "name", required = true) String name,
      @RequestParam(name = "info", required = true) String info) {

    oldId = prepareString(oldId);
    id = prepareString(id);
    name = prepareString(name);
    info = prepareString(info);

    if (oldId == null) {
      save(new ServiceType(id, name, info));
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("redirect:service_type?id=" + encodeToUTF8(id));
      return modelAndView;
    } else {
      assert (id == oldId);
      return update(new ServiceType(id, name, info), "redirect:service_type?id=" + encodeToUTF8(id));
    }
  }

  @RequestMapping(value = "/delete_service_type", method = RequestMethod.POST)
  public ModelAndView deleteServiceType(@RequestParam(name = "id", required = true) String id) {
    id = prepareString(id);
    return delete(id, "redirect:/");
  }

}