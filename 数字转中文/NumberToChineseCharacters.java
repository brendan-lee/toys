import java.math.BigDecimal;
import java.util.Scanner;

/**
 * 数字转中文
 *
 * @author LZM
 */
public class NumberToChineseCharacters {
    private static final String[] NUMBER_NAME_ARR = {"零", "一", "二", "三", "四", "五", "六", "七", "八",
            "九"};
    private static final String[] DIGIT_NAME_ARR = {"", "十", "百", "千"};
    private static final String[] BASE_DIGIT_NAME_ARR = {"", "万", "亿", "兆", "京", "垓", "秭", "穰",
            "沟", "涧", "正", "载", "极", "恒河沙", "阿僧祇", "那由他", "不可思议", "无量大数"};
    private static final String POINT = "点";
    private static final String NEGATIVE = "负";

    /**
     * 输入数字，获取其对应汉字
     * <p>
     * 支持正负数及小数，整数部分最高支持至10<sup>72</sup>位（千无量大数）
     *
     * @param numberStr 字符串形式的数字
     * @return 数字对应的中文，数字不合法或超长时返回null
     */
    private static String translate(String numberStr) {
        // 验证数字合法性
        try {
            BigDecimal bd = new BigDecimal(numberStr);
            numberStr = bd.toString(); // 规范化numStr
        } catch (NumberFormatException e) {
            return null;
        }

        // 分成整数和小数部分
        String[] numStrArr = numberStr.split("\\.");
        boolean hasDecimal = numStrArr.length == 2; // 是否含小数

        StringBuilder sb = new StringBuilder();

        // 整数部分
        String integerStr = numStrArr[0];

        // 是否为负数
        if (integerStr.startsWith("-")) {
            sb.append(NEGATIVE);
            integerStr = integerStr.substring(1, integerStr.length());
        }

        // 整数部分拆散为数组
        String[] absIntegerArr = integerStr.split("");
        int integerLen = absIntegerArr.length; // 整数部分长度

        // 验证数字是否超长
        if (integerLen > BASE_DIGIT_NAME_ARR.length * 4) {
            return null;
        }

        int baseDigitCount = (int) Math.ceil(integerLen / 4D); // 基本位的段数
        int lastBaseDigitCount = integerLen % 4; // 最后一个基本位所包含的位数
        boolean isLastBaseDigit = true; // 当前是否处于最后一个基本位

        int finCount = 0; // 已遍历的数字位数
        boolean isFin = false; // 是否已完成全部数字的遍历

        // 遍历基本位
        for (int baseDigitIndex = baseDigitCount - 1; baseDigitIndex >= 0; baseDigitIndex--) {
            String baseDigitName = BASE_DIGIT_NAME_ARR[baseDigitIndex]; // 当前基本位的名称

            // 遍历每个基本位的每一位
            boolean metZero = false;
            int nonZeroCount = 0; // 当前基本位中非零数字的数量
            for (int digitIndex = (isLastBaseDigit && lastBaseDigitCount != 0 ?
                    lastBaseDigitCount - 1 : 3); digitIndex >= 0; digitIndex--) {
                int curNumber = Integer.parseInt(absIntegerArr[finCount]); // 当前位上的数字
                String curNumberName = NUMBER_NAME_ARR[curNumber]; // 当前数字对应的中文
                String curDigitName = DIGIT_NAME_ARR[digitIndex]; // 当前位的不完整名称

                // 当前数字为0，忽略数位
                if (curNumber == 0) {
                    metZero = true;
                    curDigitName = "";
                    curNumberName = "";
                } else {
                    nonZeroCount++;
                }

                // 当前基本位中的前面一位为0，且当前数字非0，读作零
                if (curNumber != 0 && metZero) {
                    curNumberName = NUMBER_NAME_ARR[0] + curNumberName;
                    metZero = false;
                }

                // 最高基本位含2位，当前为十位，且当前数字为1时，忽略数字
                if (lastBaseDigitCount == 2 && digitIndex == 1 && curNumber == 1) {
                    curNumberName = "";
                }

                // append 数字+数位名称
                sb.append(curNumberName).append(curDigitName);

                // 全部数字遍历完成时退出
                if (++finCount == integerLen) {
                    isFin = true;
                    break;
                }
            }
            // 如果当前基本位中所有数字均为0则忽略该基本位，否则append 基本位名称
            if (nonZeroCount > 0) {
                sb.append(baseDigitName);
            }

            isLastBaseDigit = false;

            if (isFin) {
                break;
            }
        }

        // 小数部分
        if (hasDecimal) {
            sb.append(POINT); // 小数点

            String[] decimalArr = numStrArr[1].split(""); // 小数部分拆散成数组

            // 遍历每一个小数位
            for (String aDecimalArr : decimalArr) {
                int number = Integer.parseInt(aDecimalArr);
                String numberName = NUMBER_NAME_ARR[number];

                // append 数字名称
                sb.append(numberName);
            }
        }

        return sb.toString();
    }

    /**
     * 输入数字，获取其对应汉字
     * <p>
     * 支持正负数，整数部分最高支持至10<sup>72</sup>位（千无量大数）
     *
     * @param number 数字
     * @return 数字对应的中文，数字不合法或超长时返回null
     */
    public static String translate(long number) {
        return translate(String.valueOf(number));
    }

    /**
     * 输入数字，获取其对应汉字
     * <p>
     * 支持正负数，整数部分最高支持至10<sup>72</sup>位（千无量大数）
     *
     * @param number 数字
     * @return 数字对应的中文，数字不合法或超长时返回null
     */
    public static String translate(double number) {
        return translate(String.valueOf(number));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("input a number or q to quit:");
            String in = sc.next();

            switch (in) {
                case "q": {
                    sc.close();
                    System.exit(0);
                }
                default: {
                    String str = translate(in);
                    if (str != null) {
                        System.out.println(str);
                    } else {
                        System.out.println("wrong number");
                    }
                }
            }
        }
    }
}