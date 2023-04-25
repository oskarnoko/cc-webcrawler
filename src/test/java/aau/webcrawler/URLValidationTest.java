package aau.webcrawler;

import java.net.HttpURLConnection;

import org.junit.Test;
import org.junit.Assert;

public class URLValidationTest {
    @Test
    public void testFixURL() {
        String url = "www.example.com";
        String expectedFixedURL = "http://www.example.com";
        String actualFixedURL = URLValidation.fixURL(url);
        Assert.assertEquals(expectedFixedURL, actualFixedURL);
    }

    @Test
    public void testCheckIfHTTPStatusCodeOK() {
        int httpStatusCodeOK = HttpURLConnection.HTTP_OK;
        boolean expectedResult = true;
        boolean actualResult = URLValidation.checkIfHTTPStatusCodeOK(httpStatusCodeOK);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCheckIfValidURLIsValid() {
        String validURL = "http://www.example.com";
        boolean expectedResultForValidURL = true;
        boolean actualResultForValidURL = URLValidation.checkIfURLValid(validURL);
        Assert.assertEquals(expectedResultForValidURL, actualResultForValidURL);
    }

    @Test
    public void testCheckIfInvalidURLIsInvalid() {
        String invalidURL = "thisisnotan.url"; // invalid IP address
        boolean expectedResultForInvalidURL = false;
        boolean actualResultForInvalidURL = URLValidation.checkIfURLValid(invalidURL);
        Assert.assertEquals(expectedResultForInvalidURL, actualResultForInvalidURL);
    }

}
