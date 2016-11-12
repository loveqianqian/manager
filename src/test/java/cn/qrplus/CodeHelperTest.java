package cn.qrplus;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * cn.qrplus
 *
 * @author zhiwei
 * @create 2016-11-01 22:00.
 */
public class CodeHelperTest {

    @Test
    public void testGetCode() throws Exception {
        Process process=Runtime.getRuntime().exec("cmd /c wmic cpu get processorid");
        process.getOutputStream().close();
        Scanner sc=new Scanner(process.getInputStream());
        String property=sc.next();
        String serial = sc.next();
        System.out.println(property+":"+serial);
    }
}