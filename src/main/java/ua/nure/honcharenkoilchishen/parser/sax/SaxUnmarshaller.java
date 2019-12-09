package ua.nure.honcharenkoilchishen.parser.sax;

import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculties;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculty;
import ua.nure.honcharenkoilchishen.entity.faculty.FacultyBranch;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SaxUnmarshaller implements ContentHandler {
    private Faculties faculties = new Faculties();
    private Faculty currentFaculty;
    private FacultiesTagEnum currentTag;
    private Faculty.FacultyRoomNumber currentFacultyRoomNumber;
    private Faculty.Additional currentAdditional;
    private List<FacultiesTagEnum> tags;
    private Faculty.Additional.BacheloerCount currentBacheloerCount;

    public Faculties unmarshal(String filePath) {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(this);
            reader.parse(filePath);
        }catch (SAXException | IOException e) {}
        return faculties;
    }

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {
        tags = new ArrayList<>();

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        tags.add(FacultiesTagEnum.getInstance(localName, uri));
        currentTag = tags.get(tags.size() - 1);
        if (currentTag.isFaculty()) {
            this.currentFaculty = new Faculty();
            currentFaculty.setId(Integer.parseInt(atts.getValue("id")));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentTag.isFaculty()) {
            this.faculties.getFaculty().add(currentFaculty);
            this.currentFaculty = null;
        }

        tags.remove(tags.size() - 1);
        currentTag = tags.size() > 0 ? tags.get(tags.size() - 1) : null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch, start, length);
        if (currentTag.isTitle()) {
            currentFaculty.setTitle(str);
        } else if (currentTag.isShortTitle()) {
            currentFaculty.setShortTitle(str);
        } else if (currentTag.isPlaceCount()) {
            currentFaculty.setPlaceCount(Integer.parseInt(str));
        } else if (currentTag.isBudgetPlaceCount()) {
            currentFaculty.setBudgetPlaceCount(Integer.parseInt(str));
        } else if (currentTag.isFacultyBranch()) {
            currentFaculty.setFacultyBranch(FacultyBranch.fromValue(str));
        } else if (currentTag.isFacultyRoomNumber()) {
            currentFacultyRoomNumber = new Faculty.FacultyRoomNumber();
            currentFaculty.setFacultyRoomNumber(currentFacultyRoomNumber);
        } else if (currentTag.isNo()) {
            currentFacultyRoomNumber.setNo("");
        } else if (currentTag.isYes()) {
            currentFacultyRoomNumber.setYes(str);
        } else if (currentTag.isAdditional()) {
            currentAdditional = new Faculty.Additional();
            currentFaculty.setAdditional(currentAdditional);
        } else if (currentTag.isBacheloerCount()) {
            currentBacheloerCount = new Faculty.Additional.BacheloerCount();
            currentBacheloerCount.setValue(new BigInteger(str));
            currentFaculty.getAdditional().setBacheloerCount(currentBacheloerCount);
        } else if (currentTag.isYear()) {
            currentBacheloerCount.setYear(Integer.parseInt(str));
        } else if (currentTag.isEmployedStudents()) {
            currentFaculty.getAdditional().setEmployedStudents(new BigInteger(str));
        }
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    @Override
    public void skippedEntity(String name) throws SAXException {

    }
}
