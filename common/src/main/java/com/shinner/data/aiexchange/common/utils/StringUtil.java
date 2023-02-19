package com.shinner.data.aiexchange.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class StringUtil {

    public static boolean isEmpty(String str) {
        return Objects.isNull(str) || str.length() == 0;
    }

    public static String leftPad(String str, int length, char toPad) {
        return StringUtils.leftPad(str, length, toPad);
    }

    public static String rightPad(String str, int length, char toPad) {
        return StringUtils.rightPad(str, length, toPad);
    }

    public static boolean isContainChinese(String str) {

        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Pattern p = Pattern.compile("[\u4E00-\u9FA5|！，。（）《》“”？：；【】]");
        Matcher m = p.matcher(str);
        return m.find();
    }


    public static String convertToneNumber2ToneMark(String pinyinStr) {
        String lowerCasePinyinStr = pinyinStr.replaceAll("ü", "v").toLowerCase();
        if (lowerCasePinyinStr.matches("[a-z]*[1-5]?")) {
            char unmarkedVowel = '$';
            int indexOfUnmarkedVowel = -1;
            if (!lowerCasePinyinStr.matches("[a-z]*[1-5]")) {
                return lowerCasePinyinStr.replaceAll("v", "ü");
            } else {
                int tuneNumber = Character.getNumericValue(lowerCasePinyinStr.charAt(lowerCasePinyinStr.length() - 1));
                int indexOfA = lowerCasePinyinStr.indexOf(97);
                int indexOfE = lowerCasePinyinStr.indexOf(101);
                int ouIndex = lowerCasePinyinStr.indexOf("ou");
                int i;
                if (-1 != indexOfA) {
                    indexOfUnmarkedVowel = indexOfA;
                    unmarkedVowel = 'a';
                } else if (-1 != indexOfE) {
                    indexOfUnmarkedVowel = indexOfE;
                    unmarkedVowel = 'e';
                } else if (-1 != ouIndex) {
                    indexOfUnmarkedVowel = ouIndex;
                    unmarkedVowel = "ou".charAt(0);
                } else {
                    for(i = lowerCasePinyinStr.length() - 1; i >= 0; --i) {
                        if (String.valueOf(lowerCasePinyinStr.charAt(i)).matches("[aeiouv]")) {
                            indexOfUnmarkedVowel = i;
                            unmarkedVowel = lowerCasePinyinStr.charAt(i);
                            break;
                        }
                    }
                }

                if ('$' != unmarkedVowel) {
                    i = "aeiouv".indexOf(unmarkedVowel);
                    int columnIndex = tuneNumber - 1;
                    int vowelLocation = i * 5 + columnIndex;
                    char markedVowel = "āáăàaēéĕèeīíĭìiōóŏòoūúŭùuǖǘǚǜü".charAt(vowelLocation);
                    return lowerCasePinyinStr.substring(0, indexOfUnmarkedVowel).replaceAll("v", "ü") + markedVowel + lowerCasePinyinStr.substring(indexOfUnmarkedVowel + 1, lowerCasePinyinStr.length() - 1).replaceAll("v", "ü");
                } else {
                    return lowerCasePinyinStr;
                }
            }
        } else {
            return lowerCasePinyinStr;
        }
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String abbreviate(String str, int maxWidth, String suffix) {
        if (str == null) {
            return null;
        }

        if (suffix == null) {
            suffix = "...";
        }

        if (maxWidth < suffix.length() + 1) {
            throw new IllegalArgumentException("Minimum abbreviation width is suffix.length + 1");
        }

        if (str.length() <= maxWidth) {
            return str;
        }

        return str.substring(0, maxWidth - suffix.length()).trim() + suffix;
    }

    public static String keyGenerator(Object... objects) {
        return StringUtils.joinWith("-", objects);
    }

    public static String getUtf8String(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return new String(data, StandardCharsets.UTF_8);
    }


    public static String valueOf(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        return String.valueOf(obj);
    }

    public static boolean isMatch(String code, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes(StandardCharsets.UTF_8);
        int bit;
        for (byte b : bs) {
            bit = (b & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = b & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }
}
