package com.sinux.modules.component.panel;

import com.sinux.core.support.ennums.WordExportType;
import com.sinux.core.support.export.ExportWord;
import com.sinux.core.util.ConstantUtil;
import com.sinux.modules.server.WordFormat;
import com.sinux.modules.vo.SingleWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class JPWordInfo extends JPanel implements ActionListener {

    private JLabel jLfile = new JLabel();
    private JLabel jLFormatType = new JLabel();

    private JTextField jFfile = new JTextField();

    private JButton commit = new JButton("转换");
    /**
     * 创建单选按钮组
     */
    private ButtonGroup formatType = new ButtonGroup();
    private JRadioButton word = new JRadioButton();
    private JRadioButton phrase = new JRadioButton();
    private JRadioButton sentence = new JRadioButton();

    JButton choose = new JButton();
    /**文件选择器*/
    JFileChooser jfc=new JFileChooser();

    private final String JBUTTON_CONTEXT_CHOOSE = "...";
    private final String JBUTTON_CONTEXT_COMMIT = "转换";

    public JPWordInfo(){
        jfc.setCurrentDirectory(new File("c:\\"));
        this.setLayout(null);
        jLfile.setText("选择文件:");
        jLfile.setForeground(Color.BLUE);
        jLfile.setFont(new Font("Default", Font.BOLD, 15));
        jLfile.setBounds(new Rectangle(10, 105, 150, 50));
        jFfile.setOpaque(true);
        jFfile.setBounds(new Rectangle(110, 115, 200, 25));

        choose.setText(JBUTTON_CONTEXT_CHOOSE);
        choose.setOpaque(false);
        choose.addActionListener(this);
        choose.transferFocus();
        choose.setFont(new Font("Default", Font.BOLD, 15));
        choose.setBounds(new Rectangle(325, 115, 70, 25));

        jLFormatType.setText("转换方式:");
        jLFormatType.setForeground(Color.BLUE);
        jLFormatType.setFont(new Font("Default", Font.BOLD, 15));
        jLFormatType.setBounds(new Rectangle(10, 150, 100, 50));

        word.setText(WordExportType.word.type());
        word.setSelected(true);
        word.setForeground(Color.black);
        word.addActionListener(this);
        word.setFont(new Font("Default", Font.BOLD, 10));
        word.setBounds(new Rectangle(110, 160, 50, 30));
        phrase.setText(WordExportType.phrase.type());
        phrase.setSelected(true);
        phrase.setForeground(Color.black);
        phrase.addActionListener(this);
        phrase.setFont(new Font("Default", Font.BOLD, 10));
        phrase.setBounds(new Rectangle(180, 160, 50, 30));
        sentence.setText(WordExportType.sentence.type());
        sentence.setSelected(true);
        sentence.setForeground(Color.black);
        sentence.addActionListener(this);
        sentence.setFont(new Font("Default", Font.BOLD, 10));
        sentence.setBounds(new Rectangle(250, 160, 50, 30));
        commit.setBorderPainted(true);
        commit.addActionListener(this);
        commit.setBounds(new Rectangle(250, 300, 80, 30));

        formatType.add(word);
        formatType.add(phrase);
        formatType.add(sentence);
        this.add(word);
        this.add(phrase);
        this.add(sentence);
        this.add(jLfile);
        this.add(jFfile);
        this.add(jfc);
        this.add(choose);
        this.add(jLFormatType);
        this.add(commit);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        WordFormat wordFormat = new WordFormat();
        if (e.getActionCommand().equals(JBUTTON_CONTEXT_CHOOSE)) {
            /**设定只能选择到文件*/
            jfc.setFileSelectionMode(0);
            /**此句是打开文件选择器界面的触发语句*/
            int state=jfc.showOpenDialog(null);
            if(state==1){
                /**撤销则返回*/
                return;
            }
            else{
                /**f为选择到的文件*/
                File f=jfc.getSelectedFile();
                jFfile.setText(f.getAbsolutePath());
            }
        } else if (e.getActionCommand().equals(JBUTTON_CONTEXT_COMMIT)) {
            if (word.isSelected()) {
                Map<String, SingleWord> appleMap = wordFormat.singleWordFormat(jFfile.getText()).stream().collect(Collectors.toMap(SingleWord::getWord, a -> a,(k1, k2)->k1));
                ExportWord exportWord = new ExportWord();
                exportWord.exec(new ArrayList<>(appleMap.values()), WordExportType.word.name());
            } else if(phrase.isSelected()) {
                List<SingleWord> words = wordFormat.phraseFormat(jFfile.getText());
                ExportWord exportWord = new ExportWord();
                exportWord.exec(words, WordExportType.phrase.name());
            } else {
                List<SingleWord> words = wordFormat.sentenceFormat(jFfile.getText());
                ExportWord exportWord = new ExportWord();
                exportWord.exec(words, WordExportType.sentence.name());
            }
            ConstantUtil.dialog("已生成成功!");
        }
    }
}
