package com.sinux.core.support.export;

/**
 * 导出Word
 * @author heyong
 * @version 2018-10-25
 * @copyright&copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class HtmlDataset {

    public static String getTableHeader(){
        return "<table>";
    }
    public static String getTableData(String countent){
        String linedata = "<tr>\n" +
                "  <td width=553 valign=top style='width:414.8pt;padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                "  <p class=MsoNormal>"+countent+"</p>\n" +
                "  </td>\n" +
                " </tr>";
          return linedata;
    }
    public static String setColor(String str, String color){
        return "<span lang=EN-US style='font-size:9.0pt;font-family:\"微软雅黑\",sans-serif;color:"+color+"'>"+str+"</span>";
    }
    public static String getTableEnd(){
        return "</table>";
    }
}
