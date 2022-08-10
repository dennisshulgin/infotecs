package com.shulgin.ftpclient;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.shulgin.ftpclient.FTPUtils.*;

public class FTPUtilsTest {

    @DataProvider(name = "data-provider-for-get-ip-string")
    public Object[][] dpForGetIpString(){
        return new Object[][] {
                {new String[]{"227 Entering Passive Mode (192,168,254,253,233,92)", "192,168,254,253,233,92"}},
                {new String[]{"227 Entering Passive Mode (145,168,21,253,233,92)", "145,168,21,253,233,92"}},
        };
    }

    @DataProvider(name = "data-provider-for-parse-port")
    public Object[][] dpForParsePort(){
        return new Object[][] {
                {new Object[]{"192,168,254,253,233,92", 59740}},
                {new Object[]{"145,168,21,253,251,78", 64334}},
        };
    }

    @DataProvider(name = "data-provider-for-parse-ip")
    public Object[][] dpForParseIp(){
        return new Object[][] {
                {new String[]{"192,168,254,253,233,92", "192.168.254.253"}},
                {new String[]{"145,168,21,253,233,92", "145.168.21.253"}},
        };
    }

    @Test(dataProvider = "data-provider-for-get-ip-string")
    public void testGetIpString(String[] params) {
        String result = getIpString(params[0]);
        Assert.assertEquals(result, params[1]);
    }

    @Test(dataProvider = "data-provider-for-parse-port")
    public void testParsePort(Object[] params) {
        int port = FTPUtils.parsePort((String)params[0]);
        Assert.assertEquals(port, (int)params[1]);
    }

    @Test(dataProvider = "data-provider-for-parse-ip")
    public void testParseIp(String[] params) {
        String result = parseIp(params[0]);
        Assert.assertEquals(result, params[1]);
    }

    @Test
    public void testGetFtpIp() {
        String result = getFtpIp("127.0.0.1", 46790);
        Assert.assertEquals(result, "127,0,0,1,182,198");
        result = getFtpIp("127.0.0.1", 60455);
        Assert.assertEquals(result, "127,0,0,1,236,39");
    }

    @Test
    public void testGenerateFtpPort() {
        int port = generateFtpPort();
        Assert.assertTrue(port >= 49152 && port <= 65534);
        port = generateFtpPort();
        Assert.assertTrue(port >= 49152 && port <= 65534);
        port = generateFtpPort();
        Assert.assertTrue(port >= 49152 && port <= 65534);
    }
}