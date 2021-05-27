package webTests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class MainControllerTest {
  @Test
  public void simpleTest() throws IOException, SAXException {
    WebConversation wc = new WebConversation();
    WebResponse resp = wc.getResponse("http://localhost:8080/");
    assertEquals(resp.getTitle(), "Главная страница");
  }
}
