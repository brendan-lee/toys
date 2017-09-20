import java.math.BigDecimal;
import java.util.Scanner;

/**
 * ����ת����
 *
 * @author LZM
 */
public class NumberToChineseCharacters {
    private static final String[] NUMBER_NAME_ARR = {"��", "һ", "��", "��", "��", "��", "��", "��", "��",
            "��"};
    private static final String[] DIGIT_NAME_ARR = {"", "ʮ", "��", "ǧ"};
    private static final String[] BASE_DIGIT_NAME_ARR = {"", "��", "��", "��", "��", "��", "��", "�",
            "��", "��", "��", "��", "��", "���ɳ", "��ɮ�o", "������", "����˼��", "��������"};
    private static final String POINT = "��";
    private static final String NEGATIVE = "��";

    /**
     * �������֣���ȡ���Ӧ����
     * <p>
     * ֧����������С���������������֧����10<sup>72</sup>λ��ǧ����������
     *
     * @param numberStr �ַ�����ʽ������
     * @return ���ֶ�Ӧ�����ģ����ֲ��Ϸ��򳬳�ʱ����null
     */
    private static String translate(String numberStr) {
        // ��֤���ֺϷ���
        try {
            BigDecimal bd = new BigDecimal(numberStr);
            numberStr = bd.toString(); // �淶��numStr
        } catch (NumberFormatException e) {
            return null;
        }

        // �ֳ�������С������
        String[] numStrArr = numberStr.split("\\.");
        boolean hasDecimal = numStrArr.length == 2; // �Ƿ�С��

        StringBuilder sb = new StringBuilder();

        // ��������
        String integerStr = numStrArr[0];

        // �Ƿ�Ϊ����
        if (integerStr.startsWith("-")) {
            sb.append(NEGATIVE);
            integerStr = integerStr.substring(1, integerStr.length());
        }

        // �������ֲ�ɢΪ����
        String[] absIntegerArr = integerStr.split("");
        int integerLen = absIntegerArr.length; // �������ֳ���

        // ��֤�����Ƿ񳬳�
        if (integerLen > BASE_DIGIT_NAME_ARR.length * 4) {
            return null;
        }

        int baseDigitCount = (int) Math.ceil(integerLen / 4D); // ����λ�Ķ���
        int lastBaseDigitCount = integerLen % 4; // ���һ������λ��������λ��
        boolean isLastBaseDigit = true; // ��ǰ�Ƿ������һ������λ

        int finCount = 0; // �ѱ���������λ��
        boolean isFin = false; // �Ƿ������ȫ�����ֵı���

        // ��������λ
        for (int baseDigitIndex = baseDigitCount - 1; baseDigitIndex >= 0; baseDigitIndex--) {
            String baseDigitName = BASE_DIGIT_NAME_ARR[baseDigitIndex]; // ��ǰ����λ������

            // ����ÿ������λ��ÿһλ
            boolean metZero = false;
            int nonZeroCount = 0; // ��ǰ����λ�з������ֵ�����
            for (int digitIndex = (isLastBaseDigit && lastBaseDigitCount != 0 ?
                    lastBaseDigitCount - 1 : 3); digitIndex >= 0; digitIndex--) {
                int curNumber = Integer.parseInt(absIntegerArr[finCount]); // ��ǰλ�ϵ�����
                String curNumberName = NUMBER_NAME_ARR[curNumber]; // ��ǰ���ֶ�Ӧ������
                String curDigitName = DIGIT_NAME_ARR[digitIndex]; // ��ǰλ�Ĳ���������

                // ��ǰ����Ϊ0��������λ
                if (curNumber == 0) {
                    metZero = true;
                    curDigitName = "";
                    curNumberName = "";
                } else {
                    nonZeroCount++;
                }

                // ��ǰ����λ�е�ǰ��һλΪ0���ҵ�ǰ���ַ�0��������
                if (curNumber != 0 && metZero) {
                    curNumberName = NUMBER_NAME_ARR[0] + curNumberName;
                    metZero = false;
                }

                // ��߻���λ��2λ����ǰΪʮλ���ҵ�ǰ����Ϊ1ʱ����������
                if (lastBaseDigitCount == 2 && digitIndex == 1 && curNumber == 1) {
                    curNumberName = "";
                }

                // append ����+��λ����
                sb.append(curNumberName).append(curDigitName);

                // ȫ�����ֱ������ʱ�˳�
                if (++finCount == integerLen) {
                    isFin = true;
                    break;
                }
            }
            // �����ǰ����λ���������־�Ϊ0����Ըû���λ������append ����λ����
            if (nonZeroCount > 0) {
                sb.append(baseDigitName);
            }

            isLastBaseDigit = false;

            if (isFin) {
                break;
            }
        }

        // С������
        if (hasDecimal) {
            sb.append(POINT); // С����

            String[] decimalArr = numStrArr[1].split(""); // С�����ֲ�ɢ������

            // ����ÿһ��С��λ
            for (String aDecimalArr : decimalArr) {
                int number = Integer.parseInt(aDecimalArr);
                String numberName = NUMBER_NAME_ARR[number];

                // append ��������
                sb.append(numberName);
            }
        }

        return sb.toString();
    }

    /**
     * �������֣���ȡ���Ӧ����
     * <p>
     * ֧���������������������֧����10<sup>72</sup>λ��ǧ����������
     *
     * @param number ����
     * @return ���ֶ�Ӧ�����ģ����ֲ��Ϸ��򳬳�ʱ����null
     */
    public static String translate(long number) {
        return translate(String.valueOf(number));
    }

    /**
     * �������֣���ȡ���Ӧ����
     * <p>
     * ֧���������������������֧����10<sup>72</sup>λ��ǧ����������
     *
     * @param number ����
     * @return ���ֶ�Ӧ�����ģ����ֲ��Ϸ��򳬳�ʱ����null
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