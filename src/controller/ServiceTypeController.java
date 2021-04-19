package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ServiceTypeController {
  // @Autowired
  // private ServiceTypeDAO serviceTypeDAO;

  @RequestMapping(value = "/service_types", method = RequestMethod.GET)
  public ModelAndView serviceTypes() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("serviceTypes", null);
    // modelAndView.setViewName("ServiceTypes");
    // TODO: file /WEB-INF/views/ServiceTypes.jsp
    return modelAndView;
  }

}