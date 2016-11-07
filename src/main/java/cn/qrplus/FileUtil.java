package cn.qrplus;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * cn.qrplus
 *
 * @author zhiwei
 * @create 2016-11-07 23:25.
 */
public class FileUtil {

    public void changeFileTime(String filename) {

        File fileToChange = new File(filename);

        Date fileTime = new Date(fileToChange.lastModified());
        System.out.println(fileTime.toString());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        System.out.println(date.getTime());
        String format = sdf.format(date);

        SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(parse.getTime());

        //将最近修改时间修改为当前时间
        if (fileToChange.setLastModified(System.currentTimeMillis()))
            System.out.println("Success!");
        else
            System.out.println("Failed!");

    }

    public static void main(String[] args) {
        FileUtil fileutil = new FileUtil();
        fileutil.changeFileTime("C:/HaxLogs.txt");
    }
}
