package edu.asu.mars.admin;

import java.security.Provider;
import java.security.Security;

/**
 * User: npiace
 * Date: 2/6/17
 * Time: 11:24 AM
 */
public class SecurityProps {

    void listProviders() {
        System.out.println("Security Providers: ");
        Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            Provider provider = providers[i];
            System.out.println("\t" + provider.getInfo());

        }
    }
}
