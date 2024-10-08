package top.antennababy.demo.web.webtest.demos.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.math.MathUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ImageConnect {
    public static void main(String[] args) {
        String[] imgPaths= {"d:\\temp\\aaa.jpg", "d:\\temp\\bbb.jpg", "d:\\temp\\ccc.jpg"};
        String targetImagePath = "D:\\temp\\image-conn1.jpg";

        try {
            connectImageWidthVertical(imgPaths, targetImagePath);
        } catch(Exception e) {
            System.out.println("图片拼接失败！");
            e.printStackTrace();
        }
    }



    public static void connectImageWidthVertical(String[] imgPaths, String targetImagePath) throws IOException {
        List<BufferedImage> bufferedImageList = Arrays.stream(imgPaths).map(x -> MyBeanUtils.safeGet(() -> ImageIO.read(new File(x)))).toList();
        // 获取最大宽度
        int connImageWidth = bufferedImageList.stream().max(Comparator.comparingInt(BufferedImage::getWidth)).get().getWidth();
        // 获取总高度
        int connImageHeight = bufferedImageList.stream().mapToInt(x-> (int) (connImageWidth * x.getHeight() * 1.0 / x.getWidth())).sum();

        BufferedImage connImage = new BufferedImage(connImageWidth, connImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics connGraphics = connImage.getGraphics();
        int x=0,y = 0;
        for (BufferedImage image : bufferedImageList) {
            int scaledHeight = (int) (connImageWidth * image.getHeight() * 1.0 / image.getWidth());
            connGraphics.drawImage(image, x, y, connImageWidth,scaledHeight,null);
            y += scaledHeight+1;
        }

        File targetFile = FileUtil.file(targetImagePath);
        ImageIO.write(connImage, FileUtil.extName(targetFile), targetFile);
    }

}

