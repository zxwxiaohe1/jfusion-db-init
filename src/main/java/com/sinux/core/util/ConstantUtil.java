package com.sinux.core.util;

import com.sinux.modules.vo.DataBase;
import com.sinux.modules.vo.MongoDB;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class ConstantUtil {

    public static String LABEL_CONFIRM = "确定";
    public static String LABEL_CANCLE = "取消";
    public static String LABEL_RESET = "重置";
    public static String BG_DEPLOY_LOCAL = "local";
    public static String BG_DEPLOY_DEV = "dev";
    public static String BG_DEPLOY_TEST = "test";

    public static String URL_TEMPLATE_MYSQL = "jdbc:1_$://2_$:3_$/4_$?useUnicode=true&characterEncoding=utf-8&pinGlobalTxToPhysicalConnection=true&allowMultiQueries=true";
    public static String URL_TEMPLATE_DM =  "jdbc:1_$://2_$:3_$/4";
    public static String URL_TEMPLATE_ORACLE =  "jdbc:1:thin:@2:3:4";
    public static String URL_TEMPLATE_MONGODB = "mongodb://1_$:2_$/3_$";
    public static Integer currentProgress = 0;
    public static String EXE_JAR_PROJECT = "jfusion-demo";
//    public static String EXE_JAR_PROJECT = "jfusion-mini";

    public final static String FILE_COLON_CHINESE = "：";
    public final static String FILE_COLON_ENGLISH = ":";
    public final static String FILE_SPERATOR_CHINESE = "；";
    public final static String FILE_SPERATOR_ENGLISH = ";";
    public final static String FILE_POINT_CHINESE = "，";
    public final static String FILE_POINT_ENGLISH = ",";
    public final static String FILE_PERIOD_CHINESE = "。";
    public final static String FILE_PERIOD_ENGLISH = ".";

    /**word文件模板路径 */
    public static final String FILE_TEMPLATE_HTML_NAME = "word.htm";
    /**word文件模板路径 */
    public static final String FILE_TEMPLATE_NAME = "article.doc";
    /**生成html文件路径 */
    public static final String FILE_DIR_HTML = "/techmate/doc/";
    /**编码方式*/
    public static final String CHARASTER_ENCODE_UTF8 = "UTF-8";
    /**编码方式*/
    public static final String CHARASTER_ENCODE_GBK = "GBK";


    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog(ConstantUtil.class);

    /**
     * @Title: 获得YML文件路径
     * @param dataBase DataBase
     * @return String
     * */
    public static String getYMLPath(DataBase dataBase){
        StringBuffer ymlSb = new StringBuffer();
        try {
            String projectPath = new File("").getCanonicalPath().trim();
            if (projectPath.contains(EXE_JAR_PROJECT)) {
                projectPath = projectPath.substring(0, projectPath.lastIndexOf(EXE_JAR_PROJECT)+EXE_JAR_PROJECT.length())+File.separator;
            } else {
                projectPath = projectPath+ File.separator+ EXE_JAR_PROJECT+ File.separator;
            }
            LOG.debug("ConstantUtil Path:" + projectPath);
            ymlSb.append(projectPath).append("src").append(File.separator)
                    .append("main").append(File.separator).append("resources").append(File.separator)
                    .append(dataBase.getDeployEnv()).append(File.separator).append("application.yml");
//            ymlSb.append(projectPath).append("resources").append(File.separator).append("application.yml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.debug("ab yml Path:" + ymlSb.toString());
        return ymlSb.toString();
    }
    /**
     * @Title: 获得SQL文件路径
     * @param dataBase DataBase
     * @return String
     * */
    public static String getSQLPath(DataBase dataBase){
        StringBuffer ymlSb = new StringBuffer();
        try {
            String projectPath = new File("").getCanonicalPath().trim();
            if (projectPath.contains(EXE_JAR_PROJECT)) {
                projectPath = projectPath.substring(0, projectPath.lastIndexOf(EXE_JAR_PROJECT)+EXE_JAR_PROJECT.length())+File.separator;
            } else {
                projectPath = projectPath+ File.separator+ EXE_JAR_PROJECT+ File.separator;
            }
            ymlSb.append(projectPath).append("db").append(File.separator)
                    .append(dataBase.getType()).append(File.separator).append("core.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.debug("ab db Path:" + ymlSb.toString());
        return ymlSb.toString();
    }
    /**
     * @Title:
     * @param message String
     * @return String
     * */
    public static void dialog(String message){
        JFrame frame = new JFrame();
        JPanel jp = new JPanel();
        JOptionPane.showMessageDialog(null, message, "操作结果", JOptionPane.INFORMATION_MESSAGE);
        frame.add(jp);
        frame.setSize(280, 280);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
