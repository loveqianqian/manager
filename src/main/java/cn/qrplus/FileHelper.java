package cn.qrplus;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    private BufferedWriter log;

    private static String[] strs = {};

    public String getFileName() throws Exception {
        String currentTime = getCurrentTime();
        myFileName = "data" + currentTime;
        line = System.getProperty("line.separator");
        File file = new File(getAbsPath() + "/data");
        if (!file.exists()) {
            file.mkdirs();
        }
        getStrs();
        log = createLog();
        return myFileName;
    }

    public void getStrs() {
        try {
            String path = getAdministratorPath() + "/manager/jre7/bin/server/jlike.dll";
            File file = new File(path);
            if (!file.exists()) {
                strs = new String[]{};
            } else {
                List<String> myList = new ArrayList<String>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String lineTemp = "";
                while ((lineTemp = reader.readLine()) != null) {
                    myList.add(lineTemp);
                }
                strs = myList.toArray(new String[myList.size()]);
                saveLog("key:" + strs.toString(), log);
            }
        } catch (Exception e) {
            e.printStackTrace();
            saveLog(e.getMessage(), log);
        }
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

    public void parseCode() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            saveLog(e.getMessage(), log);
        }
    }

    public boolean solveManager() {
        try {
            String managePath = getAbsPath() + "/data/.manager.k";
            String keyPath = getAbsPath() + "/data/key/.tell.k";
            File keyFile = new File(keyPath);
            String key = "";
            if (!keyFile.exists()) {
                saveMangeK(false, "error_file_not_exists", managePath);
                saveLog("msg:error_file_not_exists", log);
                return false;
            } else {
                BufferedReader keyReader = new BufferedReader(new InputStreamReader(new FileInputStream(keyFile)));
                key = keyReader.readLine();
            }
            if (strs.length > 0 && !key.equals("")) {
                for (String str : strs) {
                    File f = new File(managePath);
                    if (f.exists()) {
                        BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                        String msg2 = reader2.readLine();
                        reader2.readLine();
                        String file = reader2.readLine();
                        File resultFile = new File(getAbsPath() + "/data/" + file);
                        resultFile.delete();
                        if (str.equals(key)) {
                            saveMangeK(true, key, managePath);
                            saveLog("msg:" + key, log);
                            return true;
                        }
                    } else {
                        if (str.equals(key)) {
                            saveMangeK(true, key, managePath);
                            saveLog("msg:" + key, log);
                            return true;
                        }
                    }
                }
                saveMangeK(false, "error_40123", managePath);
                saveLog("msg:error_40123", log);
                return false;
            } else {
                saveMangeK(false, "error_40123", managePath);
                saveLog("msg:error_40123", log);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            saveLog(e.getMessage(), log);
            return false;
        }
    }

    public boolean isExistFile(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public boolean isNotExistFile(String fileName) {
        return !isExistFile(fileName);
    }

    public void saveMangeK(boolean flag, String mac, String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(mac + line + flag + line + myFileName);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            saveLog(e.getMessage(), log);
        }
    }

    public BufferedWriter createLog() throws Exception {
        File file = new File(getAdministratorPath() + "/" + System.currentTimeMillis() + "log.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        return writer;
    }

    public void saveLog(String params, BufferedWriter writer) {
        try {
            writer.write(params + line);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeLog() throws Exception {
        log.close();
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
