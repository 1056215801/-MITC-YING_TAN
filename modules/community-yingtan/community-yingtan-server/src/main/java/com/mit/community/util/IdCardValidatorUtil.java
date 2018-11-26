package com.mit.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 身份证合法性校验工具类
 * <p>
 * 身份证前6位【ABCDEF】为行政区划数字代码（简称数字码）说明（参考《GB/T 2260-2007 中华人民共和国行政区划代码》）：
 * 该数字码的编制原则和结构分析，它采用三层六位层次码结构，按层次分别表示我国各省（自治区，直辖市，特别行政区）、
 * 市（地区，自治州，盟）、县（自治县、县级市、旗、自治旗、市辖区、林区、特区）。
 * 数字码码位结构从左至右的含义是：
 * 第一层为AB两位代码表示省、自治区、直辖市、特别行政区；
 * 第二层为CD两位代码表示市、地区、自治州、盟、直辖市所辖市辖区、县汇总码、省（自治区）直辖县级行政区划汇总码，其中：
 * ——01~20、51~70表示市，01、02还用于表示直辖市所辖市辖区、县汇总码；
 * ——21~50表示地区、自治州、盟；
 * ——90表示省（自治区）直辖县级行政区划汇总码。
 * 第三层为EF两位表示县、自治县、县级市、旗、自治旗、市辖区、林区、特区，其中：
 * ——01~20表示市辖区、地区（自治州、盟）辖县级市、市辖特区以及省（自治区）直辖县级行政区划中的县级市，01通常表示辖区汇总码；
 * ——21~80表示县、自治县、旗、自治旗、林区、地区辖特区；
 * ——81~99表示省（自治区）辖县级市。
 * <p>
 * --15位身份证号码：第7、8位为出生年份(两位数)，
 * 第9、10位为出生月份，
 * 第11、12位代表出生日期，
 * 第15位代表性别，奇数为男，偶数为女。
 * --18位身份证号码
 * 第7、8、9、10位为出生年份(四位数)，
 * 第11、第12位为出生月份，
 * 第13、14位代表出生日期，
 * 第17位代表性别，
 * 奇数为男，偶数为女。
 * </p>
 *
 * @author Mr.Deng
 * @date 2018/11/20 16:08
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Component
public class IdCardValidatorUtil {
    /**
     * 城市代码
     */
    private String[] cityCode = {"11", "12", "13", "14", "15", "21", "22",
            "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
            "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
            "64", "65", "71", "81", "82", "91"};
    /**
     * 每位加权因子
     */
    private int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 验证所有的身份证的合法性
     *
     * @param idCard 身份证号
     * @return boolean
     * @author Mr.Deng
     * @date 17:01 2018/11/20
     */
    boolean isValidatedAllIdCard(String idCard) {
        int len = 15;
        if (idCard.length() == len) {
            idCard = this.convertIdCarBy15bit(idCard);
        }
        return this.isValidate18Idcard(idCard);
    }

    /**
     * 判断18位身份证的合法性
     *
     * @param idCard 身份证
     * @return Boolean
     * @author Mr.Deng
     * @date 16:57 2018/11/20
     */
    private boolean isValidate18Idcard(String idCard) {
        int len = 18;
        // 非18位为假
        if (idCard.length() != len) {
            return false;
        }
        // 获取前17位
        String idCard17 = idCard.substring(0, 17);
        // 获取第18位
        String idCard18Code = idCard.substring(17, 18);
        char[] c;
        String checkCode;
        // 是否都为数字
        if (isDigital(idCard17)) {
            c = idCard17.toCharArray();
        } else {
            return false;
        }
        int[] bit;
        int sum17;
        bit = converCharToInt(c);
        sum17 = getPowerSum(bit);
        // 将和值与11取模得到余数进行校验码判断
        checkCode = getCheckCodeBySum(sum17);
        if (null == checkCode) {
            return false;
        }
        // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
        return idCard18Code.equalsIgnoreCase(checkCode);
    }

