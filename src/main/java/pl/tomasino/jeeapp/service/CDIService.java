package pl.tomasino.jeeapp.service;

import javax.enterprise.context.Dependent;

@Dependent
public class CDIService {
 
    public String getMessage() {
        return "Hello world from Hello Service!";
    }
}
