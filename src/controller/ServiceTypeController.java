package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.implementations.ServiceTypeDAOImpl;
import dao.interfaces.ServiceTypeDAO;

@Controller
public class ServiceTypeController {
  // @Autowired
  private ServiceTypeDAO serviceTypeDAO = new ServiceTypeDAOImpl();

  @RequestMapping(value = "/service_types", method = RequestMethod.GET)
  public ModelAndView serviceTypes() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("serviceTypes", serviceTypeDAO.findAll());
    modelAndView.setViewName("serviceTypes");
    return modelAndView;
  }

}