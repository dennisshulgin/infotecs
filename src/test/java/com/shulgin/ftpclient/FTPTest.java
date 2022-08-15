package com.shulgin.ftpclient;

import com.shulgin.entity.User;
import com.shulgin.jsonparser.JsonParser;
import com.shulgin.services.FtpService;
import com.shulgin.services.FtpServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class FTPTest {

    private FTPClient client;
    private User user;
    private final String path = "students-test.json";
    private FtpService ftpService;

    @BeforeClass
    public void setUp() throws IOException {
        user = new User();
        user.setUsername("myuser");
        user.setPassword("1234567");
        user.setRemoteHost("127.0.0.1");
        user.setLocalHost("127.0.0.1");
        client = new FTPClient(user.getRemoteHost(), 21);
        ftpService = new FtpServiceImpl(client);
    }

    @Test
    public void uploadAndDownloadPassiveTest() throws Exception {
        user.setType("passive");
        JsonParser jsonParser = new JsonParser(new File(path));
        StringBuilder expected = jsonParser.readFile();
        ftpService.uploadFile(user, path);
        ftpService.downloadFile(user, path);
        JsonParser jsonParser1 = new JsonParser(new File(path));
        StringBuilder actual = jsonParser1.readFile();
        Assert.assertEquals(actual.toString(), expected.toString());
    }
}
