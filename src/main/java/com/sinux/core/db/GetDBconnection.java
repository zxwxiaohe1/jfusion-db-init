package com.sinux.core.db;

import com.sinux.modules.vo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class GetDBconnection {
    /**
     * db ResultSet
     */
    public ResultSet rs = null;
    /**
     * db Statement
     */
    public Statement ps = null;
    /**
     * db Connection
     */
    public Connection cn = null;

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog(GetDBconnection.class);
    /**
     * 单例静态内部类
     */
    private static class ConnectionSigle {
        private static GetDBconnection connection = new GetDBconnection();
    }

    private GetDBconnection(){}
    /**
     * 获得单例连接
     */
    public static GetDBconnection getSingleton(){
        return ConnectionSigle.connection;
    }
    /**
     * 获得连接
     *
     * @param dataBase DriverInfo类型
     * @return Connection
     */
    public Connection getConnection(DataBase dataBase) throws Exception {

        Class.forName(dataBase.getDriver());
        cn = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUserName(), dataBase.getPassword());
        return cn;
    }

    /**
     * 获得Statement
     *
     * @param dataBase DriverInfo类型
     * @return Statement
     */
    public Statement getStatement(DataBase dataBase) {
        try {
            if (cn == null) {
                cn = getConnection(dataBase);
            }
            ps = cn.createStatement();
        } catch (Exception e) {
            exception_Close();
            e.printStackTrace();
        }
        return ps;
    }

    /**
     * 执行SQL
     *
     * @param sql String类型
     * @return void
     * @throws SQLException
     */
    public void exceSQL(String sql) throws SQLException {
        try {
            ps = cn.createStatement();
            ps.execute(sql);
        } finally {
            stmt_Close();
        }
    }

    /**
     * 关闭Statement
     *
     * @return void
     */
    public void stmt_Close() {
        try {
            if (this.ps != null) {
                this.ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭对象
     *
     * @return void
     */
    public void exception_Close() {
        try {
            if (this.rs != null) {
                this.rs.close();
            }
            if (this.ps != null) {
                this.ps.close();
            }
            if (this.cn != null) {
                this.cn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
