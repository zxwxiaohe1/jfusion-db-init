package com.sinux.core.util;

import com.sinux.modules.vo.DataBase;
import com.sinux.core.support.ennums.DriverTypes;
import com.sinux.core.utils.StringUtils;
import com.sinux.modules.vo.MongoDB;
import org.springframework.util.ObjectUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class YMLUtil {

    private static Map<String, Object> ymlInfo = new HashMap<String, Object>();

    /**
     * 获得YML文件配置属性映射对象
     *
     * @param dataBase DataBase
     * @return Map
     */
    public static Map getPriperty(DataBase dataBase) {
        Yaml yaml = new Yaml();
        try {
            /**将值转换为Map*/
            Map map = (Map) yaml.load(new FileInputStream(ConstantUtil.getYMLPath(dataBase)));
            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获得YML文件配置属性映射对象DriverInfo
     *
     * @return DataBase
     */
    public static Map<String, Object> getYmlInfo() {
        if (!ObjectUtils.isEmpty(ymlInfo)) {
            return ymlInfo;
        }
        Map<String, Object> newYmlInfo = new HashMap<String, Object>();
        DataBase dataBase = new DataBase();
        MongoDB mongoDB = new MongoDB();
        dataBase.setDeployEnv(ConstantUtil.BG_DEPLOY_LOCAL);
        Map map = getPriperty(dataBase);
        if (map != null) {
            Map datasource = (Map) map.get("datasource");
            if (datasource != null) {
                Map datasources = (Map) datasource.get("datasources");
                if (datasources != null) {
                    Map primaryDataSource = (Map) datasources.get("primaryDataSource");
                    if (primaryDataSource != null) {
                        Map xaDataSource = (Map) primaryDataSource.get("xaDataSource");
                        if (xaDataSource != null) {
                            String url = ((String) xaDataSource.get("url")).trim();
                            dataBase.setUrl((String) xaDataSource.get("url"));
                            if (StringUtils.isNotBlank(url)) {
                                setSplitInfo(dataBase);
                            }
                            dataBase.setUserName((String) xaDataSource.get("username").toString());
                            dataBase.setPassword((String) xaDataSource.get("password").toString());
                        }
                    }
                }
            }
            Map jfusion = (Map) map.get("jfusion");
            if (jfusion != null) {
                Map addon = (Map) jfusion.get("addon");
                if (addon != null) {
                    Map mongodb = (Map) addon.get("mongodb");
                    if (mongodb != null) {
                        mongoDB.setEnabled(String.valueOf(mongodb.get("enabled")));
                        mongoDB.setUrl(String.valueOf(mongodb.get("uri")));
                        if (StringUtils.isNotBlank(mongoDB.getUrl())) {
                            setSplitInfo(mongoDB);
                        }
                    }
                    Map pools = (Map) addon.get("pools");
                    if (pools != null) {
                        mongoDB.setBackup(String.valueOf(pools.get("backup")));
                    }
                }

            }
        }
        newYmlInfo.put("dataBase", dataBase);
        newYmlInfo.put("mongoDB", mongoDB);
        ymlInfo = newYmlInfo;
        return ymlInfo;
    }

    /**
     * 获得YML文件配置属性映射对象DataBase
     *
     * @return DataBase
     */
    public static void setSplitInfo(DataBase driverInfo) {
        if (driverInfo == null) {
            return;
        }
        if (StringUtils.isNotBlank(driverInfo.getUrl())) {
            String[] args = driverInfo.getUrl().split(":");
            driverInfo.setDriver(DriverTypes.getDriverType(args[1].trim()));
            driverInfo.setType(args[1].trim());
            String ip = args[2].trim();
            String port = args[3].trim();
            String dataBase = args[3].trim();
            if (DriverTypes.oracle.name().trim().equals(driverInfo.getType())) {
                ip = args[3].trim();
                port = args[4].trim();
                dataBase = args[5].trim();
            }
            if (ip.contains("/")) {
                ip = ip.replaceAll("/", "").trim();
            }
            if (ip.contains("@")) {
                ip = ip.replaceAll("@", "").trim();
            }
            driverInfo.setIp(ip);
            if (port.contains("/")) {
                String[] portArgs = port.split("/");
                port = portArgs[0];
            }
            driverInfo.setPort(port);
            if (dataBase.contains("/")) {
                String[] dataBaseArgs = dataBase.split("/");
                dataBase = dataBaseArgs[1];
                if (dataBase.contains("?")) {
                    dataBase = dataBase.substring(0, dataBase.indexOf("?"));
                }
            }
            driverInfo.setDataBase(dataBase);
        }
    }
    /**
     * 获得YML文件配置属性映射对象MongoDB
     *
     * @return DataBase
     */
    public static void setSplitInfo(MongoDB mongoDB) {
        if (mongoDB == null) {
            return;
        }
        if (StringUtils.isNotBlank(mongoDB.getUrl())) {
            String[] args = mongoDB.getUrl().split(":");
            String ip = args[1].trim();
            String port = args[2].trim();
            String dataBase = "local";
            if (ip.contains("/")) {
                ip = ip.replaceAll("/", "").trim();
            }
            if (port.contains("/")) {
                String[] portArgs = port.split("/");
                port = portArgs[0];
                dataBase = portArgs[1];
            }
            mongoDB.setIp(ip);
            mongoDB.setPort(port);
            mongoDB.setDataBase(dataBase);
        }
    }
    /**
     * 更新YML文件配置属性
     *
     * @param dataBase DriverInfo类型
     * @return void
     */
    public static void updataYML(DataBase dataBase) {
        if (dataBase == null) {
            return;
        }
        try {
            File file = new File(ConstantUtil.getYMLPath(dataBase));
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            String line = "";
            boolean urlLock = false;
            boolean usernameLock = false;
            boolean passwordLock = false;
            while (null != (line = br.readLine())) {
                if (line.trim().startsWith("url:") && !urlLock && StringUtils.isNotBlank(dataBase.getUrl())) {
                    line = line.substring(0, line.indexOf(":") + 1) + " " + dataBase.getUrl();
                    urlLock = true;
                } else if (line.trim().startsWith("username:") && !usernameLock && StringUtils.isNotBlank(dataBase.getUserName())) {
                    line = line.substring(0, line.indexOf(":") + 1) + " " + dataBase.getUserName();
                    usernameLock = true;
                } else if (line.trim().startsWith("password:") && !passwordLock && StringUtils.isNotBlank(dataBase.getPassword())) {
                    line = line.substring(0, line.indexOf(":") + 1) + " " + dataBase.getPassword();
                    passwordLock = true;
                }
                buf = buf.append(line + System.getProperty("line.separator"));
            }
            br.close();
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos, "UTF-8"), true);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 更新YML文件配置属性
     *
     * @param mongoDB MongoDB类型
     * @return void
     */
    public static void updataYML(MongoDB mongoDB,DataBase dataBase) {
        if (mongoDB == null) {
            return;
        }
        try {
            File file = new File(ConstantUtil.getYMLPath(dataBase));
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            String line = "";
            Integer lineNum = 0;
            while (null != (line = br.readLine())) {
                lineNum += 1;
                if (line.trim().startsWith("enabled:") && StringUtils.isNotBlank(mongoDB.getEnabled())) {
                    if (lineNum>59 && lineNum <63) {
                        line = line.substring(0, line.indexOf(":") + 1) + " " + mongoDB.getEnabled();
                    }
                } else if (line.trim().startsWith("uri:") && StringUtils.isNotBlank(mongoDB.getUrl())) {
                    if (lineNum>61 && lineNum <64) {
                        line = line.substring(0, line.indexOf(":") + 1) + " " + mongoDB.getUrl();
                    }
                } else if (line.trim().startsWith("backup:") && StringUtils.isNotBlank(mongoDB.getBackup())) {
                    if (lineNum>62 && lineNum <65) {
                        line = line.substring(0, line.indexOf(":") + 1) + " " + mongoDB.getBackup();
                    }
                }
                buf = buf.append(line + System.getProperty("line.separator"));
            }
            br.close();
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos, "UTF-8"), true);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        ThreadLocal<Integer> FORCE_STATUS   = ThreadLocal.withInitial(()->{
            return 0;
        });
        FORCE_STATUS.set(1);


        new Thread(() -> {

            System.out.println(FORCE_STATUS.get());
        }).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(FORCE_STATUS.get());
    }
}

