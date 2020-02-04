package com.sinux.modules.component.panel;

import com.sinux.core.util.ConstantUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class ProcessBar extends JPanel {

    private JProgressBar jProgressBar;

    public ProcessBar(JProgressBar jPgBar) {
        this.jProgressBar = jPgBar;
        /**设置进度的 最小值 和 最大值*/
        this.jProgressBar.setMinimum(0);
        this.jProgressBar.setMaximum(100);
        /**设置当前进度值*/
        this.jProgressBar.setValue(0);
        /**绘制百分比文本（进度条中间显示的百分数）*/
        this.jProgressBar.setStringPainted(true);
        this.jProgressBar.setOpaque(true);
        this.jProgressBar.setBounds(new Rectangle(10, 200, 450, 15));

        this.setLayout(null);
        this.add(this.jProgressBar);
        this.setVisible(true);
        /**模拟延时操作进度, 每隔 0.5 秒更新进度*/
        new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ConstantUtil.currentProgress < 0) {
                    ConstantUtil.currentProgress = 0;
                }
                if (ConstantUtil.currentProgress > 100) {
                    ConstantUtil.currentProgress = 100;
                }
                jProgressBar.setValue(ConstantUtil.currentProgress);
            }
        }).start();
    }
}
