package edu.asu.mars.admin;

import java.util.Arrays;

class Properties {

    //Print everything
    void printProperties() {
//    Properties props = System.getProperties();
        Object[] propNames = System.getProperties().stringPropertyNames().toArray();
        Arrays.sort(propNames);
        for (Object propName : propNames) {
            System.out.println(propName.toString() + ":" + System.getProperty(propName.toString()));
        }
    }

    //Print a single property
    void printProperties(String property) {
//    Properties props = System.getProperties();
        boolean foundProperty = false;
        Object[] propNames = System.getProperties().stringPropertyNames().toArray();
        Arrays.sort(propNames);
        for (Object propName : propNames) {
            if (propName.toString().contains(property)) {
                System.out.println(propName.toString() + ":" + System.getProperty(propName.toString()));
                foundProperty = true;
            }
        }
        if (!foundProperty) {
            System.err.println("Property '" + property + "' does not exist.");
        }
    }
}
