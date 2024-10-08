package top.antennababy.demo.web.webtest.demos.common.util;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.LineHandler;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class FileCopyAndProcess {
    public static final ExecutorService publicExecutorPool = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1);

    public static void main(String[] args) {
        TimeInterval timer = new TimeInterval();
        // 1. 源文件夹路径
        String srcPath = "D:\\temp\\sql";
        //2. 源文件夹路径
        String destPath = "D:\\temp\\sql2";
        List<String> filePaths = FileUtil.listFileNames(srcPath);
        AtomicReference<Integer> completeSize = new AtomicReference<>(0);
        // 获取所有文件
        for (String srcFile : filePaths) {
            publicExecutorPool.execute(() -> {
                try {
                    File destFile = FileUtil.newFile(destPath+"/"+srcFile);
                    FileUtil.readUtf8Lines(FileUtil.file(srcPath+"\\"+srcFile), new LineHandler() {
                        @Override
                        public void handle(String line) {
                            String modifiedLine = modifyLine(line);
                            FileUtil.appendUtf8String(modifiedLine, destFile);
                        }
                    });
                    completeSize.getAndSet(completeSize.get() + 1);
                    System.out.println("completeSize: "+completeSize.get()+" / "+filePaths.size());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }
    static String modifyLine(String line) {
        return line.replace("`lk-h-health-mate-dev`."," ")+"\n";
    }
}
