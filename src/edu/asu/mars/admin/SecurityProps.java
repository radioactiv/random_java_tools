package edu.asu.mars.admin;

import java.security.Provider;
import java.security.Security;

public class SecurityProps {

    void listProviders() {
        System.out.println("Security Providers: ");
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            System.out.println("\t" + provider.getInfo());

        }
    }
}
