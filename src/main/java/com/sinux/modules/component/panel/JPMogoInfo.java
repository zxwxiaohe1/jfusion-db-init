package com.sinux.modules.component.panel;

import com.sinux.core.util.ConstantUtil;
import com.sinux.core.util.YMLUtil;
import com.sinux.modules.vo.DataBase;
import com.sinux.modules.vo.MongoDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class JPMogoInfo extends JPanel implements ActionListener {

    private JLabel ip = new JLabel();
    private JLabel port = new JLabel();
    private JLabel jlDatabase = new JLabel();

    private JTextField ipTextField = new JTextField();
    private JTextField portTextField = new JTextField();
    private JTextField jtDatabase = new JTextField();

    private JButton jbutton = new JButton("确定");

    private MongoDB mongoDB;

    public JPMogoInfo(){
        this.setLayout(null);
        this.mongoDB = (MongoDB)YMLUtil.getYmlInfo().get("mongoDB");

        ip.setText("IP:");
        ip.setForeground(Color.BLUE);
        ip.setFont(new Font("Default", Font.BOLD, 15));
        ip.setBounds(new Rectangle(10, 105, 150, 50));
        ipTextField.setOpaque(true);
        ipTextField.setText(mongoDB.getIp());
        ipTextField.setBounds(new Rectangle(110, 115, 200, 25));

        port.setText("port:");
        port.setForeground(Color.BLUE);
        port.setFont(new Font("Default", Font.BOLD, 15));
        port.setBounds(new Rectangle(330, 100, 50, 50));
        portTextField.setOpaque(true);
        portTextField.setText(mongoDB.getPort());
        portTextField.setBounds(new Rectangle(380, 115, 80, 25));

        jlDatabase.setText("实例数据库:");
        jlDatabase.setForeground(Color.BLUE);
        jlDatabase.setFont(new Font("Default", Font.BOLD, 15));
        jlDatabase.setBounds(new Rectangle(10, 160, 150, 50));
        jtDatabase.setOpaque(true);
        jtDatabase.setText(mongoDB.getDataBase());
        jtDatabase.setBounds(new Rectangle(110, 170, 350, 25));

        jbutton.setBorderPainted(true);
        jbutton.addActionListener(this);
        jbutton.setBounds(new Rectangle(250, 300, 80, 30));

        this.add(ip);
        this.add(ipTextField);
        this.add(port);
        this.add(portTextField);
        this.add(jlDatabase);
        this.add(jtDatabase);
        this.add(jbutton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("确定")) {
            this.mongoDB.setIp(ipTextField.getText().trim());
            this.mongoDB.setPort(portTextField.getText().trim());
            this.mongoDB.setDataBase(jtDatabase.getText().trim());
            this.mongoDB.fillUrl(ConstantUtil.URL_TEMPLATE_MONGODB);
            this.mongoDB.setBackup(this.mongoDB.getUrl());
            DataBase dataBase = new DataBase();
            dataBase.setDeployEnv(ConstantUtil.BG_DEPLOY_LOCAL);
            YMLUtil.updataYML(this.mongoDB,dataBase);
            ConstantUtil.dialog("修改成功!");
        }
    }
}
