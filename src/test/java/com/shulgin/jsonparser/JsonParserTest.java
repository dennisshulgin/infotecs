package com.shulgin.jsonparser;

import com.shulgin.entity.Student;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonParserTest {
    JsonParser jsonParser;

    @BeforeClass
    public void setUp() throws IOException {
         jsonParser = new JsonParser(new File("students-test.json"));
    }

    @Test
    public void testParse() {
        List<Student> studentsExpected = new ArrayList<>();
        studentsExpected.add(new Student(1, "student1"));
        studentsExpected.add(new Student(3, "student3"));
        List<Student> studentsActual = jsonParser.parse();
        Assert.assertEquals(studentsActual, studentsExpected);
    }

    @Test
    public void testParseStudent() {
        Student expectedStudent = new Student(1, "Denis");
        StringBuilder str = new StringBuilder();
        str.append("\"id\": 1,\"name\": \"Denis\"\n");
        Student actualStudent = jsonParser.parseStudent(str, 0, str.length());
        Assert.assertEquals(actualStudent, expectedStudent);
    }

    @Test
    public void testParseParam() {
        StringBuilder str = new StringBuilder();
        str.append("\"id\": 5");
        Map.Entry<String, String> entry = jsonParser.parseParam(str, 0, str.length());
        Assert.assertEquals(entry.getKey(), "id");
        Assert.assertEquals(entry.getValue(), "5");
        str.delete(0, str.length());
        str.append("\"name\": \"Denis Shulgin\"");
        entry = jsonParser.parseParam(str, 0, str.length());
        Assert.assertEquals(entry.getKey(), "name");
        Assert.assertEquals(entry.getValue(), "Denis Shulgin");
    }
}