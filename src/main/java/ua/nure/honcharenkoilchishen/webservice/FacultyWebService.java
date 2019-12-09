package ua.nure.honcharenkoilchishen.webservice;

import ua.nure.honcharenkoilchishen.entity.faculty.Faculty;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FacultyWebService {

    @WebMethod
    Faculty geFacultyByName(String title) throws Exception;

    @WebMethod
    Faculty createFaculty(Faculty faculty) throws Exception;

    @WebMethod
    Boolean updateFacultyTitle(Faculty faculty) throws Exception;

    @WebMethod
    Faculty getFacultyById(Integer facultyId) throws Exception;

    @WebMethod
    Faculty deleteFaculty(Integer facultyId) throws Exception;

}
