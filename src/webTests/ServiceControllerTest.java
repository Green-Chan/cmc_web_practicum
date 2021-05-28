package webTests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

public class ServiceControllerTest {
  @Test
  public void ServiceTest() throws IOException, SAXException {
    WebConversation wc = new WebConversation();
    WebResponse resp1 = wc.getResponse("http://localhost:8080/edit_service_type");
    WebForm forms[] = resp1.getForms();
    forms[0].setParameter("id", "T");
    forms[0].setParameter("name", "Test name");
    resp1 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("save")));

    WebResponse resp3 = wc.getResponse("http://localhost:8080/edit_service");
    forms = resp3.getForms();
    assertEquals(forms[0].getAction(), "/save_service");
    forms[0].setParameter("serviceTypeId", "T");
    forms[0].setParameter("clientId", "1");
    forms[0].setParameter("beginTimeStr", "27-05-21");
    forms[0].setParameter("price", "15000");
    resp3 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("save")));

    WebResponse resp2 = wc.getResponse("http://localhost:8080/services");
    forms = resp2.getForms();
    assertEquals(forms[0].getAction(), "");
    forms[0].setParameter("serviceTypeId", "T");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("search")));
    WebLink links[] = resp2.getLinks();
    assertEquals(links.length, 10);
    resp2 = wc.getResponse("http://localhost:8080/" + links[9].getURLString());
    assertEquals(resp2.getTitle(), resp3.getTitle());
    assertEquals(resp2.getLinks().length, 11);
    forms = resp2.getForms();
    assertEquals(forms[0].getAction(), "/edit_service");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("edit")));
    forms = resp2.getForms();
    assertEquals(forms[0].getAction(), "/save_service");
    forms[0].setParameter("employeeId2", "2");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("save")));
    assertEquals(resp2.getLinks().length, 12);
    forms = resp2.getForms();
    assertEquals(forms[1].getAction(), "/delete_service");
    resp2 = wc.getResponse(forms[1].getRequest(forms[1].getSubmitButton("delete")));
    resp2 = wc.getResponse("http://localhost:8080/services");
    forms = resp2.getForms();
    forms[0].setParameter("serviceTypeId", "T");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("search")));
    links = resp2.getLinks();
    assertEquals(links.length, 9);
  }
}
