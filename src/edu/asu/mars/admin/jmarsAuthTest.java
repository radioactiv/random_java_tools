package edu.asu.mars.admin;


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: npiace
 * Date: 7/6/2022
 * Time: 10:53 AM
 */
public class jmarsAuthTest {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private final String url1 = "https://jmars.mars.asu.edu/about.php?product=jmars&user=pa-test&pass=testing123";
    private final String url2 = "https://jm-web.mars.asu.edu/about.php?product=jmars&user=pa-test&pass=testing123";

    public void internalTest() {
        internal(url1);
        internal(url2);
    }

    public void internal(String url) {
        System.out.println(ANSI_CYAN + "Using Java Built-In HTTP client" + ANSI_RESET);
        System.out.println(ANSI_RED + "Fetching: " + url + ANSI_RESET);
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestProperty("accept", "application/text");
//        InputStream responseStream = connection.getInputStream();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println(inputLine);
                }
                in.close();

                // print result
//                System.out.println(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void commonsTest() {
        commons(url1);
        commons(url2);
    }

    public void commons(String url) {
        System.out.println(ANSI_CYAN + "Using Apache Commons HTTP client" + ANSI_RESET);
        System.out.println(ANSI_RED + "Fetching: " + url + ANSI_RESET);
        // Create an instance of HttpClient.
        HttpClient client = new HttpClient();
        // Create a method instance.
        GetMethod method = new GetMethod(url);
        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));

        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
//            byte[] responseBody = method.getResponseBody();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    method.getResponseBodyAsStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
//            System.out.println(new String(responseBody));

        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
    }
}