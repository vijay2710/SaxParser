import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SaxParser {

  public static void main(String[] args) throws Exception {
    SAXParserFactory parserFactor = SAXParserFactory.newInstance();
    SAXParser parser = parserFactor.newSAXParser();
    SAXHandler handler = new SAXHandler();
    String file = "Parser.xml";
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream iStream = classLoader.getResourceAsStream(file);
    parser.parse(iStream, handler);

 

    //Printing the list of employees obtained from XML

    for ( Employee emp : handler.empList){
      System.out.println(emp);
    }
  }
}

/**

 * The Handler for SAX Events.

 */

class SAXHandler extends DefaultHandler {
  List<Employee> empList = new ArrayList<>();
  Employee emp = null;
  String content = null;

  @Override

  //Triggered when the start of tag is found.

  public void startElement(String uri, String localName,String qName, Attributes attributes)throws SAXException {

 

    switch(qName){

      //Create a new Employee object when the start tag is found

      case "employee":
      emp = new Employee();
      emp.id = attributes.getValue("id");
        break;
    }
  }

  @Override
  public void endElement(String uri, String localName,String qName) throws SAXException {
   switch(qName){
     //Add the employee to list once end tag is found
     case "employee":
       empList.add(emp);      
       break;

     //For all other end tags the employee has to be updated.
     case "firstName":
       emp.firstName = content;
       break;

     case "lastName":
       emp.lastName = content;
       break;

     case "Age":
       emp.Age = content;
       break;
       
     case "Salary":
    	 emp.Salary = content;
    	 break;

   }
  }

  @Override

  public void characters(char[] ch, int start, int length)throws SAXException {
    content = String.copyValueOf(ch, start, length).trim();
  }
}
class Employee {
  String id;
  String firstName;
  String lastName;
  String Age;
  String Salary;
  @Override
  public String toString() {
    return "(" + id + ")" + firstName + " " + lastName + " " + Age + " " + Salary;
  }
}
