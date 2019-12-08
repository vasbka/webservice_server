package ua.nure.honcharenkoilchishen.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculties;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculty;
import ua.nure.honcharenkoilchishen.entity.faculty.FacultyBranch;

public class DOMParser {

    public Faculties parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document root = db.parse(in);
        NodeList faculty = root.getElementsByTagName("fc:faculty");
        Faculties faculties = new Faculties();
        for (int i = 0; i < faculty.getLength(); i++) {
            Node item = faculty.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) item;
                Faculty faculty1 = new Faculty();
                faculty1.setTitle(getTextContent(element, "fc:title"));
                faculty1.setShortTitle(getTextContent(element, "fc:shortTitle"));
                faculty1.setPlaceCount(Integer.valueOf(getTextContent(element, "fc:placeCount")));
                faculty1.setBudgetPlaceCount(Integer.valueOf(getTextContent(element, "fc:budgetPlaceCount")));
                FacultyBranch facultyBranch = FacultyBranch.fromValue(getTextContent(element, "fc:facultyBranch"));
                faculty1.setFacultyBranch(facultyBranch);
                Faculty.FacultyRoomNumber facultyRoomNumber = new Faculty.FacultyRoomNumber();
                String facultyRoomNumberIsPresent = getRoomNumberValue(element);
                if (facultyRoomNumberIsPresent.equals("no")) {
                    facultyRoomNumber.setNo("no");
                } else {
                    facultyRoomNumber.setNo("yes");
                }
                faculty1.setFacultyRoomNumber(facultyRoomNumber);
                NodeList childNodes = element.getElementsByTagName("fc:additional").item(0).getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node item1 = childNodes.item(j);
                    if (item1.getNodeType() == Node.ELEMENT_NODE) {
                        Faculty.Additional additional = new Faculty.Additional();
                        Faculty.Additional.BacheloerCount bacheloerCount = new Faculty.Additional.BacheloerCount();
                        bacheloerCount.setYear(Integer.valueOf(item1.getAttributes().getNamedItem("year").getTextContent()));
                        bacheloerCount.setValue(BigInteger.valueOf(Long.valueOf(item1.getFirstChild().getNodeValue())));
                        additional.setBacheloerCount(bacheloerCount);
                        additional.setEmployedStudents(new BigInteger(String.valueOf(0L)));
                        faculty1.setAdditional(additional);
                    }
                }
                faculties.addFaculty(faculty1);
            }
        }
        return faculties;

    }

    private String getRoomNumberValue(Element element) {
        return element.getElementsByTagName("fc:facultyRoomNumber").item(0).getChildNodes().item(1).getLocalName();
    }

    private String getTextContent(Element element, String attrName) {
        return element.getElementsByTagName(attrName).item(0).getTextContent();
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        System.out.println("--== DOM Parser ==--");
        DOMParser domParser = new DOMParser();
        InputStream in = new FileInputStream("faculty.xml");
        System.out.println(in);
        Faculties faculties = domParser.parse(in);
        System.out.println("====================================");
        System.out.println("Here is the Facultys: \n" + faculties);
        System.out.println("====================================");
    }
}