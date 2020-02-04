package com.sinux.core.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @Discrption: 推送权限码
 * @Author: Created by heyong
 * @Date: 2019/04/09
 * @Copyright&Copy: <a href="http://www.sinux.com.cn">JFusion</a> All rights reserved
 */
public class FileUtil {

    public final static String FILE_CONTENT_END_SIGN = "=end=";

    public static void IOAppend(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void wrAppend(String fileName, String content) {
        FileWriter writer = null;
        try {
            /**打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件*/
            writer = new FileWriter(fileName, true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content  追加的内容
     */
    public static void RAAppend(String fileName, String content) {
        RandomAccessFile randomFile = null;
        try {
            /**打开一个随机访问文件流，按读写方式*/
            randomFile = new RandomAccessFile(fileName, "rw");
            /**文件长度，字节数*/
            long fileLength = randomFile.length();
            /**将写文件指针移到文件尾*/
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param path String类型
     * @return String
     * @Title: addEndSux
     * @Description: 系统文件分隔符替换
     */
    public static String getPathStr(String path) {
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
            if (path.contains("\\")) {
                path = path.replace("\\", File.separator);
            }
        } else if (Pattern.matches("Windows.*", osName)) {
            if (path.contains("/")) {
                path = path.replace("/", File.separator);
            }
        }
        return path;
    }

    /**
     * 清空文件内容
     *
     * @param filePath String类型
     * @Title: delete
     * @return: void
     */
    public static void clear(String filePath) {
        if (StringUtils.isEmpty(filePath) || filePath == null) {
            return;
        }
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            if (file != null && file.exists()) {
                fileWriter = new FileWriter(file);
                if (fileWriter != null) {
                    fileWriter.write("");
                    fileWriter.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 从输入流中的得到字节
     * @param is InputStream类型
     * @return SecretKey
     */
    public static byte[] getStreamBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        is.close();
        baos.close();
        return bytes;
    }
}

