/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.core.util;
import java.security.MessageDigest;

/**
 * 1.MD5加密字符串（32位大写）
 * 2.MD5加密字符串（32位小写）
 * <p>
 * MD5在线加密：https://md5jiami.51240.com/
 * 3.将二进制字节数组转换为十六进制字符串
 * 4.Unicode中文编码转换成字符串
 */

/**
 * 每六位描述一个字节
 * @author zhouzhian
 */
public class UnicodeUtil {

    /**
     * 字符串编码成Unicode编码
     */
    public static String encode(String src) throws Exception {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < src.length(); i++) {
            c = src.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
                str.append("\\u" + strHex);
            else
                str.append("\\u00" + strHex); // 低位在前面补00
        }
        return str.toString();
    }

    /**
     * Unicode解码成字符串
     * @param src
     * @return
     */
    public static String decode(String src) {
        int t =  src.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = src.substring(i * 6, (i + 1) * 6); // 每6位描述一个字节
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }
}