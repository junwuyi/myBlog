package ssm.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author chen
 * @date 2019/2/22 下午8:39
 */

public class BCryptUtil {


    /**
     * 对密码进行加密
     * @param password
     * @return
     */
    public static String encode(String password) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(password);
        return hashPass;
    }

    /**
     * 对原密码和已加密的密码进行匹配，判断是否相等
     * @param password
     * @param encodedPassword
     * @return
     */
    public static boolean match(String password,String encodedPassword) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean result = bcryptPasswordEncoder.matches(password, encodedPassword);
        return result;
    }

    public static void main(String[] args) {
        String s = encode("123456");
        System.out.println(s);
    }


}
