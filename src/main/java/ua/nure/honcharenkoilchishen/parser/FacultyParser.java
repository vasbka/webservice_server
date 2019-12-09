package ua.nure.honcharenkoilchishen.parser;

import ua.nure.honcharenkoilchishen.entity.faculty.Faculties;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface FacultyParser {
    Faculties unmarshall() throws JAXBException, IOException;

    void marshall(Faculties faculties) throws Exception;
}
