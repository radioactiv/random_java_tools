package edu.asu.mars.admin;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.util.Arrays;

/**
 * User: npiace
 * Date: 6/29/2022
 * Time: 2:23 PM
 */
public class sslProps {
    public void listProps() {
        try {
            System.out.println("Supported SSL Protocols:");
            SSLParameters sslParams = SSLContext.getDefault().getSupportedSSLParameters();
            System.out.println("  " + Arrays.toString(sslParams.getProtocols()));
            System.out.println("");//Newline
            System.out.println("Supported Cipher Suites:");
            String[] ciphers  = sslParams.getCipherSuites();
            Arrays.sort(ciphers);
            for (Object cipher : ciphers) {
                System.out.println("  " + cipher);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
