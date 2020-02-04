package com.sinux.core.support.ennums;

/**
 * Created by heyong on 2019/4/26.
 */
public enum WordType {
    /**
     * 名词
     */
    n(){
        @Override
        public String type() {
            return "名词";
        }
    },
    /**
     * 动词
     */
    v(){
        @Override
        public String type() {
            return "动词";
        }
    },
    /**
     * 形容词
     */
    adj(){
        @Override
        public String type() {
            return "形容词";
        }
    },
    /**
     * 副词
     */
    adv(){
        @Override
        public String type() {
            return "副词";
        }
    },
    /**
     * 不及物动词
     */
    vi(){
        @Override
        public String type() {
            return "不及物动词";
        }
    },
    /**
     * 副词
     */
    vt(){
        @Override
        public String type() {
            return "及物动词";
        }
    },
    /**
     * 副词
     */
    prep(){
        @Override
        public String type() {
            return "介词";
        }
    };
    /**
     * @Title: driver Type
     * @Description: 获得驱动
     * @return String
     * */
    public abstract String type();
    /**
     * @Title: getDriverType
     * @Description: 获得驱动
     * @return String
     * */
    public static String type(String type) {
        if(type == null) {
            return "";
        }
        type = type.toLowerCase();
        for(WordType v : values()) {
            if(type.trim().equals(v.name().trim()))  {
                return v.type();
            }
        }
        return "";
    }
}
