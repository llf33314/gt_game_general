package com.gt.game.core.util;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @date 2012-11-26
 * @author xhw
 * 
 */
public class ImageCut {

    /**
     * 源图片路径名称如:c:\1.jpg
     */
    private String srcpath = "e:/poool.jpg";
    /**
     * 剪切图片存放路径名称.如:c:\2.jpg
     */
    private String subpath = "e:/pool_end";
    /**
     * jpg图片格式
     */
    private static final String IMAGE_FORM_OF_JPG = "jpg";
    /**
     * png图片格式
     */
    private static final String IMAGE_FORM_OF_PNG = "png";
    /**
     * 剪切点x坐标
     */
    private int x;

    /**
     * 剪切点y坐标
     */
    private int y;

    /**
     * 剪切点宽度
     */
    private int width;

    /**
     * 剪切点高度
     */
    private int height;

    public ImageCut() {

    }

    public ImageCut(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static void main(String[] args) throws Exception {
        ImageCut imageCut = new ImageCut(134, 0, 366, 366);
        imageCut.cut(imageCut.getSrcpath(), imageCut.getSubpath());
    }

    /**
     * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
     * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
     * 
     * @param postFix
     *            文件的后缀名
     * @return
     */
    public Iterator<ImageReader> getImageReadersByFormatName(String postFix) {
        switch (postFix) {
        case IMAGE_FORM_OF_JPG:
            return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_JPG);
        case IMAGE_FORM_OF_PNG:
            return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_PNG);
        default:
            return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_JPG);
        }
    }
    
    /**
     * Base64解码并生成图片
     * @param imgStr
     * @param srcUrl
     * @return
     */
	 public  boolean generateImage(String imgStr,String srcUrl,String newFileName)
     {//对字节数组字符串进行Base64解码并生成图片
		 if (imgStr == null) //图像数据为空
	            return false;
         BASE64Decoder decoder = new BASE64Decoder();
         try 
         {
             //Base64解码
             byte[] b = decoder.decodeBuffer(imgStr);
             for(int i=0;i<b.length;++i)
             {
                 if(b[i]<0)
                 {//调整异常数据
                     b[i]+=256;
                 }
             }
	         // create new file by date
             File dir = new File(srcUrl);
             if (!dir.exists()) {
            	 dir.mkdirs();
             }
             srcUrl += "/" + newFileName;
	         //生成图片
	         OutputStream out = new FileOutputStream(srcUrl);    
	         out.write(b);
	         out.flush();
	         out.close();
	         return true;
         } 
         catch (Exception e) 
         {
        	 e.printStackTrace();
        	 return false;
         }
     }

    /**
     * 对图片裁剪，并把裁剪完蛋新图片保存 。
     * @param srcpath 源图片路径
     * @param subpath 剪切图片存放路径
     * @throws IOException
     */
    public boolean cut(String srcpath, String subpath) throws IOException {
        FileInputStream is = null;
        ImageInputStream iis = null;
        boolean result=true;
        try {
            // 读取图片文件
            is = new FileInputStream(srcpath);

            // 获取文件的后缀名
            String postFix = getPostfix(srcpath);
            System.out.println("图片格式为：" + postFix);
            /*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
             * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
             */
            Iterator<ImageReader> it = getImageReadersByFormatName(postFix);

            ImageReader reader = it.next();
            // 获取图片流
            iis = ImageIO.createImageInputStream(is);

            /*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis, true);

            /*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
             * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();

            /*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            Rectangle rect = new Rectangle(x,y,width,height);

            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            /*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
             * BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0, param);

            // 保存新图片
            ImageIO.write(bi, postFix, new File(subpath ));
            ContinueFTP myFtp = new ContinueFTP();
			try {
				myFtp.upload(subpath);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }catch(Exception e){
        	e.printStackTrace();
        	result=false;
        }finally {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
            
            return result;
        }
    }

    /**
     * 获取inputFilePath的后缀名，如："e:/test.pptx"的后缀名为："pptx"<br>
     * 
     * @param inputFilePath
     * @return
     */
    public String getPostfix(String inputFilePath) {
        return inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrcpath() {
        return srcpath;
    }

    public void setSrcpath(String srcpath) {
        this.srcpath = srcpath;
    }

    public String getSubpath() {
        return subpath;
    }

    public void setSubpath(String subpath) {
        this.subpath = subpath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    
    
    /* 
     * 根据尺寸图片居中裁剪 
     */  
     public static void cutCenterImage(String src,int w,int h) throws IOException{   
         Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");   
         ImageReader reader = (ImageReader)iterator.next();   
         InputStream in=new FileInputStream(src);  
         ImageInputStream iis = ImageIO.createImageInputStream(in);   
         reader.setInput(iis, true);   
         ImageReadParam param = reader.getDefaultReadParam();   
         int imageIndex = 0;   
         Rectangle rect = new Rectangle((reader.getWidth(imageIndex)-w)/2, (reader.getHeight(imageIndex)-h)/2, w, h);    
         param.setSourceRegion(rect);   
         BufferedImage bi = reader.read(0,param);     
         ImageIO.write(bi, "jpg", new File(src));             
         ContinueFTP myFtp = new ContinueFTP();
		 try {
			myFtp.upload(src);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
     }  
     
     
     /* 
      * 图片缩放 
      */  
     public static void zoomImage(String src,String dest,int w,int h) throws Exception {  
         double wr=0,hr=0;  
         File srcFile = new File(src);  
         File destFile = new File(dest);  
         BufferedImage bufImg = ImageIO.read(srcFile);  
         Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);  
         wr=w*1.0/bufImg.getWidth();  
         hr=h*1.0 / bufImg.getHeight();  
         AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);  
         Itemp = ato.filter(bufImg, null);  
         try {  
             ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);  
             ContinueFTP myFtp = new ContinueFTP();
             try {
     			myFtp.upload(dest);
     		 } catch (Exception e) {
     			e.printStackTrace();
     		 }
         } catch (Exception ex) {  
             ex.printStackTrace();  
         }  
           
     }  
     
 	/**
 	 * 源字符串,直接解析得到后缀名
 	 * 
 	 * @param original
 	 */
     public static String getExt(String original) {
 		String[] temps = original.split(",");
 		String ext = "";
 		try {
 			String mine = temps[0];
 			String regext = "(?<=(data:image/))(.?)*(?=(;base64))";
 			Pattern pattern = Pattern.compile(regext);
 			Matcher matcher = pattern.matcher(mine);
 			while (matcher.find()) {
 				ext = matcher.group();
 				break;
 			}
 		} catch (Exception e) {
 			ext = "";
 			e.printStackTrace();
 		}
 		return ext;
 	}
}