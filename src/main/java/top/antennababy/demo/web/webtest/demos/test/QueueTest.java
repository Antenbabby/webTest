package top.antennababy.demo.web.webtest.demos.test;

import cn.hutool.json.JSONUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class QueueTest {
    public static void main(String[] args) {
        ArrayDeque<String> strings = new ArrayDeque<String>(2);
        strings.add("a");
        strings.add("b");
        strings.add("c");
        String jsonStr = JSONUtil.toJsonStr(strings);
        System.out.println(jsonStr);
        try {
            System.out.println(1/0);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(getStackTraceAsString(e));
        }
        List<String> list = JSONUtil.toList(jsonStr, String.class);
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
