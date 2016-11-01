package cn.qrplus;

import java.io.IOException;
import java.util.Scanner;

/**
 * <p>ProjectName:manager</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/10/30
 */
public class CodeHelper {

    public String getCode() throws IOException {
        Process process = Runtime.getRuntime().exec(new String[]{"cmd","wmic", "cpu", "get", "ProcessorId"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        return property + ": " + serial;
    }
}
