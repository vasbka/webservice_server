package ua.nure.honcharenkoilchishen.webservice.impl;

import ua.nure.honcharenkoilchishen.entity.faculty.Faculties;
import ua.nure.honcharenkoilchishen.entity.faculty.Faculty;
import ua.nure.honcharenkoilchishen.parser.DOMParser;
import ua.nure.honcharenkoilchishen.webservice.FacultyWebService;

import javax.jws.WebService;
import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;


@WebService(endpointInterface = "ua.nure.honcharenkoilchishen.webservice.FacultyWebService")
public class FacultyWebServiceImpl implements FacultyWebService {
    private static DOMParser domParser;

    public FacultyWebServiceImpl() {
        domParser = new DOMParser();
    }

    @Override
    public Faculty geFacultyByName(String title) throws Exception {
        Faculties parse = domParser.parse(new FileInputStream("faculty.xml"));
        List<Faculty> collect = parse.getFaculty().stream()
                .filter(faculty -> faculty.getTitle().equals(title))
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            return collect.get(0);
        }
        return null;
    }
}
