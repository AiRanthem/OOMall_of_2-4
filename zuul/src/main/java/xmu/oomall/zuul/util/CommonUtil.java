package xmu.oomall.zuul.util;

/**
 * 一般工具类
 * @author zty
 */
public class CommonUtil {
    public static Integer ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1){
            pos = str.indexOf(substr, pos + 1);
        }
        return pos;
    }
}
