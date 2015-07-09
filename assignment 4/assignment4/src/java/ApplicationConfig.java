
import java.util.Set;

import javax.ws.rs.core.Application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c0617418
 */
@javax.ws.rs.ApplicationPath("restfull")
public class ApplicationConfig extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
}

    private void addRestResourceClasses(Set<Class<?>> resources) {
         resources.add(serveletdemo.class);
    }
}
