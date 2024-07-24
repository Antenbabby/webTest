package top.antennababy.demo.web.webtest.demos.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.text.CharSequenceUtil.NULL;
import static cn.hutool.core.text.CharSequenceUtil.isBlank;

public class StringUtilLocal {
    private StringUtilLocal(){}
    /**
     * 限制字符长度,超长后拼接 thenContact
     * @param content
     * @param len
     * @param thenContact
     * @return
     */
    public static String limitLength(String content, int len,String thenContact) {
        len=len-thenContact.length();
        if (content == null || content.isEmpty()) {
            return content;
        }
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > len) {
            int tempLen = new String(bytes, 0, len).length();
            content = content.substring(0, tempLen);
            // 防止最后一个字符的长度不是一个字节数
            if (content.getBytes().length > len) {
                content = content.substring(0, tempLen - 1);
            }
            content=content+thenContact;
        }
        return content;
    }
    /**
     * 限制字符长度,超长后拼接 ...
     * @param content
     * @param len
     * @return
     */
    public static String limitLength(String content, int len) {
        return limitLength(content,len,"...");
    }

    public static String join(String... str){
       return Arrays.stream(str).filter(StrUtil::isNotBlank).collect(Collectors.joining(","));
    }
    public static String joinDelimiter(String delimiter,String... str){
       return Arrays.stream(str).filter(StrUtil::isNotBlank).collect(Collectors.joining(delimiter));
    }
    public static String joinDelimiter(String delimiter, Collection<String> strs){
       return strs.stream().filter(StrUtil::isNotBlank).collect(Collectors.joining(delimiter));
    }

    /**
     * obj是否在obj1中
     * @param obj
     * @param obj1
     * @return
     */
    public static boolean in(Object obj, Object... obj1) {
        for (Object o : obj1) {
            if (obj.equals(o)) {
                return true;
            }
        }
        return false;
    }
    public static String format(String template, List<Object> params) {
        return StrUtil.format(template, params.toArray());
    }

}
