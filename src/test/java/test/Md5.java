package test;

import static ssm.util.MyUtils.strToMd5;

/**
 * @author chen
 * @create 2019-07-12 12:17
 */
public class Md5 {
    public static void main(String[] args) {
        String md5 = strToMd5("123456"+"FC1EE06AE4354D3FBF7FDD15C8FCDA71");
        System.out.println(md5);
    }
}
