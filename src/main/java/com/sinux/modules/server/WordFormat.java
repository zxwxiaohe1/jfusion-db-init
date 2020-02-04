package com.sinux.modules.server;

import com.sinux.core.support.ennums.WordType;
import com.sinux.core.util.ConstantUtil;
import com.sinux.core.util.FileUtil;
import com.sinux.core.util.StringUtil;
import com.sinux.modules.vo.SingleWord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: Created by heyong.
 * @createtime: on 2017/3/1+.
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class WordFormat {

    public List<SingleWord> singleWordFormat(String filePath) {
        if (filePath == null || filePath == "") {
            return null;
        }
        filePath = FileUtil.getPathStr(filePath);
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "utf-8");
            BufferedReader read = new BufferedReader(isr);
            String line = "";
            String[] strs = null;
            String word = "";
            String mean = "";
            SingleWord singleWord = new SingleWord();
            List<SingleWord> singleWords = new ArrayList<SingleWord>();
            List<SingleWord.Mean> means = new ArrayList<SingleWord.Mean>();
            SingleWord.Mean singleMean = singleWord.new Mean();
            while ((line = read.readLine()) != null) {
                if (StringUtil.isNotBlank(line)) {
                    line = StringUtil.removeIndexSpace(line);
                    singleMean = singleWord.new Mean();
                    if (line.contains(ConstantUtil.FILE_COLON_CHINESE)) {
                        strs = line.split(ConstantUtil.FILE_COLON_CHINESE);
                        singleWord.setMeans(means);
                        singleWords.add(singleWord);
                        singleWord = new SingleWord();
                        means = new ArrayList<SingleWord.Mean>();
                    } else if (line.contains(ConstantUtil.FILE_COLON_ENGLISH)) {
                        strs = line.split(ConstantUtil.FILE_COLON_ENGLISH);
                        singleWord.setMeans(means);
                        singleWords.add(singleWord);
                        singleWord = new SingleWord();
                        means = new ArrayList<SingleWord.Mean>();
                    } else {
                        mean = line;
                    }
                    if (strs != null) {
                        if (strs.length >= 1) {
                            word = strs[0].toLowerCase();
                        }
                        if (strs.length >= 2) {
                            mean = strs[1];
                        }
                    }
                    if (StringUtil.isNotBlank(word) && StringUtil.isNotBlank(mean)) {
                        singleWord.setWord(word);
                        if (mean.contains(ConstantUtil.FILE_PERIOD_ENGLISH)) {
                            strs = mean.split("\\" + ConstantUtil.FILE_PERIOD_ENGLISH);
                        }
                        if (strs != null) {
                            if (strs.length >= 1) {
                                singleMean.setWordType(WordType.type(strs[0]));
                            }
                            if (strs.length >= 2) {
                                mean = strs[1];
                            }
                        }
                        if (StringUtil.isNotBlank(mean)) {
                            mean = mean.replaceAll(ConstantUtil.FILE_SPERATOR_CHINESE, ConstantUtil.FILE_SPERATOR_ENGLISH)
                                    .replaceAll(ConstantUtil.FILE_COLON_CHINESE, ConstantUtil.FILE_COLON_ENGLISH)
                                    .replaceAll(ConstantUtil.FILE_POINT_CHINESE, ConstantUtil.FILE_POINT_ENGLISH)
                                    .replaceAll(ConstantUtil.FILE_PERIOD_CHINESE, ConstantUtil.FILE_PERIOD_ENGLISH);
                            if (mean.endsWith(ConstantUtil.FILE_SPERATOR_ENGLISH)) {
                                mean = mean.substring(0, mean.length() - ConstantUtil.FILE_SPERATOR_ENGLISH.length());
                            }
                            singleMean.setMean(mean);
                            mean = "";
                            strs = null;
                        }
                    }
                    means.add(singleMean);
                    line = "";
                }
            }
            singleWord.setMeans(means);
            singleWords.add(singleWord);
            return singleWords;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SingleWord> phraseFormat(String filePath) {
        if (filePath == null || filePath == "") {
            return null;
        }
        filePath = FileUtil.getPathStr(filePath);
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "utf-8");
            BufferedReader read = new BufferedReader(isr);
            String line = "";
            String temp = "";
            SingleWord singleWord = new SingleWord();
            List<SingleWord> singleWords = new ArrayList<SingleWord>();
            List<SingleWord.Mean> means = new ArrayList<SingleWord.Mean>();
            SingleWord.Mean singleMean = singleWord.new Mean();
            Integer index = -1;
            Boolean isMean = false;
            Set<String> phraseSingle = new HashSet<String>();
            while ((line = read.readLine()) != null) {
                System.out.println(line);
                if (StringUtil.isNotBlank(line)) {
                    temp = StringUtil.removeAllSpace(line);
                    if (phraseSingle.contains(temp)) {
                        continue;
                    }
                    phraseSingle.add(temp);
                    line = StringUtil.removeIndexSpace(line);
                    char[] strArr = line.toCharArray();
                    for (int i = 0, size = strArr.length; i < size; i++) {
                        if (StringUtil.isNum(String.valueOf(strArr[i]))) {
                            continue;
                        }
                        if (StringUtil.isSpecialChar(String.valueOf(strArr[i]))) {
                            continue;
                        }
                        index = i;
                        break;
                    }
                    if (index >= 0) {
                        isMean = true;
                        if (index == 0) {
                            Boolean isSpaceLast = StringUtil.isBlank(singleMean.getMean());
                            singleMean.setMean(singleMean.getMean() + line.trim());
                            if (isSpaceLast) {
                                means.add(singleMean);
                            }
                        } else {
                            singleWord.setMeans(means);
                            singleWords.add(singleWord);
                            singleWord = new SingleWord();
                            if (isMean) {
                                singleMean = singleWord.new Mean();
                            }
                            singleWord.setWord(singleWord.getWord() + line.substring(0, index).trim().toLowerCase());
                            singleMean.setMean(singleMean.getMean() + line.substring(index).trim());
                            means = new ArrayList<SingleWord.Mean>();
                            means.add(singleMean);

                        }
                    } else {
                        if (isMean) {
                            singleWord.setMeans(means);
                            singleWords.add(singleWord);
                            singleWord = new SingleWord();
                            singleMean = singleWord.new Mean();
                            means = new ArrayList<SingleWord.Mean>();
                        }
                        singleWord.setWord(singleWord.getWord() + line.toLowerCase());
                        isMean = false;
                    }
                }
                index = -1;
            }
            singleWord.setMeans(means);
            singleWords.add(singleWord);
            return singleWords;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SingleWord> sentenceFormat(String filePath) {
        if (filePath == null || filePath == "") {
            return null;
        }
        filePath = FileUtil.getPathStr(filePath);
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "utf-8");
            BufferedReader read = new BufferedReader(isr);
            String line = "";
            String temp = "";
            SingleWord singleWord = new SingleWord();
            List<SingleWord> singleWords = new ArrayList<SingleWord>();
            List<SingleWord.Mean> means = new ArrayList<SingleWord.Mean>();
            SingleWord.Mean singleMean = singleWord.new Mean();
            Integer index = -1;
            Boolean isMean = false;
            Set<String> phraseSingle = new HashSet<String>();
            while ((line = read.readLine()) != null) {
                System.out.println(line);
                if (StringUtil.isNotBlank(line)) {
                    temp = StringUtil.removeAllSpace(line);
                    if (phraseSingle.contains(temp)) {
                        continue;
                    }
                    phraseSingle.add(temp);
                    line = StringUtil.removeIndexSpace(line);
                    char[] strArr = line.toCharArray();
                    for (int i = 0, size = strArr.length; i < size; i++) {
                        if (StringUtil.isNum(String.valueOf(strArr[i]))) {
                            continue;
                        }
                        if (StringUtil.isSpecialChar(String.valueOf(strArr[i]))) {
                            continue;
                        }
                        index = i;
                        break;
                    }
                    if (index >= 0) {
                        isMean = true;
                        if (index == 0) {
                            Boolean isSpaceLast = StringUtil.isBlank(singleMean.getMean());
                            singleMean.setMean(singleMean.getMean() + line.trim());
                            if (isSpaceLast) {
                                means.add(singleMean);
                            }
                        } else {
                            singleWord.setMeans(means);
                            singleWords.add(singleWord);
                            singleWord = new SingleWord();
                            if (isMean) {
                                singleMean = singleWord.new Mean();
                            }
                            singleWord.setWord(singleWord.getWord() + line.substring(0, index).trim().toLowerCase());
                            singleMean.setMean(singleMean.getMean() + line.substring(index).trim());
                            means = new ArrayList<SingleWord.Mean>();
                            means.add(singleMean);

                        }
                    } else {
                        if (isMean) {
                            singleWord.setMeans(means);
                            singleWords.add(singleWord);
                            singleWord = new SingleWord();
                            singleMean = singleWord.new Mean();
                            means = new ArrayList<SingleWord.Mean>();
                        }
                        singleWord.setWord(singleWord.getWord() + line.toLowerCase());
                        isMean = false;
                    }
                }
                index = -1;
            }
            singleWord.setMeans(means);
            singleWords.add(singleWord);
            return singleWords;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
