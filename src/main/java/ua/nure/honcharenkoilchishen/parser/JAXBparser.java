package ua.nure.honcharenkoilchishen.parser;

import ua.nure.honcharenkoilchishen.entity.faculty.Faculties;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JAXBparser implements FacultyParser {
    private static final String FILE_NAME = "test.xml";
    public Faculties unmarshall() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Faculties.class);
        return (Faculties) context.createUnmarshaller()
                .unmarshal(new FileReader("./" + FILE_NAME ));
    }

    public void marshall(Faculties faculties) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Faculties.class);
        Marshaller mar = context.createMarshaller();
        mar.marshal(faculties, new File("./" + FILE_NAME ));
    }
}
