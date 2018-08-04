package edu.asu.mars.admin;

import java.util.Map;
import java.util.TreeMap;

class SystemEnv {

    //Print everything
    void printEnv() {
        Map<String, String> env = System.getenv();
        Map<String, String> envSorted = new TreeMap<String, String>(env);
        for (Map.Entry<String, String> envEntry : envSorted.entrySet()) {
            System.out.println(envEntry.getKey() + ":" + envEntry.getValue());
        }
    }

    //Print a single environment value
    void printEnv(String envString) {
        String envValue = System.getenv(envString);
        if (envValue != null) {
            System.out.println(envString + ":" + envValue);
        }
        else {
            System.err.println("Environment value: '" + envString + "' does not exist.");
        }
    }
}