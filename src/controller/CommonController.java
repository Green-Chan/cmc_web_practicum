package controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import dao.interfaces.CommonDAO;
import objects.Service;

public class CommonController<T, K, D extends CommonDAO<T, K>> {
  protected D dao;

  protected String prepareString(String str) {
    if (str != null && str.isEmpty()) {
      str = null;
    }
    if (str != null) {
      try {
        str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
      } catch (UnsupportedEncodingException e) {
        System.out.println("Encoding problem on value: " + str);
        e.printStackTrace();
      }
    }
    return str;
  }

  protected Date prepareDate(String str) {
    if (str == null || str.isEmpty()) {
      return null;
    }
    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Date date = null;
    if (str != null && !str.isEmpty()) {
      try {
        date = format.parse(str);
      } catch (ParseException e) {
        System.out.println("Date parse exception on value: " + str);
        e.printStackTrace();
      }
    }
    return date;
  }

  protected String dateToString(Date date) {
    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    if (date == null) {
      return null;
    }
    return format.format(date);
  }

  protected String encodeToUTF8(String str) {
    try {
      str = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException e) {
      System.out.println("Unexpected encoding Problem on value: " + str);
      System.out.println(e);
    }
    return str;
  }

  protected ModelAndView findById(K id, String viewName) {
    ModelAndView modelAndView = new ModelAndView();
    T foundObject = dao.findById(id);
    if (foundObject == null) {
      modelAndView.setViewName("error");
    } else {
      modelAndView.addObject("theObject", foundObject);
      if (foundObject != null && foundObject instanceof Service) {
        Service service = (Service) foundObject;
        modelAndView.addObject("beginDateStr", dateToString(service.getBeginTime()));
        modelAndView.addObject("endDateStr", dateToString(service.getEndTime()));
      }
      modelAndView.setViewName(viewName);
    }
    return modelAndView;
  }

  protected void save(T objectToSave) {
    dao.save(objectToSave);
  }

  protected ModelAndView update(T objectToSave, String viewName) {
    ModelAndView modelAndView = new ModelAndView();
    dao.update(objectToSave);
    modelAndView.setViewName(viewName);
    return modelAndView;
  }

  protected ModelAndView delete(K id, String viewName) {
    ModelAndView modelAndView = new ModelAndView();
    T objectToDelete = dao.findById(id);
    dao.delete(objectToDelete);
    modelAndView.setViewName(viewName);
    return modelAndView;
  }
}
