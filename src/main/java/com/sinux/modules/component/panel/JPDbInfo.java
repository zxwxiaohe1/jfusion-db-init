package com.sinux.modules.component.panel;

import com.sinux.core.db.GetDBconnection;
import com.sinux.core.db.Status;
import com.sinux.core.support.ennums.DriverTypes;
import com.sinux.core.util.ConstantUtil;
import com.sinux.core.util.YMLUtil;
import com.sinux.modules.component.panel.JPMogoInfo;
import com.sinux.modules.component.panel.ProcessBar;
import com.sinux.modules.server.ImportData;
import com.sinux.modules.vo.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class JPDbInfo extends JPanel implements ActionListener {

    private JLabel jlDriver = new JLabel();
    private JLabel ip = new JLabel();
    private JLabel port = new JLabel();
    private JLabel jlUserName = new JLabel();
    private JLabel jlPassword = new JLabel();
    private JLabel jlDatabase = new JLabel();
    private JLabel error = new JLabel();

    private JButton jbConfire = new JButton(ConstantUtil.LABEL_CONFIRM);
    private JButton jbCancle = new JButton(ConstantUtil.LABEL_CANCLE);
    private JButton jbReset = new JButton(ConstantUtil.LABEL_RESET);

    private JTextField jtDriver = new JTextField();
    private JTextField ipTextField = new JTextField();
    private JTextField portTextField = new JTextField();
    private JTextField jtUserName = new JTextField();
    private JTextField jtDatabase = new JTextField();
    private JPasswordField jPasswordField = new JPasswordField();
    /**
     * 创建单选按钮组
     */
    private ButtonGroup bgDbType = new ButtonGroup();
    private JRadioButton jrMysql = new JRadioButton();

    private DataBase dataBase;

    public JPDbInfo() {
        this.dataBase = (DataBase)YMLUtil.getYmlInfo().get("dataBase");
        this.setLayout(null);
        jlDriver.setText("数据库驱动:");
        jlDriver.setForeground(Color.BLUE);
        jlDriver.setFont(new Font("Default", Font.BOLD, 15));
        jlDriver.setBounds(new Rectangle(10, 40, 150, 50));
        jtDriver.setOpaque(true);
        jtDriver.setText(dataBase.getDriver());
        jtDriver.setBounds(new Rectangle(110, 50, 350, 25));

        jrMysql.setText(DriverTypes.mysql.name());
        jrMysql.setSelected(true);
        jrMysql.setEnabled(false);
        jrMysql.setForeground(Color.black);
        jrMysql.addActionListener(this);
        jrMysql.setFont(new Font("Default", Font.BOLD, 10));
        jrMysql.setBounds(new Rectangle(110, 80, 100, 30));

        ip.setText("IP:");
        ip.setForeground(Color.BLUE);
        ip.setFont(new Font("Default", Font.BOLD, 15));
        ip.setBounds(new Rectangle(10, 105, 150, 50));
        ipTextField.setOpaque(true);
        ipTextField.setText(dataBase.getIp());
        ipTextField.setBounds(new Rectangle(110, 115, 200, 25));

        port.setText("port:");
        port.setForeground(Color.BLUE);
        port.setFont(new Font("Default", Font.BOLD, 15));
        port.setBounds(new Rectangle(330, 100, 50, 50));
        portTextField.setOpaque(true);
        portTextField.setText(dataBase.getPort());
        portTextField.setBounds(new Rectangle(380, 115, 80, 25));

        jlDatabase.setText("实例数据库:");
        jlDatabase.setForeground(Color.BLUE);
        jlDatabase.setFont(new Font("Default", Font.BOLD, 15));
        jlDatabase.setBounds(new Rectangle(10, 160, 150, 50));
        jtDatabase.setOpaque(true);
        jtDatabase.setText(dataBase.getDataBase());
        jtDatabase.setBounds(new Rectangle(110, 170, 350, 25));

        jlUserName.setText("用  户 名:");
        jlUserName.setForeground(Color.BLUE);
        jlUserName.setFont(new Font("Default", Font.BOLD, 15));
        jlUserName.setBounds(new Rectangle(10, 220, 150, 50));
        jtUserName.setOpaque(true);
        jtUserName.setText(dataBase.getUserName());
        jtUserName.setBounds(new Rectangle(110, 230, 350, 25));

        jlPassword.setText("密     码:");
        jlPassword.setForeground(Color.BLUE);
        jlPassword.setFont(new Font("Default", Font.BOLD, 15));
        jlPassword.setBounds(new Rectangle(10, 280, 150, 50));
        jPasswordField.setOpaque(true);
        jPasswordField.setText(dataBase.getPassword());
        jPasswordField.setBounds(new Rectangle(110, 290, 350, 25));

        error.setText("错");
        error.setForeground(Color.RED);
        error.setFont(new Font("Default", Font.BOLD, 15));
        error.setBounds(new Rectangle(10, 320, 560, 50));
        error.setVisible(false);

        jbConfire.setOpaque(false);
        jbConfire.addActionListener(this);
        jbConfire.transferFocus();
        jbConfire.setFont(new Font("Default", Font.BOLD, 15));
        jbConfire.setBounds(new Rectangle(10, 420, 110, 35));
        jbCancle.setOpaque(false);
        jbCancle.addActionListener(this);
        jbCancle.setFont(new Font("Default", Font.BOLD, 15));
        jbCancle.setBounds(new Rectangle(180, 420, 110, 35));
        jbReset.setOpaque(false);
        jbReset.addActionListener(this);
        jbReset.setFont(new Font("Default", Font.BOLD, 15));
        jbReset.setBounds(new Rectangle(350, 420, 110, 35));

        this.add(jlDriver);
        this.add(jtDriver);
        bgDbType.add(jrMysql);
//        bgDbType.add(jrOracle);
//        bgDbType.add(jrDm);
        this.add(jrMysql);
//        this.add(jrOracle);
//        this.add(jrDm);
        this.add(ip);
        this.add(ipTextField);
        this.add(port);
        this.add(portTextField);
        this.add(jlDatabase);
        this.add(jtDatabase);
        this.add(jlUserName);
        this.add(jtUserName);
        this.add(jlPassword);
        this.add(jPasswordField);
        this.add(error);
        this.add(jbConfire);
        this.add(jbCancle);
        this.add(jbReset);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(DriverTypes.mysql.name())) {
            jtDriver.setText(DriverTypes.mysql.driverType());
        } else if (e.getActionCommand().equals(DriverTypes.oracle.name())) {
            jtDriver.setText(DriverTypes.oracle.driverType());
        } else if (e.getActionCommand().equals(DriverTypes.dm.name())) {
            jtDriver.setText(DriverTypes.dm.driverType());
        }
    }
    /**
     * 导入数据
     * @return void
     */
    public void importData(){
        ImportData importData = new ImportData(dataBase);
        importData.exce();
        YMLUtil.updataYML(dataBase);
    }

    public Status getConnectStatus(){
        Status status = new Status();
        this.dataBase.setDriver(jtDriver.getText().trim());
        this.dataBase.setIp(ipTextField.getText().trim());
        this.dataBase.setPort(portTextField.getText().trim());
        this.dataBase.setDataBase(jtDatabase.getText().trim());
        this.dataBase.setUserName(jtUserName.getText().trim());
        this.dataBase.setPassword(jPasswordField.getText().trim());
        this.dataBase.setDeployEnv(ConstantUtil.BG_DEPLOY_LOCAL.trim());
        if (jrMysql.isSelected()) {
            this.dataBase.setType(DriverTypes.mysql.name());
            this.dataBase.fillUrl(ConstantUtil.URL_TEMPLATE_MYSQL);
        }
        GetDBconnection testConn = GetDBconnection.getSingleton();
        try {
            testConn.getConnection(this.dataBase);
        } catch (Exception e) {
            status.setCode(500);
            status.setError(e.getMessage());
            e.printStackTrace();
        }
        return status;
    }

    public JLabel getError() {
        return error;
    }

    public void setError(JLabel error) {
        this.error = error;
    }

}
