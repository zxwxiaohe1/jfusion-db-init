package com.sinux.modules.component.frame;

import com.sinux.core.db.Status;
import com.sinux.core.util.ConstantUtil;
import com.sinux.core.util.YMLUtil;
import com.sinux.modules.component.panel.JPDbCl;
import com.sinux.modules.component.panel.JPMogoInfo;
import com.sinux.modules.component.panel.JPWordInfo;
import com.sinux.modules.component.panel.ProcessBar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class BaseFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel jlp = new JPanel(new BorderLayout());
    private JScrollPane jspt = new JScrollPane();
    private JPanel jrp = new JPanel();
    private JSplitPane jsplr = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
    private CardLayout cl = new CardLayout();
    //    private String BUTTON_IMPORT_DATA = "数据导入";
//    private String BUTTON_CONF_MONGODB = "MongoDB配置";
    private String BUTTON_CONTENT_FORMAT = "英语内容格式化";
    private String BUTTON_FINISH = "完成";
    private JButton[] jbutton = {
//            new JButton(BUTTON_IMPORT_DATA), new JButton(BUTTON_CONF_MONGODB),
            new JButton(BUTTON_CONTENT_FORMAT), new JButton(BUTTON_FINISH)
    };

    public BaseFrame() {
        setMainFrame();
        initJp();
    }

    /**
     * 设置主Frame
     *
     * @return void
     */
    public void setMainFrame() {

        this.getLayeredPane().setOpaque(false);
        ((JComponent) this.getContentPane()).setOpaque(false);

        for (int j = 0; j < 2; j++) {
            jlp.add(jbutton[j]);
            jbutton[j].setFont(new Font("Default", Font.BOLD, 15));
            jbutton[j].addActionListener(this);
            jbutton[j].setBorderPainted(false);
            jbutton[j].setHorizontalAlignment(JButton.LEFT);
            jbutton[j].setBounds(new Rectangle(0, j * 30 + 50, 190, 30));

        }
        jrp.setOpaque(false);
        jlp.setOpaque(false);
        jspt.setOpaque(false);
        jspt.getViewport().setOpaque(false);
        jsplr.setOpaque(false);
        jlp.add(jspt);
        jsplr.setLeftComponent(jlp);
        jrp.setBounds(200, 50, 300, 400);
        jsplr.setRightComponent(jrp);
        jsplr.setDividerLocation(200);
        jsplr.setDividerSize(1);
        jsplr.setEnabled(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("数据导入");
        this.setSize(700, 500);
        this.setResizable(false);
        this.getContentPane().add(jsplr);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIDefaults uiDf = UIManager.getLookAndFeelDefaults();
        uiDf.put("SplitPane.background", new ColorUIResource(Color.GRAY));
        SplitPaneUI ui = jsplr.getUI();
        if (ui instanceof BasicSplitPaneUI) {
            ((BasicSplitPaneUI) ui).getDivider().setBorder(null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals(BUTTON_IMPORT_DATA)) {
//            cl.show(jrp, "JPDbCl");
//        } else if (e.getActionCommand().equals(BUTTON_CONF_MONGODB)) {
//            cl.show(jrp, "JPMogoInfo");
//        } else
        if (e.getActionCommand().equals(BUTTON_CONTENT_FORMAT)) {
            cl.show(jrp, "JPWordInfo");
        } else if (e.getActionCommand().equals(BUTTON_FINISH)) {
            System.exit(0);
        }
    }

    public void initJp() {
        jrp.setLayout(cl);
//        jrp.add(new JPDbCl(), "JPDbCl");
//        jrp.add(new JPMogoInfo(), "JPMogoInfo");
        jrp.add(new JPWordInfo(), "JPWordInfo");

    }
}