package com.sinux.modules.vo;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/19.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class DataBase {

    private String driver;
    private String url;
    private String userName;
    private String password;
    private String type;
    private String ip;
    private String port;
    private String dataBase;
    private String deployEnv;

    /**
     * @Title: 填充连接模板
     * @param tmpUrl String
     * @return void
     * */
    public void fillUrl (String tmpUrl) {
        tmpUrl = tmpUrl.replace("1_$", this.getType());
        tmpUrl = tmpUrl.replace("2_$", this.getIp());
        tmpUrl = tmpUrl.replace("3_$", this.getPort());
        tmpUrl = tmpUrl.replace("4_$", this.getDataBase());
        this.setUrl(tmpUrl);
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDeployEnv() {
        return deployEnv;
    }

    public void setDeployEnv(String deployEnv) {
        this.deployEnv = deployEnv;
    }
}
