package com.piaoniu.biz;

import com.piaoniu.common.TextUtils;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.imageio.ImageIO;

/**
 * Created by slj on 16/10/25.
 */
public class ImgUtils {

    public static final int DEFAULT_FONT_SIZE =40;
    public static  Font fontTemp =null;
    static {
        try {
            fontTemp = Font.createFont(Font.TRUETYPE_FONT,ImgUtils.class.getClassLoader().getResourceAsStream("msyh_emoji.ttf"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加文字水印
     * @param targetImg 目标图片路
     * @param pressText 水印文字，
     * @param color 字体颜色
     * @param x 水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static BufferedImage waterMark(InputStream targetImg, String pressText, Color color, int x, int y, float alpha) {
        try {
            Image image = ImageIO.read(targetImg);
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            if(!canDrawString(width)){
                g.dispose();
                return bufferedImage;
            }
            int fontSize =  getFontSize(width/3,pressText,x,g);


            Font font = fontTemp.deriveFont(Font.PLAIN,fontSize);
            g.setFont(font);
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            FontMetrics metrics = g.getFontMetrics(font);
            g.drawString(new String(pressText.getBytes(),"utf-8"), width-metrics.stringWidth(pressText)-x, height-y);
            g.dispose();
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getFontSize(int width,String pressText,int dx,Graphics2D g){
        int result = DEFAULT_FONT_SIZE;
        Graphics2D g1 = g;
        FontMetrics metrics = g1.getFontMetrics(fontTemp.deriveFont(Font.PLAIN,result));
        int d_left = width-metrics.stringWidth(pressText)-dx;
        int count=3;
        while (d_left<0 && count>0){
            result = result/2;
            metrics =g1.getFontMetrics(fontTemp.deriveFont(Font.PLAIN,result));
            d_left = width-metrics.stringWidth(pressText)-dx;
            count--;
        }
        return result;
    }
    private static boolean canDrawString(int width){
        return width>300;
    }

    public static void drawString(Graphics2D g,String pressText,int fontSize,Color color, int x, int y, float alpha) throws UnsupportedEncodingException {
        g.setColor(color);
        Font font = fontTemp.deriveFont(Font.PLAIN,fontSize);
        g.setFont(font);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(new String(pressText.getBytes(),"utf-8"),x,y);
    }

    public static void drawStringXCenterOnGraphics(Graphics2D g,String pressText,int fontSize,Color color, int x, int y, float alpha) throws UnsupportedEncodingException {
        Font font = fontTemp.deriveFont(Font.PLAIN,fontSize);
        g.setFont(font);
        g.setColor(color);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics metrics = g.getFontMetrics(font);
        int strWidth = metrics.stringWidth(pressText);
        int strHeight = metrics.getHeight();
        g.drawString(new String(pressText.getBytes(),"utf-8"),(x-strWidth)/2,y + strHeight/2);
    }

    //将图片转化为圆形
    public static BufferedImage makeRoundedCorner(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int cornerRadius;
        if (w>h)
            cornerRadius = w;
        else
            cornerRadius = h;
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = output.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g.setComposite(AlphaComposite.SrcAtop);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return output;
    }




    public static void  main (String args[]){

        InputStream inputStream = ImgUtils.class.getResourceAsStream("msyh_emoji.ttf");
        File file = new File("/Users/slj/Downloads/temp.jpg");
        BufferedImage bufferedImage;
        try {
            bufferedImage = waterMark(new FileInputStream(file), TextUtils.subString("票牛\uD83D\uDC8B",13)+"@票牛",Color.white,20,40,0.65f);
            ImageIO.write(bufferedImage,"jpg",new File("result.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
