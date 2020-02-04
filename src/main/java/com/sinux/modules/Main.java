package com.sinux.modules;

import com.sinux.modules.component.frame.BaseFrame;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class Main {
	private static boolean packFrame = false;
	public static BaseFrame baseFrame;

	public Main() {
		startClass();
	}
	/**
	 * 启动类
	 * @return void
	 */
	private void startClass() {
		baseFrame = new BaseFrame();
		setLocation(baseFrame);
	}
	/**
	 * 设置窗体位置
	 * @return void
	 */
	private static void setLocation(JFrame frame) {
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
			Dimension scSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension fSize = frame.getSize();
			if (fSize.height > scSize.height) {
				fSize.height = scSize.height;
			}
			if (fSize.width > scSize.width) {
				fSize.width = scSize.width;
			}
			frame.setLocation((scSize.width - fSize.width) / 2,
					(scSize.height - fSize.height) / 2);
		}
		baseFrame.setVisible(true);
	}
	/**
	 * main函数
	 * @return void
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
//					UIManager.setLookAndFeel(UIManager
//							.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				new Main();
			}
		});
	}

}
