package com.sinux.core.support.ennums;

/**
 * Created by heyong on 2019/4/26.
 */
public enum WordExportType {
    /**
     * 单词
     */
    word(){
        @Override
        public String type() {
            return "单词";
        }
    },
    /**
     * 短语
     */
    phrase(){
        @Override
        public String type() {
            return "短语";
        }
    },
    /**
     *
     */
    sentence(){
        @Override
        public String type() {
            return "句子";
        }
    };
    /**
     * @Title: driver Type
     * @Description: 获得驱动
     * @return String
     * */
    public abstract String type();
}
