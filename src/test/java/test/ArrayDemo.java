package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @create 2019-07-09 21:57
 */
public class ArrayDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("父");
        list.add("子");
        list.add("孙");

        for (String str: list) {
            System.out.println(str);
        }

        Integer[] str = {1,2,3,4};
        //System.out.println(str);
        for (Integer s : str) {
            System.out.println(s);
        }

    }
}
