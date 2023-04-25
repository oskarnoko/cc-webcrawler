package aau.webcrawler;

import org.junit.Test;
import org.junit.Assert;
public class MDHelperTest {
    @Test
    public void testCompactOverview() {
        String actualCompactOverview = MDHelper.generateCompactOverview("websiteName", 3, "en", "de");
        String expectedCompactOverview = "input: " + "<a>websiteName</a>\n" +
                "<br>depth: 3\n" +
                "<br>source language: en\n" +
                "<br>target language: de\n";;
        Assert.assertEquals(expectedCompactOverview, actualCompactOverview);
    }

}
