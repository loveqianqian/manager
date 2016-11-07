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

    private static String line = "";

    private static long timeStamp = 0L;

    public String getFileName() {
        String currentTime = getCurrentTime();
        myFileName = "data" + currentTime;
        line = System.getProperty("line.separator");
        File file = new File(getAbsPath() + "/data");
        if (!file.exists()) {
            file.mkdirs();
        }
        return myFileName;
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
        File file = new File(administratorPath + "/data.txt");
        File resultFile = new File(getAbsPath() + "/data/" + myFileName + ".t");
        if (file.exists()) {
            if (resultFile.exists()) {
                resultFile.delete();
                resultFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
            String lineTemp;
            reader.readLine();
            while ((lineTemp = reader.readLine()) != null) {
                String temp = lineTemp.split("\\|\\|")[0];
                writer.write(temp + line);
                writer.flush();
            }
            reader.close();
            writer.close();
        }
        resultFile.setLastModified(timeStamp);
    }

    public boolean solveManager() throws Exception {
        String code = new CodeHelper().getMacAddress();
        String secretPath = getAdministratorPath() + "/manager/jre7/lib/amd64/config.properties";
        String managePath = getAbsPath() + "/data/.manager.k";
        if (isNotExistFile(secretPath)) {
            saveConfig(code, secretPath);
            saveMangeK(true, string2MD5(code), managePath);
            return true;
        } else {
            if (isNotExistFile(managePath)) {
                saveMangeK(false, string2MD5(code), managePath);
                return false;
            } else {
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(new File(secretPath))));
                String msg1 = reader1.readLine();
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File(managePath))));
                String msg2 = reader2.readLine();
                String msg3 = string2MD5(code);
                reader2.readLine();
                String file = reader2.readLine();
                File resultFile = new File(getAbsPath() + "/data/" + file);
                resultFile.delete();
                if (msg1.equals(msg2) && msg1.equals(msg3)) {
                    saveMangeK(true, msg2, managePath);
                    return true;
                } else {
                    saveMangeK(false, msg2, managePath);
                    return false;
                }
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

    public void saveMangeK(boolean flag, String mac, String path) throws Exception {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(mac + line + flag + line + myFileName);
        writer.flush();
        writer.close();
    }

    public void saveConfig(String mac, String path) throws Exception {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(string2MD5(mac));
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
        timeStamp = System.currentTimeMillis();
        return sdf.format(timeStamp);
    }


}
