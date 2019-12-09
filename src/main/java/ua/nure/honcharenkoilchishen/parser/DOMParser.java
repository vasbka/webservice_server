package ua.nure.honcharenkoilchishen.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculties;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculty;
import ua.nure.honcharenkoilchishen.entity.faculty.FacultyBranch;

public class DOMParser {
    private static final String FILE_NAME = "test.xml";
    public static final String NM_SP = "http://nure.ua/honcharenkoilchishen/faculty";

    public void marshall(Faculties faculties) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = sf.newSchema(new File("src/main/resources/faculty.xsd"));
            factory.setSchema(schema);
            factory.setNamespaceAware(true);
        } catch (SAXException e) {
            e.printStackTrace();
        }

        try {
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element facultiesElement = doc.createElementNS(NM_SP, "faculties");
            doc.appendChild(facultiesElement);

            faculties.getFaculty().forEach(faculty -> facultiesElement.appendChild(prepareFaculty(faculty, doc)));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_NAME));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws Exception {
//        DOMParser domParser = new DOMParser();
//        Faculties faculties = new Faculties();
//        Faculty faculty = new Faculty();
//        faculty.setTitle("hello");
//        faculty.setId(15);
//        faculty.setShortTitle("short title");
//        faculty.setPlaceCount(15);
//        faculty.setBudgetPlaceCount(10);
//        faculty.setFacultyBranch(FacultyBranch.MATHMETICAL);
//        Faculty.FacultyRoomNumber facultyRoomNumber = new Faculty.FacultyRoomNumber();
//        facultyRoomNumber.setYes("412");
//        faculty.setFacultyRoomNumber(facultyRoomNumber);
//        Faculty.Additional additional = new Faculty.Additional();
//        Faculty.Additional.BacheloerCount value = new Faculty.Additional.BacheloerCount();
//        value.setYear(2011);
//        value.setValue(new BigInteger("15"));
//        additional.setBacheloerCount(value);
//        additional.setEmployedStudents(new BigInteger("20"));
//        faculty.setAdditional(additional);
//        faculties.setFaculty(Collections.singletonList(faculty));
//        domParser.marshall(faculties);
//    }

    public Element prepareFaculty(Faculty faculty, Document root) {
        Element facultyElement = root.createElementNS(NM_SP, "faculty");
        facultyElement.setAttribute("id", String.valueOf(faculty.getId()));
        facultyElement.appendChild(getSimpleElement(root, NM_SP, "title", faculty.getTitle()));
        facultyElement.appendChild(getSimpleElement(root, NM_SP, "shortTitle", faculty.getShortTitle()));
        facultyElement.appendChild(getSimpleElement(root, NM_SP, "placeCount", faculty.getPlaceCount()));
        facultyElement.appendChild(getSimpleElement(root, NM_SP, "budgetPlaceCount", faculty.getBudgetPlaceCount()));
        facultyElement.appendChild(getSimpleElement(root, NM_SP, "facultyBranch", faculty.getFacultyBranch().value()));
        Element facultyRoomNumber = getSimpleElementWithoutValue(root, NM_SP, "facultyRoomNumber");
        if (faculty.getFacultyRoomNumber().getNo() != null) {
            facultyRoomNumber.appendChild(getSimpleElementWithoutValue(root, NM_SP, "no"));
        } else {
            facultyRoomNumber.appendChild(getSimpleElement(root, NM_SP, "yes", faculty.getFacultyRoomNumber().getYes()));
        }
        facultyElement.appendChild(facultyRoomNumber);
        Faculty.Additional additional = faculty.getAdditional();
        if (additional != null) {
            Element additional1 = getSimpleElementWithoutValue(root, NM_SP, "additional");
            Faculty.Additional.BacheloerCount bacheloerCount = additional.getBacheloerCount();
            if (bacheloerCount != null) {
                Element bacheloerCountElemtn = null;
                if (bacheloerCount.getYear() != 0 && bacheloerCount.getValue() != null) {
                    bacheloerCountElemtn
                            = getSimpleElement(root, NM_SP, "bacheloerCount", bacheloerCount.getValue());
                    bacheloerCountElemtn.setAttribute("year" , String.valueOf(bacheloerCount.getYear()));
                }
                if (bacheloerCountElemtn != null) {
                    additional1.appendChild(bacheloerCountElemtn);
                }
            }
            BigInteger employedStudents = additional.getEmployedStudents();
            if (employedStudents != null) {
                additional1.appendChild(getSimpleElement(root, NM_SP, "employedStudents", employedStudents));
            }
            facultyElement.appendChild(additional1);

        }
        return facultyElement;
    }

    private Element getSimpleElementWithoutValue(Document doc, String ns, String tagName) {
        return doc.createElementNS(ns, tagName);
    }

    private Element getSimpleElement(Document doc, String ns, String tagName, Object value) {
        Element element = doc.createElementNS(ns, tagName);
        element.setTextContent(String.valueOf(value));
        return element;
    }


    public Faculties unmarshall() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document root = db.parse(new FileInputStream(FILE_NAME));
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

}