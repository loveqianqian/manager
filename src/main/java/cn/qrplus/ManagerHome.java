package cn.qrplus;

public class ManagerHome {

    public static void main(String[] args) throws Exception {
        FileHelper fileHelper=new FileHelper();
        boolean flag = fileHelper.solveManager();
        if (flag) {
            fileHelper.parseCode();
        }
    }

}
