package top.antennababy.demo.web.webtest.demos.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * @author: zhengzhonghua
 * @date: 2023/3/16 13:23
 * @description: 生成钉钉类似的头像
 */
public class HeadGeneration {
   static String HEAD_IMAGE_URL = "D:/temp/";
    /**
     * @author: zhengzhonghua
     * @date: 2023/3/16 13:24
     * @description: 头像生成
     * @param name 用户名称
     * @return 访问图片的路径 localhost:8082/img/
     */
    public static String generateImg(String name) throws IOException {
        int nameLen = name.length();
        //定义最后在图片上显示的姓名
        String nameWritten;
        if (nameLen <= 2) {
            nameWritten = name;
        } else {
            //如果用户姓名大于三位,截取后两位
            nameWritten = StrUtil.subSuf(name,1);
        }
        String uuid = UUID.randomUUID().toString().replace("-","");
        //文件名(路径+uuid+.jpg)
        String fileName = HEAD_IMAGE_URL + uuid+".jpg";
        File file = new File(fileName);
        //生成图片
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(getRandomColor());
        g2.clearRect(0, 0, 100, 100);
        g2.setPaint(Color.WHITE);
        Font font = null;
        // 两个字及以上
        if (nameWritten.length() >= 2) {
            font = new Font("微软雅黑", Font.BOLD, 30);
            g2.setFont(font);
            g2.drawString(nameWritten, 20, 60);
        }
        // 一个字
        if (nameWritten.length() == 1) {
            // 中文
            font = new Font("微软雅黑", Font.PLAIN, 50);
            g2.setFont(font);
            g2.drawString(nameWritten, 25, 70);

        }
        //图片做圆角处理
        BufferedImage rounded = makeRoundedCorner(bi, 20);
        ImageIO.write(rounded, "png", file);
        return HEAD_IMAGE_URL+uuid+".jpg";
    }

    /**
     * @author: zhengzhonghua
     * @date: 2023/3/16 13:32
     * @description: 生成随机颜色
     */
    private static Color getRandomColor() {
        String[] beautifulColors = new String[]{"2,168,250","250,126,2","250,2,2","2,250,2","250,2,250","2,2,250"};
        int len = beautifulColors.length;
        Random random = new Random();
        String[] color = beautifulColors[random.nextInt(len)].split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
    }

    /**
     * @author: zhengzhonghua
     * @date: 2023/3/16 13:39
     * @description: 图片做圆角处理
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(generateImg("李向阳"));
    }
}
