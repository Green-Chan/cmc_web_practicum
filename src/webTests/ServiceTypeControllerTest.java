package webTests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

public class ServiceTypeControllerTest {
  @Test
  public void bigTest() throws IOException, SAXException {
    WebConversation wc = new WebConversation();
    WebResponse resp1 = wc.getResponse("http://localhost:8080/edit_service_type");
    assertEquals(resp1.getTitle(), "Добавить тип услуги");
    WebForm forms[] = resp1.getForms();
    assertEquals(forms[0].getAction(), "/save_service_type");
    forms[0].setParameter("id", "T");
    forms[0].setParameter("name", "Test name");
    resp1 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("save")));
    WebResponse resp2 = wc.getResponse("http://localhost:8080/service_types");
    forms = resp2.getForms();
    assertEquals(forms[0].getAction(), "");
    forms[0].setParameter("name", "Test");
    resp2 = wc.getResponse(forms[0].getRequest(forms[0].getSubmitButton("save")));
    WebLink links[] = resp2.getLinks();
    assertEquals(links.length, 10);
    resp2 = wc.getResponse("http://localhost:8080/" + links[9].getURLString());
    assertEquals(resp2.getTitle(), resp1.getTitle());
  }
}
