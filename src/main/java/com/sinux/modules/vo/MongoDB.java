package com.sinux.modules.vo;

import java.util.Map;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/19.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class MongoDB {

    private String ip;
    private String port;
    private String dataBase;
    private String url;
    private String enabled;
    private String backup;

    /**
     * @Title: 填充连接模板
     * @param tmpUrl String
     * @return void
     * */
    public void fillUrl (String tmpUrl) {
        tmpUrl = tmpUrl.replace("1_$", this.getIp());
        tmpUrl = tmpUrl.replace("2_$", this.getPort());
        tmpUrl = tmpUrl.replace("3_$", this.getDataBase());
        this.setUrl(tmpUrl);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }
}
