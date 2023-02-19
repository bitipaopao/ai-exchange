package com.shinner.data.aiexchange.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

@SuppressWarnings("unused")
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String fileChapterSetCode(String fileName) throws Exception {
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName))) {
            int p = (bin.read() << 8) + bin.read();
            String code;
            switch (p) {
                case 0xefbb:
                    code = "UTF-8";
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                default:
                    code = "GBK";
            }
            return code;
        }
    }

    public static Boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static String getFileName(String fileName) {
        String tmp = fileName.replace("\\", "/");
        String[] splits = tmp.split("/");
        tmp = splits[splits.length - 1];
        splits = tmp.split("\\.");
        return splits[0];
    }

    public static String getFileNameWithExp(String fileName) {
        String tmp = fileName.replace("\\", "/");
        String[] splits = tmp.split("/");
        return splits[splits.length - 1];
    }

    public static String getDir(String fileName) {
        int index = fileName.lastIndexOf("/");
        return index > 0 ? fileName.substring(0, index) : fileName;
    }

    public static Boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return false;
        }
        return deleteDir(file);
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    public static String extName(String filename) {
        try {
            String result = filename;
            if (filename.contains("/")) {
                result = result.substring(result.lastIndexOf("/") + 1);
            }
            if (result.startsWith("\"") && result.endsWith("\"")) {
                result = result.substring(1, result.length()-1);
            }
            int index = result.lastIndexOf(".");
            if (index == -1) {
                return null;
            }
            return result.substring(index + 1);
        } catch (Exception e) {
            logger.error("filename=" + filename);
            throw e;
        }
    }

    synchronized public static boolean checkAndMkdir(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException();
        }
        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            return file.mkdirs();
        }
    }

    synchronized public static boolean checkAndMkParent(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException();
        }
        File file = new File(path);
        if (file.getParentFile().exists() && file.getParentFile().isDirectory()) {
            return true;
        }

        if (!file.getParentFile().exists()) {
            boolean needMkParent = checkAndMkParent(file.getParent());
            if (needMkParent) {
                return file.getParentFile().mkdirs();
            }
        }
        return false;
    }
}
