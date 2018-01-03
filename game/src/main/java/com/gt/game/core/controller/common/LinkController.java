package com.gt.game.core.controller.common;

import com.gt.axis.server.wxmp.ShortUrlServer;
import com.gt.game.core.util.QRcodeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 链接controller
 * Created by psr on 2017/9/21 0021.
 */
@Api(value = "/app/link", description = "链接控制器")
@RestController
@RequestMapping(value = "/app/link")
public class LinkController {

    private static Logger logger = Logger.getLogger(LinkController.class);

    @ApiOperation(value = "生成二维码", notes = "传入链接，生成二维码")
    @RequestMapping(value = "/buildQrcode", method = RequestMethod.GET)
    public void buildQrcode(@RequestParam @ApiParam("移动端链接") String url, HttpServletRequest request, HttpServletResponse response){
        QRcodeKit.buildQRcode(url, 300, 300, response);
    }

    /**
     * 下载提取链接二维码
     * @param request
     * @param response
     * @param url
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "下载二维码", notes = "下载二维码")
    @RequestMapping(value = "/downQrcode", method = RequestMethod.GET)
    public void downQrcode(HttpServletRequest request, HttpServletResponse response, @RequestParam @ApiParam("移动端链接") String url) throws UnsupportedEncodingException {
        String filename = "二维码.jpg";
        response.addHeader("Content-Disposition", "attachment;filename="
                + new String(filename.replaceAll(" ", "").getBytes("utf-8"),
                "iso8859-1"));
        response.setContentType("application/octet-stream");
        QRcodeKit.buildQRcode(url, 450, 450, response);
    }

    /**
     * 获取短连接
     * @param request
     * @param response
     * @param url
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "获取短信链接", notes = "获取短信链接")
    @RequestMapping(value = "/getShorUrl", method = RequestMethod.GET)
    public String getShorUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam @ApiParam("移动端链接") String url) throws UnsupportedEncodingException {
      String s = ShortUrlServer.getShorUrl(url);
      return s;
    }

    /**
     * 跳转视频链接
     * @param request
     * @param response
     * @param key
     */
    @ApiOperation(value = "跳转视频链接", notes = "跳转视频链接")
    @RequestMapping(value = "{key}/getVideourl", method = RequestMethod.GET)
    public void getVideourl(HttpServletRequest request, HttpServletResponse response, @PathVariable("key") String key ){
        try {
//            String url = course.urlquery(key);
            String url = "https://v.qq.com/iframe/player.html?vid=g0550rl08hs&tiny=0&auto=0";
            if(!url.equals("")){
                if(!(url.indexOf("http:")>-1 || url.indexOf("https:")>-1)){
                    url = "http://" + url;
                }
                url = url.trim();
                response.sendRedirect(url);
            }else{
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("管理员很懒,没有放视频^_^	");
                response.getWriter().flush();
                response.getWriter().close();
            }
        } catch (Exception e) {
            logger.error(""+e.getMessage());
            e.printStackTrace();
        }
    }

}
