package ua.nure.honcharenkoilchishen.webservice;

import ua.nure.honcharenkoilchishen.entity.faculty.Faculty;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Collection;
import java.util.List;

@WebService(targetNamespace = "http://nure.ua/honcharenkoilchishen/service")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FacultyWebService {

    @WebMethod
    @WebResult(targetNamespace="http://nure.ua/honcharenkoilchishen/faculty")
    Faculty geFacultyByName(
            @WebParam(name = "title")
            String title) throws Exception;

    @WebMethod
    @WebResult(targetNamespace="http://nure.ua/honcharenkoilchishen/faculty")
    Faculty createFaculty(
            @WebParam(name = "faculty", targetNamespace="http://nure.ua/honcharenkoilchishen/faculty")
            Faculty faculty) throws Exception;

    @WebMethod
    @WebResult(targetNamespace="http://nure.ua/honcharenkoilchishen/faculty")
    Boolean updateFacultyTitle(
            @WebParam(name = "faculty", targetNamespace="http://nure.ua/honcharenkoilchishen/faculty")
            Faculty faculty) throws Exception;

    @WebMethod
    @WebResult(targetNamespace="http://nure.ua/honcharenkoilchishen/faculty")
    Faculty getFacultyById(
            @WebParam(name = "id")
            Integer facultyId) throws Exception;

    @WebMethod
    @WebResult(targetNamespace="http://nure.ua/honcharenkoilchishen/faculty")
    Faculty deleteFaculty(
            @WebParam(name = "id")
            Integer facultyId) throws Exception;

    @WebMethod
    @WebResult(targetNamespace="http://nure.ua/honcharenkoilchishen/faculty")
    Faculty[] getFaculties() throws Exception;
}
