package webTests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

public class EmployeeControllerTest {
  @Test
  public void ClientTest() throws IOException, SAXException {
    WebConversation wc = new WebConversation();
    WebResponse resp1 = wc.getResponse("http://localhost:8080/edit_employee");
    WebForm forms[] = resp1.getForms();
    forms[0].setParameter("name", "Test name");
    forms[0].setParameter("position", "Test position");
    forms[0].setParameter("education", "Test education");
    resp1 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("save")));

    WebResponse resp2 = wc.getResponse("http://localhost:8080/employees");
    forms = resp2.getForms();
    assertEquals(forms[0].getAction(), "");
    forms[0].setParameter("name", "Test");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("search")));
    WebLink links[] = resp2.getLinks();
    assertEquals(links.length, 10);
    resp2 = wc.getResponse("http://localhost:8080/" + links[9].getURLString());
    assertEquals(resp2.getTitle(), resp1.getTitle());
    forms = resp2.getForms();
    assertEquals(forms[0].getAction(), "/edit_employee");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("edit")));
    forms = resp2.getForms();
    assertEquals(forms[0].getAction(), "/save_employee");
    forms[0].setParameter("name", "New name");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("save")));
    assertEquals(resp2.getTitle(), "New name");
    forms = resp2.getForms();
    assertEquals(forms[1].getAction(), "/delete_employee");
    resp2 = wc.getResponse(forms[1].getRequest(forms[1].getSubmitButton("delete")));
    resp2 = wc.getResponse("http://localhost:8080/clients");
    forms = resp2.getForms();
    forms[0].setParameter("name", "est");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("search")));
    links = resp2.getLinks();
    assertEquals(links.length, 9);
  }
}
