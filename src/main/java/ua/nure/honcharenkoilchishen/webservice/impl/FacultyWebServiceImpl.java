package ua.nure.honcharenkoilchishen.webservice.impl;

import ua.nure.honcharenkoilchishen.entity.faculty.Faculties;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculty;
import ua.nure.honcharenkoilchishen.parser.DOMParser;
import ua.nure.honcharenkoilchishen.parser.JAXBparser;
import ua.nure.honcharenkoilchishen.webservice.FacultyWebService;

import javax.jws.WebService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


@WebService(endpointInterface = "ua.nure.honcharenkoilchishen.webservice.FacultyWebService",
targetNamespace = "http://nure.ua/honcharenkoilchishen/service", portName = "FacultyPort",
serviceName = "Faculties")
public class FacultyWebServiceImpl implements FacultyWebService {
    private static DOMParser domParser;
    private static JAXBparser jaxBparser;

    public FacultyWebServiceImpl() {
        jaxBparser = new JAXBparser();
        domParser = new DOMParser();
    }

    @Override
    public Faculty geFacultyByName(String title) throws Exception {
        Faculties parse = jaxBparser.unmarshall();
        Optional<Faculty> first = parse.getFaculty().stream()
                .filter(faculty -> faculty.getTitle().equals(title)).findFirst();
        return first.orElseGet(Faculty::new);
    }

    @Override
    public Faculty createFaculty(Faculty faculty) throws Exception {
        Faculties faculties = jaxBparser.unmarshall();
        faculties.getFaculty().add(faculty);
        jaxBparser.marshall(faculties);
        return faculty;
    }

    @Override
    public Boolean updateFacultyTitle(Faculty faculty) throws Exception {
        Faculties faculties = jaxBparser.unmarshall();
        AtomicBoolean isUpdated = new AtomicBoolean(false);

        faculties.getFaculty().forEach(xmlFaculty -> {
           if (xmlFaculty.getId() == faculty.getId()) {
               updateFaculty(faculty, xmlFaculty);
               isUpdated.set(true);
           }
        });
        jaxBparser.marshall(faculties);
        return isUpdated.get();
    }

    @Override
    public Faculty getFacultyById(Integer facultyId) throws Exception {
        Faculties faculties = jaxBparser.unmarshall();
        Optional<Faculty> first = faculties.getFaculty()
                .stream()
                .filter(faculty -> faculty.getId() == facultyId)
                .findFirst();
        return first.orElseGet(Faculty::new);
    }

    @Override
    public Faculty deleteFaculty(Integer facultyId) throws Exception {
        Faculties faculties = jaxBparser.unmarshall();
        Faculty deletedFaculty = getFacultyById(facultyId);
        if (deletedFaculty != null) {
            List<Faculty> listWithRemovedItem = faculties.getFaculty()
                    .stream()
                    .filter(faculty -> faculty.getId() != facultyId)
                    .collect(Collectors.toList());
            faculties.setFaculty(listWithRemovedItem);
            jaxBparser.marshall(faculties);
        }
        return deletedFaculty;
    }

    @Override
    public Faculty[] getFaculties() throws Exception {
        return jaxBparser.unmarshall().getFaculty().toArray(new Faculty[0]);
    }

    public void updateFaculty(Faculty targetFaculty, Faculty facultyForUpdate) {
        facultyForUpdate.setTitle(targetFaculty.getTitle());
        facultyForUpdate.setShortTitle(targetFaculty.getShortTitle());
        facultyForUpdate.setPlaceCount(targetFaculty.getPlaceCount());
        facultyForUpdate.setBudgetPlaceCount(targetFaculty.getBudgetPlaceCount());
        facultyForUpdate.setAdditional(targetFaculty.getAdditional());
        facultyForUpdate.setFacultyRoomNumber(targetFaculty.getFacultyRoomNumber());
    }
}
