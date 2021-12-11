package top.xinsin.container;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * @Auther wzp
 * @Date 2021/12/11 20:33
 * @Version 1.0
 */
@RestController
public class VerificationCode {
    @GetMapping("/api/getverificationcode")
    public String GetVerificationCode() throws IOException {
        //定义宽高
        int height=50;
        int width=100;

        /**
         * 1，创建对象，内存中生成(验证码图片对象)
         * 第一个参数：图片的宽
         * 第二个参数：图片的高
         * 第三个参数：图片类型（常量）
         */
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Random r = new Random();

        //2，美化图片
        //2.1，填充背景色
        Graphics g = image.getGraphics();//获取画笔对象
        g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));//设置画笔颜色
        g.fillRect(0,0,width,height);//设置画笔填充背景颜色

        //2.2,画边框
        g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));//设置画笔颜色
        g.drawRect(0,0,width-1,height-1);//设置画笔画边框，从(0,0)开始，到(width-1,height-1)

        //2.3，写验证码
        g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));//设置画笔颜色
        String str="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
        //生成随机数字
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <=4; i++) {
            int index = r.nextInt(str.length());//生成随机角标，最大长度为字符串长度
            char ch = str.charAt(index);//根据角标，获取str的字符
            g.setFont(new Font("宋体", Font.BOLD,20));
            g.drawString(String.valueOf(ch),width/5*i,height/2);//将验证码写入
            sb.append(String.valueOf(ch));
        }
        System.out.println("输出验证码:" + sb);
        g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));//设置画笔颜色
        //生成随机干扰线(画10根)
        for (int i = 0; i < 10; i++) {
            //两点确定一条直线 (x1,y1)和(x2,y2)确定一条直线
            int x1 = r.nextInt(width);
            int y1 = r.nextInt(height);

            int x2 = r.nextInt(width);
            int y2 = r.nextInt(height);

            g.drawLine(x1,x2,y1,y2);
        }

        /**
         * 3，将图片输出到页面展示
         * 第一个参数：图片对象
         * 第二个参数：后缀名
         * 第三个参数：输出流，通过response对象获取
         */
        ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",BAOS);
        byte[] bytes = BAOS.toByteArray();
        String B64String = Base64.getEncoder().encodeToString(bytes);
        B64String += "," + sb.toString();
        return B64String;
    }
}
