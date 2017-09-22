import java.util.ArrayList;
import java.util.List;

/**
 * N皇后求解类
 *
 * @author LZM
 */
public class NQueens {
    /**
     * 皇后的数量（棋盘宽度）
     */
    private int queenCount;

    /**
     * 模拟棋盘，存储皇后在棋盘上的摆放情况
     * <p>
     * true表示该位为皇后，false表示该位为空
     */
    private boolean[][] chessBoard;

    /**
     * 解的个数
     */
    private int solutionCount;

    /**
     * 存储所有解的list
     * <p>
     * list元素为int数组，数组的索引代表棋盘行号，数组的值代表该行皇后所在的列号
     */
    private List<int[]> solutionList = new ArrayList<>();

    /**
     * 构造N皇后求解对象
     *
     * @param queenCount 皇后的数量（即棋盘宽度）
     */
    public NQueens(int queenCount) {
        this.queenCount = queenCount;
        chessBoard = new boolean[queenCount][queenCount];
    }

    /**
     * 解决当前的N皇后问题，并返回存有所有解的list
     * list元素为int数组，数组的索引代表棋盘行号，数组的值代表该行皇后所在的列号
     *
     * @param doPrint 是否打印出皇后在棋盘上的分布情况
     * @return 存有所有解的list
     */
    public List<int[]> solve(boolean doPrint) {
        placeQueen(0, doPrint);
        return solutionList;
    }

    /**
     * 从指定行开始，逐行递归放置皇后
     *
     * @param rowNum  行号，尝试在该行中放置皇后
     * @param doPrint 是否打印出皇后在棋盘上的分布情况
     */
    private void placeQueen(int rowNum, boolean doPrint) {
        // 如果已到达最后一行，则记录该解并结束该分支
        if (rowNum == queenCount) {
            solutionList.add(getCurSolutionInOneDimArrForm());
            solutionCount++;
            if (doPrint) {
                printCurSolution();
            }
            return;
        }

        // 遍历当前行的全部列，尝试在每一列上放置皇后
        for (int tmpColNum = 0; tmpColNum < queenCount; tmpColNum++) {
            // 如果该列可以放置皇后，则继续尝试在下一行放置皇后
            if (isPlaceable(rowNum, tmpColNum)) {
                chessBoard[rowNum][tmpColNum] = true; // 放置皇后
                placeQueen(rowNum + 1, doPrint); // 尝试下一行

                // 该列后续分支已完成或无解，则清除当前皇后，继续遍历本行剩余列，寻找下一个可以放置皇后的列
                chessBoard[rowNum][tmpColNum] = false;
            }
        }
    }

    /**
     * 判断在棋盘的指定位置上是否可以放置皇后
     * <p>
     * 判断该行之前的所有行中，是否存在同列、同斜线的皇后
     *
     * @param rowNum 行号
     * @param colNum 列号
     * @return 满足同列、同斜线上均无皇后时返回true，否则返回false
     */
    private boolean isPlaceable(int rowNum, int colNum) {
        // 查找同列是否存在皇后
        for (int tmpRowNum = 0; tmpRowNum < rowNum; tmpRowNum++) {
            if (chessBoard[tmpRowNum][colNum]) {
                return false;
            }
        }

        // 遍历之前的每一行
        for (int tmpRowNum = rowNum - 1, tmpColNumL = colNum - 1, tmpColNumR = colNum + 1;
             tmpRowNum >= 0; tmpRowNum--, tmpColNumL--, tmpColNumR++) {
            // 同列是否存在皇后
            if (chessBoard[tmpRowNum][colNum]) {
                return false;
            }

            // 左侧斜线上是否存在皇后
            if (tmpColNumL >= 0 && chessBoard[tmpRowNum][tmpColNumL]) {
                return false;
            }

            // 右侧斜线上是否存在皇后
            if (tmpColNumR < queenCount && chessBoard[tmpRowNum][tmpColNumR]) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取表示当前解的一维数组
     * <p>
     * 数组的索引代表棋盘行号，数组的值代表该行皇后所在的列号
     *
     * @return 表示当前解的一维数组
     */
    private int[] getCurSolutionInOneDimArrForm() {
        int[] solutionArr = new int[queenCount];

        for (int rowNum = 0; rowNum < queenCount; rowNum++) {
            for (int colNum = 0; colNum < queenCount; colNum++) {
                if (chessBoard[rowNum][colNum]) {
                    solutionArr[rowNum] = colNum;
                }
            }
        }

        return solutionArr;
    }

    /**
     * 打印当前的解
     */
    private void printCurSolution() {
        StringBuilder sb = new StringBuilder();

        sb.append("Solution ").append(solutionCount).append(":").append(System.lineSeparator());
        for (boolean[] row : chessBoard) {
            for (boolean isQueen : row) {
                sb.append(isQueen ? "[Q]" : "[ ]");
            }
            sb.append(System.lineSeparator());
        }

        System.out.println(sb);
    }
}
