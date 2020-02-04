package com.sinux.core.support.ennums;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/19.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public enum DriverTypes {
    /**
     * mysql
     */
    mysql(){
        @Override
        public String driverType() {
            return "com.mysql.jdbc.Driver";
        }
    },
    /**
     * h2
     */
    h2(){
        @Override
        public String driverType() {
            return "org.h2.Driver";
        }
    },
    /**
     * oracle
     */
    oracle(){
        @Override
        public String driverType() {
            return "oracle.jdbc.driver.OracleDriver";
        }
    },
    /**
     * jtds_sqlserver sux
     */
    jtds_sqlserver(){
        @Override
        public String driverType() {
            return "net.sourceforge.jtds.jdbc.Driver";
        }
    },
    /**
     *sqlserver
     */
    sqlserver(){
        @Override
        public String driverType() {
            return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }
    },
    /**
     * dm
     */
    dm(){
        @Override
        public String driverType() {
            return "dm.jdbc.driver.DmDriver";
        }
    };
    /**
     * @Title: driver Type
     * @Description: 获得驱动
     * @return String
     * */
    public abstract String driverType();
    /**
     * @Title: getDriverType
     * @Description: 获得驱动
     * @return String
     * */
    public static String getDriverType(String name) {
        if(name == null) {
            return " ";
        }
        name = name.toLowerCase();
        for(DriverTypes v : values()) {
            if(name.trim().equals(v.name().trim()))  {
                return v.driverType();
            }
        }
        return mysql.driverType();
    }
}
