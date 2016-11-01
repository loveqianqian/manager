package cn.qrplus;

import javax.swing.filechooser.FileSystemView;
import java.io.*;

/**
 * <p>ProjectName:manager</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/10/30
 */
public class FileHelper {

    public String getAdministratorPath() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        return String.valueOf(fsv.getHomeDirectory());
    }

    public String getAbsPath(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String administratorPath= String.valueOf(fsv.getHomeDirectory());
        int i = administratorPath.lastIndexOf("\\");
        return administratorPath.substring(0,i);
    }

    public void parseCode() throws IOException {
        String administratorPath = getAdministratorPath();
        String absPath = getAbsPath();
        File file = new File(administratorPath + "/data.txt");
        File resultFile = new File(absPath+"/data/data.t");
        if (file.exists()) {
            if (resultFile.exists()) {
                resultFile.delete();
                resultFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
            String line;
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
        File myFile = new File(getAbsPath()+"/data");
        if (!myFile.exists()) {
            myFile.mkdirs();
        }
        File file = new File(getAbsPath()+"/data/.manager.k");
        String code = new CodeHelper().getCode();
        if (!file.exists()) {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(code + "\ntrue");
            writer.flush();
            writer.close();
            return true;
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String msg = reader.readLine();
            String result;
            boolean flag;
            if (!code.equals(msg)) {
                result = msg + "\nfalse";
                flag = false;
            } else {
                result = msg + "\ntrue";
                flag = true;
            }
            file.delete();
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result);
            writer.flush();
            writer.close();
            reader.close();
            return flag;
        }
    }


}
