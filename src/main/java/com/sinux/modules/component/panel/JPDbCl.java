package com.sinux.modules.component.panel;

import com.sinux.core.db.GetDBconnection;
import com.sinux.core.db.Status;
import com.sinux.modules.vo.DataBase;
import com.sinux.modules.server.ImportData;
import com.sinux.core.support.ennums.DriverTypes;
import com.sinux.core.util.ConstantUtil;
import com.sinux.core.util.YMLUtil;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class JPDbCl extends JPanel implements ActionListener {

    private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
    private JPanel jpt = new JPanel();
    private JPanel jspn = new JPanel();
    private CardLayout clDb = new CardLayout();
    private String BUTTON_BACK = "返回";
    private String BUTTON_CONFIRM = "确定";
    private JButton[] jbutton = {new JButton(BUTTON_BACK), new JButton(BUTTON_CONFIRM)};

    JPDbInfo jPDbInfo = new JPDbInfo();

    public JPDbCl() {
        configInfo();
        initJpt();
    }

    public void configInfo() {

        this.setLayout(new GridLayout(1, 1));
        jpt.setLayout(null);
        jsp.setTopComponent(jspn);
        jsp.setBottomComponent(jpt);
        jsp.setDividerLocation(365);
        jsp.setDividerSize(1);
        jbutton[0].setEnabled(false);
        for (int k = 0; k < 2; k++) {
            jpt.add(jbutton[k]);
            jbutton[k].setBorderPainted(true);
            jbutton[k].addActionListener(this);
            jbutton[k].setBounds(new Rectangle(k * 150 + 80, 20, 80, 30));

        }
        this.add(jsp);
        this.setVisible(true);
    }

    public void initJpt() {
        jspn.setLayout(clDb);
        jspn.add(jPDbInfo, "JPDbInfo");
        jspn.add(new ProcessBar(new JProgressBar()), "ProcessBar");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(BUTTON_BACK)) {
            clDb.show(jspn, "JPDbInfo");
            jbutton[0].setEnabled(false);
            jbutton[1].setEnabled(true);
            jPDbInfo.getError().setVisible(false);
        } else if (e.getActionCommand().equals(BUTTON_CONFIRM)) {

            ConstantUtil.currentProgress = 0;
            jPDbInfo.getConnectStatus();
            Status status = jPDbInfo.getConnectStatus();
            if (200 == status.getCode()) {
                jbutton[0].setEnabled(true);
                jbutton[1].setEnabled(false);
                ConstantUtil.currentProgress = 0;
                clDb.show(jspn, "ProcessBar");
                new Thread() {
                    @Override
                    public void run() {
                        jPDbInfo.importData();
                    }
                }.start();
            } else {
                jPDbInfo.getError().setText(status.getError());
                jPDbInfo.getError().setVisible(true);
            }
        }
    }
//    public static void main(String args[]) {
//        JFrame jFrame = new JFrame();
//        jFrame.add(new JPDbCl(), BorderLayout.CENTER);
//        jFrame.setSize(450, 500);
//        jFrame.setVisible(true);
//    }
}

