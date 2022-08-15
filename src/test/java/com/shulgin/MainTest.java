package com.shulgin;

import com.shulgin.ftpclient.FTPTest;
import com.shulgin.ftpclient.FTPUtilsTest;
import com.shulgin.jsonparser.JsonParserTest;
import org.testng.TestNG;

public class MainTest {
    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        testNG.setTestClasses(new Class[]{
                FTPUtilsTest.class,
                JsonParserTest.class,
                FTPTest.class
        });
        testNG.run();
    }
}
