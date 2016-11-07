package cn.qrplus;

public class ManagerHome {

    public static void main(String[] args) throws Exception {
        FileHelper fileHelper=new FileHelper();
        fileHelper.getFileName();
        boolean flag = fileHelper.solveManager();
        if (flag) {
            fileHelper.parseCode();
        }
    }
}
