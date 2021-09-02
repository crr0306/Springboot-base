package com.spring.base.utils.aes;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;

public class SimpleShiftUtils {
    private static Logger logger = Logger.getLogger(SimpleShiftUtils.class);
    private static int maxLength = 13;
    private static String randomString = "ppoiuytredsxcvbnmqlkajhfwzg";

    public SimpleShiftUtils() {
    }

    public static String encode(String number) {
        if (!NumberUtils.isDigits(number)) {
            logger.error("只能对数字加密:" + number);
            throw new IllegalArgumentException("只能对数字加密:" + number);
        } else {
            StringBuffer sb = new StringBuffer(number);
            sb.reverse();
            boolean head = true;
            int tail = number.charAt(0);
            if (tail % 2 == 0) {
                head = false;
            }

            StringBuffer temp = new StringBuffer();

            try {
                char[] var5 = sb.toString().toCharArray();
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    char bt = var5[var7];
                    int index;
                    if (head) {
                        index = Integer.parseInt("" + bt) + 1;
                        temp.append(randomString.charAt(index));
                    } else {
                        index = Integer.parseInt("" + bt) + 1;
                        temp.append(randomString.charAt(index + 10));
                    }
                }
            } catch (Exception var10) {
                logger.error("数字加密错误:", var10);
            }

            return temp.length() < maxLength ? align(temp) : temp.toString();
        }
    }

    public static String decode(String number) {
        if (number != null && Pattern.matches("^\\w+$", number) && !number.equals("undefined")) {
            int tailStart = number.indexOf("j");
            if (tailStart != -1) {
                number = number.substring(0, tailStart);
            }

            StringBuffer result = new StringBuffer();
            char[] var3 = number.toString().toCharArray();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                char bt = var3[var5];
                int entity = randomString.indexOf(bt, 1);
                if (entity > 10) {
                    entity -= 11;
                } else {
                    --entity;
                }

                result.append(entity);
            }

            if (!NumberUtils.isDigits(result.toString())) {
                throw new IllegalArgumentException("解密参数错误:" + number);
            } else {
                return result.reverse().toString();
            }
        } else {
            throw new IllegalArgumentException("解密参数错误:" + number);
        }
    }

    private static String align(StringBuffer number) {
        for(int i = 21; i < 27 && number.length() < maxLength; ++i) {
            number.append(randomString.charAt(i));
            if (i == 26) {
                i = 0;
            }
        }

        return number.toString();
    }

    public static void main(String[] args) {
        System.out.println(decode("sseojhfwzgpoi"));
    }
}