    /**
     * 验证15位身份证的合法性,该方法验证不准确，最好是将15转为18位后再判断，该类中已提供
     *
     * @param idCard 身份证号
     * @return Boolean
     * @author Mr.Deng
     * @date 16:48 2018/11/20
     */
    public boolean isValidate15Idcard(String idCard) {
        int len = 15;
        int mon = 12;
        // 非15位为假
        if (idCard.length() != len) {
            return false;
        }
        // 是否全都为数字
        if (isDigital(idCard)) {
            String provinceId = idCard.substring(0, 2);
            String birthday = idCard.substring(6, 12);
            int year = Integer.parseInt(idCard.substring(6, 8));
            int month = Integer.parseInt(idCard.substring(8, 10));
            int day = Integer.parseInt(idCard.substring(10, 12));
            // 判断是否为合法的省份
            boolean flag = false;
            for (String id : cityCode) {
                if (id.equals(provinceId)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
            // 该身份证生出日期在当前日期之后时为假
            Date birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (birthDate == null || new Date().before(birthDate)) {
                return false;
            }
            // 判断是否为合法的年份
            GregorianCalendar curDay = new GregorianCalendar();
            int curYear = curDay.get(Calendar.YEAR);
            int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
            // 判断该年份的两位表示法，小于50的和大于当前年份的，为假
            int yea = 50;
            if ((year < yea && year > year2bit)) {
                return false;
            }
            // 判断是否为合法的月份
            if (month < 1 || month > mon) {
                return false;
            }
            // 判断是否为合法的日期
            boolean mFlag = false;
            // 将该身份证的出生日期赋于对象curDay
            curDay.setTime(birthDate);
            switch (month) {
                case 12:
                    mFlag = (day >= 1 && day <= 31);
                    break;
                // 公历的2月非闰年有28天,闰年的2月是29天。
                case 2:
                    if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
                        mFlag = (day >= 1 && day <= 29);
                    } else {
                        mFlag = (day >= 1 && day <= 28);
                    }
                    break;
                case 11:
                    mFlag = (day >= 1 && day <= 30);
                    break;
                default:
            }
            return mFlag;
        } else {
            return false;
        }
    }

    /**
     * 将15位的身份证转成18位身份证
     *
     * @param idCard 身份证号
     * @return 身份证号
     * @author Mr.Deng
     * @date 16:42 2018/11/20
     */
    String convertIdCarBy15bit(String idCard) {
        int len = 15;
        String idCard17;
        // 非15位身份证
        if (idCard.length() != len) {
            return null;
        }
        if (isDigital(idCard)) {
            // 获取出生年月日
            String birthday = idCard.substring(6, 12);
            Date birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cDay = Calendar.getInstance();
            if (birthDate != null) {
                cDay.setTime(birthDate);
            }
            String year = String.valueOf(cDay.get(Calendar.YEAR));
            idCard17 = idCard.substring(0, 6) + year + idCard.substring(8);
            char[] c = idCard17.toCharArray();
            String checkCode;
            int[] bit;
            // 将字符数组转为整型数组
            bit = converCharToInt(c);
            int sum17;
            sum17 = getPowerSum(bit);
            // 获取和值与11取模得到余数进行校验码
            checkCode = getCheckCodeBySum(sum17);
            // 获取不到校验位
            if (null == checkCode) {
                return null;
            }
            // 将前17位与第18位校验码拼接
            idCard17 += checkCode;
        } else {
            // 身份证包含数字
            return null;
        }
        return idCard17;
    }

    /**
     * 15位和18位身份证号码的基本数字和位数验校
     * idcard == null || "".equals(idcard)
     *
     * @param idCard 身份证号
     * @return Boolean
     * @author Mr.Deng
     * @date 16:35 2018/11/20
     */
    public boolean isIdCard(String idCard) {
        String regex = "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)";
        if (StringUtils.isBlank(idCard)) {
            return false;
        } else {
            return Pattern.matches(regex, idCard);
        }
    }

    /**
     * 15位身份证号码的基本数字和位数验校
     *
     * @param idCard 身份证号
     * @return boolean
     * @author Mr.Deng
     * @date 16:31 2018/11/20
     */
    public boolean is15Idcard(String idCard) {
        String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        if (StringUtils.isBlank(idCard)) {
            return false;
        } else {
            return Pattern.matches(regex, idCard);
        }
    }

    /**
     * 18位身份证号码的基本数字和位数验校
     *
     * @param idCard 身份证号
     * @return boolean
     * @author Mr.Deng
     * @date 16:29 2018/11/20
     */
    public boolean is18IdCard(String idCard) {
        String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
        return Pattern.matches(regex, idCard);

    }

    /**
     * 数字校验
     *
     * @param str 字符串
     * @return boolean
     * @author Mr.Deng
     * @date 16:26 2018/11/20
     */
    private boolean isDigital(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            return str.matches("^[0-9]*$");
        }
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param bit int数组
     * @return int
     * @author Mr.Deng
     * @date 16:21 2018/11/20
     */
    private int getPowerSum(int[] bit) {
        int sum = 0;
        if (power.length != bit.length) {
            return sum;
        }
        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }
        return sum;
    }

    /**
     * 将和值与11取模得到余数进行校验码判断
     *
     * @param sum17 前17位数字
     * @return 校验位
     * @author Mr.Deng
     * @date 16:15 2018/11/20
     */
    private String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
             default:
        }
        return checkCode;
    }

    /**
     * 将字符数组转为整型数组
     *
     * @param c 字节
     * @return int[]
     * @throws NumberFormatException 数字格式错误
     * @author Mr.Deng
     * @date 16:14 2018/11/20
     */
    private int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }

    public static void main(String[] args) {
        //15位
//        String idCard15 = "123456789123467899";
        //18位
        String idCard18 = "360124199812180318";
        IdCardValidatorUtil i = new IdCardValidatorUtil();
        System.out.println(i.isValidatedAllIdCard(idCard18));
    }
}
