package cn.qrplus;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>ProjectName:manager</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/10/30
 */
public class FileHelper {

    private static String myFileName = "";

    public void getFileName(){
        myFileName = "data" + getCurrentTime();
    }

    public String getAdministratorPath() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        return String.valueOf(fsv.getHomeDirectory());
    }

    public String getAbsPath() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String administratorPath = String.valueOf(fsv.getHomeDirectory());
        int i = administratorPath.lastIndexOf("\\");
        return administratorPath.substring(0, i);
    }

    public void parseCode() throws IOException {
        String administratorPath = getAdministratorPath();
        String absPath = getAbsPath();
        File file = new File(administratorPath + "/data.txt");
        File resultFile = new File("C:/Users/Administrator/data/" + myFileName);
        if (file.exists()) {
            if (resultFile.exists()) {
                resultFile.delete();
                resultFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String temp = line.split("\\|\\|")[0];
                writer.write(temp + "\n");
                writer.flush();
            }
            reader.close();
            writer.close();
        }
    }

    public boolean solveManager() throws Exception {
        String code = new CodeHelper().getMacAddress();
        String secretPath = getAdministratorPath() + "/jre7/bin/amd64/config.properties";
        String managePath=getAdministratorPath()+"/data/.manager.k";
        if (isNotExistFile(secretPath)) {
            saveConfig(code);
            saveMangeK(true, code,managePath);
            return true;
        }else{
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(new File(secretPath))));
            String msg1 = reader1.readLine();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File(managePath))));
            String msg2 = reader2.readLine();
            String msg3=string2MD5(code);
            if (msg1.equals(msg2)&&msg1.equals(msg3)) {
                saveMangeK(true,msg2,managePath);
                return true;
            }else{
                saveMangeK(false,msg2,managePath);
                return false;
            }
        }
    }

    public boolean isExistFile(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public boolean isNotExistFile(String fileName) {
        return !isExistFile(fileName);
    }

    public void saveMangeK(boolean flag, String mac,String path) throws Exception {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(string2MD5(mac) + "\n" + flag + "\n" + myFileName);
        writer.flush();
        writer.close();
    }

    public void saveConfig(String mac) throws Exception {
        String secretPath = getAdministratorPath() + "/jre7/bin/amd64/config.properties";
        File file = new File(secretPath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(string2MD5(mac) + getCurrentTime());
        writer.flush();
        writer.close();
    }

    public String string2MD5(String inStr) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return sdf.format(date);
    }


}
