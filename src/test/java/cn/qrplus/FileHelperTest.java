package cn.qrplus;

import org.junit.Test;

/**
 * <p>ProjectName:manager</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/11/1
 */
public class FileHelperTest {

    @Test
    public void testGetAdministratorPath() throws Exception {
        FileHelper helper=new FileHelper();
        String administratorPath = helper.getAdministratorPath();
        System.out.println(administratorPath.substring(0,administratorPath.lastIndexOf("\\")));
    }
}