package top.antennababy.demo.web.webtest.demos.common.util;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;

public class ConnectImageThenToPdf {
    public static void main(String[] args) {
        String[] imgPaths= {"d:\\temp\\aaa.jpg", "d:\\temp\\bbb.jpg", "d:\\temp\\ccc.jpg"};
        String pdfPath = "D:\\temp\\image-conn1.pdf";

        getPdfFromImages(imgPaths, pdfPath);
    }

    @SneakyThrows
    private static void getPdfFromImages(String[] imgPaths, String pdfPath) {
        ImageConnect.connectImageWidthVertical(imgPaths, "D:\\temp\\temp.jpg");
        ImageToPdfConverter.image2Pdf("D:\\temp\\temp.jpg", pdfPath);
        FileUtil.del("D:\\temp\\temp.jpg");
    }
}
