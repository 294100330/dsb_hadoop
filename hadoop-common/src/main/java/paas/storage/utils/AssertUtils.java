package paas.storage.utils;

import paas.storage.exception.QCRuntimeException;

import java.nio.charset.Charset;

/**
 * 教研工具类
 *
 * @author 豆沙包
 * Creation time 2021/1/27 15:24
 */
public abstract class AssertUtils {


    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new QCRuntimeException(message);
        }
    }

    public static void charLengthLe(String src, int limitLength, String message) {
        byte[] bytes = src.getBytes(Charset.forName("gbk"));
        AssertUtils.isTrue(bytes.length <= limitLength, message);
    }
}
