package ua.nure.honcharenkoilchishen.parser;

import ua.nure.honcharenkoilchishen.entity.faculty.Faculties;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculty;
import ua.nure.honcharenkoilchishen.entity.faculty.FacultyBranch;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

public class JAXBparser implements FacultyParser {
//    public static void main(String[] args) throws Exception {
//        JAXBparser jaxBparser = new JAXBparser();
//        Faculty faculty = new Faculty();
//        Faculties faculties = new Faculties();
//        faculty.setId(1);
//        faculty.setTitle("he");
//        faculty.setShortTitle("short");
//        faculty.setBudgetPlaceCount(15);
//        faculty.setPlaceCount(25);
//        Faculty.FacultyRoomNumber facultyRoomNumber = new Faculty.FacultyRoomNumber();
//        facultyRoomNumber.setYes("412");
//        faculty.setFacultyRoomNumber(facultyRoomNumber);
//        Faculty.Additional additional = new Faculty.Additional();
//        Faculty.Additional.BacheloerCount bacheloerCount = new Faculty.Additional.BacheloerCount();
//        bacheloerCount.setYear(2015);
//        bacheloerCount.setValue(new BigInteger("15"));
//        additional.setBacheloerCount(bacheloerCount);
//        faculty.setAdditional(additional);
//        faculty.setFacultyBranch(FacultyBranch.PHILOLOGY);
//        faculties.setFaculty(Collections.singletonList(faculty));
//        Faculties unmarshall = jaxBparser.unmarshall();
//        System.out.println(unmarshall);
////        jaxBparser.marshall(faculties);
//    }
    private static final String FILE_NAME = "test.xml";
    public Faculties unmarshall() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Faculties.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Faculties)unmarshaller
                .unmarshal(new FileReader("./" + FILE_NAME));
    }

    public void marshall(Faculties faculties) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Faculties.class);
        Marshaller mar = context.createMarshaller();
        mar.marshal(faculties, new File("./" + FILE_NAME ));
    }
}
