import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * N皇后入口类
 *
 * @author LZM
 */
public class Main {
    /**
     * 平台换行符
     */
    private static final String LINE_SEP = System.lineSeparator();

    /**
     * N皇后主入口
     *
     * @param args 初始参数
     */
    public static void main(String[] args) {
        // 用户输入
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入皇后的数量：");
        int queenCount = sc.nextInt();
        System.out.println("是否打印皇后在棋盘中的分布图（会拖慢运算）？[y/n]：");
        boolean doPrintChessBoard = "y".equals(sc.next());
        System.out.println("是否打印一维数组形式的解？[y/n]：");
        boolean doPrintOneDimArr = "y".equals(sc.next());
        System.out.println();
        sc.close();

        // 求解N皇后
        long startTimestamp = System.currentTimeMillis();
        List<int[]> solutionList = new NQueens(queenCount).solve(doPrintChessBoard);
        long endTimestamp = System.currentTimeMillis();

        // 打印
        if (doPrintOneDimArr) {
            StringBuilder sb = new StringBuilder();

            sb.append("一维数组形式的解：").append(LINE_SEP);
            for (int[] solution : solutionList) {
                sb.append(Arrays.toString(solution)).append(LINE_SEP);
            }

            System.out.println(sb + LINE_SEP);
        }
        System.out.println("运算耗时" + (endTimestamp - startTimestamp) + "ms");
        System.out.println(+queenCount + "皇后共有" + solutionList.size() + "个解");
    }
}
