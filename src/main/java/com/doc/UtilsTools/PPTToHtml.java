package com.doc.UtilsTools;




import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * com.doc.UtilsTools 于2019/11/29 由Administrator 创建 .
 */
public class PPTToHtml {

    public static boolean doPPTtoImage(File file) {
        boolean isppt = checkFile(file);
        if (!isppt) {
            System.out.println("The image you specify don't exit!");
            return false;
        }
        try {

            FileInputStream is = new FileInputStream(file);
            SlideShow ppt = new SlideShow(is);
            is.close();
            Dimension pgsize = ppt.getPageSize();
            Slide[] slide = ppt.getSlides();
            for (int i = 0; i < slide.length; i++) {
                System.out.print("第" + i + "页。");

                TextRun[] truns = slide[i].getTextRuns();
                for ( int k=0;k<truns.length;k++){
                    RichTextRun[] rtruns = truns[k].getRichTextRuns();
                    for(int l=0;l<rtruns.length;l++){
                        int index = rtruns[l].getFontIndex();
                        String name = rtruns[l].getFontName();
                        rtruns[l].setFontIndex(1);
                        rtruns[l].setFontName("宋体");
//                        System.out.println(rtruns[l].getText());
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width,pgsize.height, BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.BLUE);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                slide[i].draw(graphics);

                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream("D:/pict_"+ (i + 1) + ".jpeg");
                javax.imageio.ImageIO.write(img, "jpeg", out);
                out.close();

            }
            System.out.println("success!!");
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e);
            // System.out.println("Can't find the image!");
        } catch (IOException e) {
        }
        return false;
    }

    // function 检查文件是否为PPT
    public static boolean checkFile(File file) {

        boolean isppt = false;
        String filename = file.getName();
        String suffixname = null;
        if (filename != null && filename.indexOf(".") != -1) {
            suffixname = filename.substring(filename.indexOf("."));
            if (suffixname.equals(".ppt")) {
                isppt = true;
            }
            return isppt;
        } else {
            return isppt;
        }
    }


     public static void PPtToImage() throws IOException {
         HSLFSlideShow hslfSlideShow = new HSLFSlideShow("D:/123.ppt");
         SlideShow slideShow = new SlideShow(hslfSlideShow);
         if(slideShow.getSlides()!=null&&slideShow.getSlides().length>0){
             Slide slide = slideShow.getSlides()[0];//读取第一页
             TextRun[] textRuns = slide.getTextRuns();
             for (TextRun textRun : textRuns) {
                 for (RichTextRun richTextRun  : textRun.getRichTextRuns()) {
                     richTextRun.setFontName("宋体");//防止中文乱码
                 }
             }
             BufferedImage img = new BufferedImage(
                     slideShow.getPageSize().width,
                     slideShow.getPageSize().height,
                     BufferedImage.TYPE_INT_RGB);
             Graphics2D graphics = img.createGraphics();
             slide.draw(graphics);
             FileOutputStream fos = new FileOutputStream("d:/p1.png");
             ImageIO.write(img, "png", fos);
             fos.close();
         }
     }

    public static void main(String[] args) throws IOException {
//         doc2pdf("D:\\李军.doc");
//        PPtToImage();
        doPPTtoImage(new File("D:/123.ppt"));
    }

}
