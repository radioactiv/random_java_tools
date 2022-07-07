package edu.asu.mars.admin;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: npiace
 * Date: 6/29/2022
 * Time: 2:23 PM
 */
public class sslProps {
    String[] windowsOracleCiphers = {
            "TLS_AES_128_GCM_SHA256",
            "TLS_AES_256_GCM_SHA384",
            "TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
            "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384",
            "TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDH_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDH_RSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_EMPTY_RENEGOTIATION_INFO_SCSV",
            "TLS_RSA_WITH_AES_128_CBC_SHA",
            "TLS_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_RSA_WITH_AES_256_CBC_SHA",
            "TLS_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_RSA_WITH_AES_256_GCM_SHA384",
    };

    public void listProps() {
        try {
            System.out.println("Supported SSL Protocols:");
            SSLParameters sslParams = SSLContext.getDefault().getSupportedSSLParameters();
            System.out.println("  " + Arrays.toString(sslParams.getProtocols()));
            System.out.println("");//Newline
            System.out.println("Supported Cipher Suites:");
            String[] ciphers = sslParams.getCipherSuites();
            Arrays.sort(ciphers);
            List ciphersList = Arrays.asList(ciphers);
            List wantedCiphers = Arrays.asList(windowsOracleCiphers);
            ArrayList missing = new ArrayList();
            ArrayList extras = new ArrayList();
            for (Object cipher : ciphers) {
                System.out.println("  " + cipher);
            }
            for (String cipher : windowsOracleCiphers) {
                if (!ciphersList.contains(cipher)) {
                    missing.add(cipher.toString());
                }
            }
            for (String cipher : ciphers) {
                if (!wantedCiphers.contains(cipher)) {
                    extras.add(cipher.toString());
                }
            }
            System.out.println("Possibly Missing Ciphers:");
            for (Object miss : missing) {
                System.out.println("  " + miss);
            }
//            System.out.println(missing.toString());
            System.out.println("Extra Ciphers:");
            for (Object extra : extras) {
                System.out.println("  " + extra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
