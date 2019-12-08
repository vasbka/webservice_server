package ua.nure.honcharenkoilchishen.webservice;

import ua.nure.honcharenkoilchishen.webservice.impl.FacultyWebServiceImpl;

import javax.xml.ws.Endpoint;

public class EntryPoint {
    public static void main(String... args) {
        Endpoint.publish("http://localhost:1986/wss/faculty", new FacultyWebServiceImpl());
    }
}
