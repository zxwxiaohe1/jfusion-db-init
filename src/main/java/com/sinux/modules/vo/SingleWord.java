package com.sinux.modules.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyong on 2019/4/26.
 */
public class SingleWord {

    private String word = "";
    private String mean = "";
    private List<Mean> means = new ArrayList<Mean>();

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Mean> getMeans() {
        return means;
    }

    public void setMeans(List<Mean> means) {
        this.means = means;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public class Mean {
        private String wordType = "";
        private String mean = "";

        public String getWordType() {
            return wordType;
        }

        public void setWordType(String wordType) {
            this.wordType = wordType;
        }

        public String getMean() {
            return mean;
        }

        public void setMean(String mean) {
            this.mean = mean;
        }
    }
}
