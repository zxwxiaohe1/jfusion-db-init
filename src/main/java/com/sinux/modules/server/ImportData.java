package com.sinux.modules.server;

import com.sinux.core.db.GetDBconnection;
import com.sinux.core.support.ennums.DriverTypes;
import com.sinux.core.util.ConstantUtil;
import com.sinux.core.util.StringUtil;
import com.sinux.modules.vo.DataBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class ImportData {

    private DataBase dataBase;
    private List<String> createList = new ArrayList<String>();
    private List<String> insertList = new ArrayList<String>();
    private List<String> dropList = new ArrayList<String>();
    private List<String> commentList = new ArrayList<String>();
    private List<String> alterList = new ArrayList<String>();
    private List<String> indexList = new ArrayList<String>();
    private List<String> otherList = new ArrayList<String>();
    /**
     * 日志对象
     */
    private static final Log LOG = LogFactory.getLog(ImportData.class);

    public ImportData() {}

    public ImportData(DataBase dataBase) {
        this.dataBase = dataBase;
    }
    /**
     * 执行数据导入
     * @return void
     */
    public void exce() {
        GetDBconnection dbCreate = GetDBconnection.getSingleton();
        Statement stmt = dbCreate.getStatement(this.dataBase);
        try {
            readAndClassify(ConstantUtil.getSQLPath(this.dataBase));
            if (this.dropList != null) {
                for (int i = 0, size = dropList.size(); i < size; i++) {
                    try {
                        dbCreate.exceSQL(dropList.get(i));
                    } catch (SQLException e) {
                        LOG.debug("drop table Exception: table not exist->"+dropList.get(i));
                        e.printStackTrace();
                    }
                }
            }
            ConstantUtil.currentProgress = 10;
            if (this.createList != null) {
                for (int i = 0, size = createList.size(); i < size; i++) {
                    try {
                        dbCreate.exceSQL(createList.get(i));
                    } catch (SQLException e) {
                        LOG.debug("create table Exception:" + createList.get(i));
                        e.printStackTrace();
                    }
                }
            }
            ConstantUtil.currentProgress = 20;
            if (this.commentList != null) {
                try {
                    for (int i = 0, size = commentList.size(); i < size; i++) {
                        stmt.addBatch(commentList.get(i));
                        if ((((i > 0) && ((i % 100) == 0)) || (i == (size - 1)))) {
                            try {
                                stmt.executeBatch();
                                stmt.clearBatch();
                                stmt.close();
                            } catch (SQLException e) {
                                LOG.debug("comment executeBatch Exception !");
                                e.printStackTrace();
                            }
                            stmt = dbCreate.getStatement(dataBase);
                        }
                    }
                } catch (SQLException e) {
                    LOG.debug("create comment Exception !");
                    e.printStackTrace();
                }
            }
            ConstantUtil.currentProgress = 30;
            if (this.indexList != null) {
                if (this.indexList != null) {
                    for (int i = 0, size = indexList.size(); i < size; i++) {
                        try {
                            dbCreate.exceSQL(indexList.get(i));
                        } catch (SQLException e) {
                            LOG.debug("index execute Exception:"+indexList.get(i));
                            e.printStackTrace();
                        }
                    }
                }
            }
            ConstantUtil.currentProgress = 40;
            if (this.alterList != null) {
                if (this.alterList != null) {
                    for (int i = 0, size = alterList.size(); i < size; i++) {
                        try {
                            dbCreate.exceSQL(alterList.get(i));
                        } catch (SQLException e) {
                            LOG.debug("alter execute Exception:"+alterList.get(i));
                            e.printStackTrace();
                        }
                    }
                }
            }
            ConstantUtil.currentProgress = 70;
            if (this.insertList != null) {
                try {
                    for (int i = 0, size = insertList.size(); i < size; i++) {
                        stmt.addBatch(insertList.get(i));
                        if ((((i > 0) && ((i % 100) == 0)) || (i == (size - 1)))) {
                            try {
                                stmt.executeBatch();
                                stmt.clearBatch();
                                stmt.close();
                            } catch (SQLException e) {
                                LOG.debug("insert execute Exception !");
                                e.printStackTrace();
                            }
                            stmt = dbCreate.getStatement(dataBase);
                        }
                        System.out.println(insertList.get(i));
                    }
                } catch (SQLException e) {
                    LOG.debug("insert Exception !");
                    e.printStackTrace();
                }
            }
            ConstantUtil.currentProgress = 90;
            if (this.otherList != null) {
                try {
                    for (int i = 0, size = otherList.size(); i < size; i++) {
                        stmt.addBatch(otherList.get(i));
                        if ((((i > 0) && ((i % 100) == 0)) || (i == (size - 1)))) {
                            try {
                                stmt.executeBatch();
                                stmt.clearBatch();
                            } catch (SQLException e) {
                                LOG.debug("other execute Exception !");
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException e) {
                    LOG.debug("other Exception !");
                    e.printStackTrace();
                }
            }
            ConstantUtil.currentProgress = 100;
        } finally {
            dbCreate.exception_Close();
        }
    }
    /**
     * 执行数据导入
     * @param path String类型
     * @return void
     */
    public void readAndClassify(String path) {
        try {
            File file = new File(path);
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(read);
            String line;
            String str = "";
            Integer ide = 0;
            StringBuffer sql = new StringBuffer();
            while ((line = reader.readLine()) != null) {

                sql.append(line + " ");
                str = sql.toString().trim();
                if ((str.startsWith("/*") && str.endsWith("*/"))
                        || (str.startsWith("--") && str.endsWith("--"))) {
                    sql.setLength(0);
                    continue;
                }
                if (str.endsWith(";")) {
                    if (str.startsWith("create") || str.startsWith("CREATE")) {
                        if (str.contains("index")) {
                            ide = str.indexOf("index");
                        } else if (str.contains("INDEX")) {
                            ide = str.indexOf("INDEX");
                        }
                        if (ide > 0 && ide < str.indexOf("(")) {
                            this.dropList.add("DROP INDEX " + getIndexName(str));
                            this.indexList.add(replaceKey(str));
                            sql.setLength(0);
                            continue;
                        }
                        this.createList.add(replaceKey(str));
                    } else if (str.startsWith("insert") || str.startsWith("INSERT")) {
                        this.insertList.add(replaceKey(str));
                    } else if (str.startsWith("drop") || str.startsWith("DROP")) {
                        this.dropList.add(replaceKey(str));
                    } else if (str.startsWith("comment") || str.startsWith("COMMENT")) {
                        this.commentList.add(replaceKey(str));
                    } else if (str.startsWith("alter") || str.startsWith("ALTER")) {
                        this.alterList.add(replaceKey(str));
                    } else {
                        this.otherList.add(replaceKey(str));
                    }
                    sql.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获得序列名称
     * @param sql String类型
     * @return String
     */
    public String getIndexName(String sql) {
        String indexName = "";
        if (StringUtil.isBlank(sql)) {
            return "";
        }
        Integer index = 0, on = 0;
        if (sql.contains(" index ")) {
            index = sql.indexOf(" index ");
        } else if (sql.contains(" INDEX ")) {
            index = sql.indexOf(" INDEX ");
        }
        if (sql.contains(" on ")) {
            on = sql.indexOf(" on ");
        } else if (sql.contains("ON")) {
            on = sql.indexOf(" ON ");
        }
        indexName = sql.substring(index + " index ".length(), on).trim();
        return indexName;
    }
    /**
     * 替换关键字
     * @param sql String类型
     * @return String
     */
    public String replaceKey (String sql) {

        if (StringUtil.isBlank(sql)) {
            return "";
        }
        if (DriverTypes.dm.name().equals(dataBase.getType())){
            if (sql.startsWith("create") || sql.startsWith("CREATE")) {
                sql = sql.replaceAll("BYTE\\)",")");
                sql = sql.replaceAll("CHAR\\)",")");
            }
            if (sql.contains(")")) {
                return sql.substring(0, sql.lastIndexOf(")")+1);
            }
        }
        return sql.replaceAll(";", "");
    }
}
