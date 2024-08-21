package top.antennababy.demo.web.webtest.demos.test;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Future;

public class Test {
    @SneakyThrows
    public static void main(String[] args) {
        String format = DateUtil.format(DateUtil.offsetMinute(DateUtil.beginOfDay(new Date())
                , Integer.parseInt(DateUtil.between(DateUtil.beginOfDay(new Date())
                        , DateUtil.parse("2024-08-12 12:57", "yyyy-MM-dd HH:mm"), DateUnit.MINUTE) / 5 * 5 + "")), "HH:mm");
        System.out.println("活跃时间占比:"+format );
        Future<String> stringFuture = ThreadUtil.execAsync(() -> "11111111");
        System.out.println(stringFuture.get());
        System.out.println(stringFuture.get());
    }
}
