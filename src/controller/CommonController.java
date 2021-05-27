package controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.servlet.ModelAndView;

import dao.interfaces.CommonDAO;

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
        System.out.println("Unexpected encoding Problem on value: " + str);
        System.out.println(e);
      }
    }
    return str;
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
