package com.gt.game.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 多粉应用类配置文件
 * Created by psr on 2017/9/19 0019.
 */
@Component
public class ApplyProperties {

    @Value("${apply.ftp.ip}")
    private String ftpIp; // ftpIp
    @Value("${apply.ftp.port}")
    private String ftpPort; // ftpport
    @Value("${apply.ftp.user}")
    private String ftpUser; // ftpUser
    @Value("${apply.ftp.pwd}")
    private String ftpPwd; // ftpPwd
    @Value("${apply.ftp.need}")
    private boolean ftpNeed; // ftpPwd
    @Value("${apply.res.doc.path}")
    private String resDocPath; // 文档路径
    @Value("${apply.resource.url.prefix}")
    private String resourceUrlPrefix; // 资源访问路径
    @Value("${apply.mobile.baseUrl}")
    private String mobileBaseUrl; // 移动端访问链接
    @Value("${apply.path.imageUrl}")
    private String imageUrl; // 图片上传
    @Value("${apply.path.voiceUrl}")
    private String voiceUrl;//音频上传路径

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPwd() {
        return ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }

    public String getResDocPath() {
        return resDocPath;
    }

    public void setResDocPath(String resDocPath) {
        this.resDocPath = resDocPath;
    }

    /**
     * 资源访问路径
     * @return
     */
    public String getResourceUrlPrefix() {
        return resourceUrlPrefix;
    }

    public void setResourceUrlPrefix(String resourceUrlPrefix) {
        this.resourceUrlPrefix = resourceUrlPrefix;
    }

    public boolean isFtpNeed() {
        return ftpNeed;
    }

    public void setFtpNeed(boolean ftpNeed) {
        this.ftpNeed = ftpNeed;
    }

    public String getMobileBaseUrl() {
        return mobileBaseUrl;
    }

    public void setMobileBaseUrl(String mobileBaseUrl) {
        this.mobileBaseUrl = mobileBaseUrl;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }
}
