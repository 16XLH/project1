/**
 * Created by admin on 2016/10/29.
 */
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class pj1 {
    public static void main(String[] args) throws FileNotFoundException {
        //将地图通过读取数据的方法打印出来得到初始化地图
        Scanner file1 = new Scanner(new File("tile.txt"));
        Scanner file2 = new Scanner(new File("animal.txt"));
        //用一维数组表示出数据中的数字代表的含义
        String[] map = {" 　 ", " 水 ", " 陷 ", " 家 ", " 陷 ", " 家 "};
        String[] a左 = {" 　 ", "1鼠 ", "2猫 ", "3狼 ", "4狗 ", "5豹 ", "6虎 ", "7狮 ", "8象 "};
        String[] a右 = {" 　 ", " 鼠1", " 猫2", " 狼3", " 狗4", " 豹5", " 虎6", " 狮7", " 象8"};
        String[] A1 = new String[9];
        String[] A2 = new String[9];
        //记录一个二维数组，用于地图的记录
        String[][] board = new String[7][9];
        for (int m = 0; m < 7; m++) {
            String String1 = file1.next();
            String String2 = file2.next();
            for (int i = 0; i < 9; i++) {
                char theChar1 = String1.charAt(i);
                int char1 = (int) theChar1 - 48;
                A1[i] = map[char1];
            }
            for (int j = 0; j < 9; j++) {
                char theChar2 = String2.charAt(j);
                int char2 = (int) theChar2 - 48;
                if (j < 5) {
                    A2[j] = a左[char2];
                }
                if (j >= 5) {
                    A2[j] = a右[char2];
                }
            }
            for (int j = 0; j < 9; j++) {
                if (!A1[j].equals(" 　 ")) {
                    board[m][j] = A1[j];
                } else {
                    if (!A2[j].substring(1, 2).equals("　")) {
                        board[m][j] = A2[j];
                    } else board[m][j] = " 　 ";
                }
            }
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        //记录一个10000步的三维数组，使其记录历史棋盘并有足够多的步数
        String[][][] boardHistory = new String[10000][7][9];
        int n = 0;
        int k = 0;
        boolean player = true;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    boardHistory[n][i][j] = board[i][j];
                }
            }//左方行棋的代码
            if (n%2 ==0) {
                System.out.print("左方行棋: ");
                String nextPosition = scanner.next();
                System.out.println(nextPosition);

                if (nextPosition.equals("undo")) {
                    if (n == 0) {
                        System.out.println("已经退回到开局，不能再悔棋了！");
                        n = n-1;
                    } else {
                        for (int i = 0; i < 7; i++) {
                            for (int j = 0; j < 9; j++) {
                                board[i][j] = boardHistory[n - 1][i][j];
                            }
                        }
                        n = n - 2;
                        k = k + 1;
                    }
                } else if (nextPosition.equals("redo")) {
                    if (k == 0) {
                        System.out.println("已回到最后记录，不能再取消悔棋了");
                        n = n-1;
                    } else {
                        for (int i = 0; i < 7; i++) {
                            for (int j = 0; j < 9; j++) {
                                board[i][j] = boardHistory[n + 1][i][j];
                            }
                        }
                        k = k - 1;
                    }
                } else if (nextPosition.equals("help")) {
                    System.out.println("指令介绍：");
                    System.out.println("1.移动指令");
                    System.out.println("    移动指令由两个部分组成。");
                    System.out.println("    第一个部分是数字1-8，根据战斗力分别对应属（1），猫（2），狼（3），狗（4），豹（5），虎（6），狮（7），象（8）");
                    System.out.println("    第二个部分是字母wasd中的一个，w对应上方向，a对应左方向，s对应下方向，d对应右方向");
                    System.out.println("    比如指令“1d”表示鼠向右走，4w表示狗向左走");
                    System.out.println("2.游戏指令");
                    System.out.println("    输入restart重新开始游戏");
                    System.out.println("    输入help查看帮助");
                    System.out.println("    输入undo悔棋");
                    System.out.println("    输入redo取消悔棋");
                    System.out.println("    输入exit退出游戏");
                    n = n-1;
                } else if (nextPosition.equals("exit")) {
                    break;
                } else if (nextPosition.equals("restart")) {
                    n = -1;
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            board[i][j] = boardHistory[0][i][j];

                        }
                    }
                } else if ((nextPosition.substring(0, 1).equals("1") || nextPosition.substring(0, 1).equals("2") || nextPosition.substring(0, 1).equals("3") ||
                        nextPosition.substring(0, 1).equals("4") || nextPosition.substring(0, 1).equals("5") || nextPosition.substring(0, 1).equals("6") ||
                        nextPosition.substring(0, 1).equals("7") || nextPosition.substring(0, 1).equals("8")) && (nextPosition.substring(1, 2).equals("a") ||
                        nextPosition.substring(1, 2).equals("w") || nextPosition.substring(1, 2).equals("s") || nextPosition.substring(1, 2).equals("d"))
                        && nextPosition.length() == 2) {
                    int A = Integer.parseInt(nextPosition.substring(0, 1));
                    stop:
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (board[i][j].substring(0, 1).equals(nextPosition.substring(0, 1))) {
                                if (nextPosition.substring(1, 2).equals("a")) {
                                    if (j == 0) {
                                        System.out.println("动物不能走出地图边界");
                                        n = n - 1;
                                        break stop;
                                    } else {
                                        if (!board[i][j - 1].substring(0, 1).equals(" ")) {
                                            System.out.println("己方动物不能进入友方单位所在的格子");
                                            n = n - 1;
                                            break stop;
                                        } else if (i == 3 && j == 1) {
                                            System.out.println("己方动物不能进入己方兽穴");
                                            n = n - 1;
                                            break stop;
                                        } else if ((i == 3 && j == 7) || (i == 2 && j == 8) || (i == 4 && j == 8)) {
                                            board[i][j - 1] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break stop;
                                        } //当棋子在水中或将要进入水中时的代码
                                        else if ((i == 1 || i == 2 || i == 4 || i == 5) && (j == 3 || j == 4 || j == 5 || j == 6)) {
                                            //棋子将要进入水中时
                                            if ((i == 1 || i == 2 || i == 4 || i == 5) && j == 6) {
                                                if (board[i][j].substring(0, 1).equals("2") || board[i][j].substring(0, 1).equals("3") || board[i][j].substring(0, 1).equals("4") ||
                                                        board[i][j].substring(0, 1).equals("5") || board[i][j].substring(0, 1).equals("8")) {
                                                    System.out.println("该子不能下水");
                                                    n = n - 1;
                                                    break stop;
                                                } else if (board[i][j].substring(0, 1).equals("1")) {
                                                    board[i][j - 1] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else if (board[i][j].substring(0, 1).equals("6")) {
                                                    if (board[i][j - 4].substring(2,3).equals("7") || board[i][j - 4].substring(2,3).equals("6") || board[i][j - 4].substring(2,3).equals("8")||!board[i][j - 4].substring(0,1).equals(" ")) {
                                                        System.out.println("请按照规则消灭动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i][j - 1].substring(2,3).equals("1") || board[i][j - 2].substring(2,3).equals("1") || board[i][j - 3].substring(2,3).equals("1")) {
                                                        System.out.println("敌方鼠在虎跳河路线中时，虎不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i][j - 4] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                } else {
                                                    if (board[i][j - 4].substring(2,3).equals("7") || board[i][j - 4].substring(2,3).equals("8")||!board[i][j - 4].substring(0,1).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i][j - 1].substring(2,3).equals("1") || board[i][j - 2].substring(2,3).equals("1") || board[i][j - 3].substring(2,3).equals("1")) {
                                                        System.out.println("敌方鼠在狮跳河路线中时，狮不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i][j - 4] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                }
                                            } //在水中的老鼠的行棋代码
                                            else if ((i == 1 || i == 2 || i == 4 || i == 5) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i][j - 1].equals("    ") || board[i][j - 1].equals(" 鼠1")|| board[i][j - 1].equals(" 水 ")) {
                                                    board[i][j - 1] = board[i][j];
                                                    board[i][j] = " 水 ";
                                                    break stop;
                                                } else {
                                                    System.out.println("请按照规则消灭敌方动物");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } //吃子的代码写法
                                        else if (board[i][j - 1].substring(2,3).equals("1") || board[i][j - 1].substring(2,3).equals("2") || board[i][j - 1].substring(2,3).equals("3") ||
                                                board[i][j - 1].substring(2,3).equals("4") || board[i][j - 1].substring(2,3).equals("5") || board[i][j - 1].substring(2,3).equals("6") ||
                                                board[i][j - 1].substring(2,3).equals("7") || board[i][j - 1].substring(2,3).equals("8")) {
                                            int q = Integer.parseInt(board[i][j - 1].substring(2,3));
                                            if(A == 8&&q == 1) {
                                                if ((i == 2 && j == 1) || (i == 3 && j == 2) || (i == 4 && j == 1)) {
                                                    board[i][j - 1] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            } else if (A >= q || (A == 1 && q == 8)) {
                                                board[i][j - 1] = board[i][j];
                                                board[i][j] = "    ";
                                                break stop;
                                            } else {
                                                if ((i == 2 && j == 1) || (i == 3 && j == 2) || (i == 4 && j == 1)) {
                                                    board[i][j - 1] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } else {
                                            board[i][j - 1] = board[i][j];
                                            board[i][j] = "    ";
                                            break stop;
                                        }
                                    }
                                }
                                if (nextPosition.substring(1, 2).equals("w")) {
                                    if (i == 0) {
                                        System.out.println("动物不能走出地图边界");
                                        n = n - 1;
                                        break stop;
                                    } else {
                                        if (!board[i - 1][j].substring(0, 1).equals(" ")) {
                                            System.out.println("己方动物不能进入友方单位所在的格子");
                                            n = n - 1;
                                            break stop;
                                        } else if (i == 4 && j == 0) {
                                            System.out.println("己方动物不能进入己方兽穴");
                                            n = n - 1;
                                            break stop;
                                        } else if ((i == 2 && j == 0) || (i == 3 && j == 1) || (i == 3 && j == 7) || (i == 2 && j == 8)) {
                                            board[i - 1][j] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break stop;
                                        } //当棋子在水中或将要进入水中时的代码
                                        else if ((i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) && (j == 3 || j == 4 || j == 5)) {
                                            //棋子将要进入水中时
                                            if ((i == 3 || i == 6) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i][j].substring(0, 1).equals("2") || board[i][j].substring(0, 1).equals("3") || board[i][j].substring(0, 1).equals("4") ||
                                                        board[i][j].substring(0, 1).equals("5") || board[i][j].substring(0, 1).equals("8")) {
                                                    System.out.println("该子不能下水");
                                                    n = n - 1;
                                                    break stop;
                                                } else if (board[i][j].substring(0, 1).equals("1")) {
                                                    board[i - 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else if (board[i][j].substring(0, 1).equals("6")) {
                                                    if (board[i - 3][j].substring(2,3).equals("7") || board[i - 3][j].substring(2,3).equals("6") || board[i - 3][j].substring(2,3).equals("8")||!board[i-3][j].substring(0,1).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i - 1][j].substring(2,3).equals("1") || board[i - 2][j].substring(2,3).equals("1")) {
                                                        System.out.println("敌方鼠在虎跳河路线中时，虎不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i - 3][j] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                } else {
                                                    if (board[i - 3][j].substring(2,3).equals("7") || board[i - 3][j].substring(2,3).equals("8")||!board[i-3][j].substring(0,1).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i - 1][j].substring(2,3).equals("1") || board[i - 2][j].substring(2,3).equals("1")) {
                                                        System.out.println("敌方鼠在狮跳河路线中时，狮不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i - 3][j] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                }
                                            }//在水中的老鼠的行棋代码
                                            else if ((i == 1 || i == 2 || i == 4 || i == 5) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i - 1][j].equals("    ") || board[i - 1][j].equals(" 鼠1") || board[i - 1][j].equals(" 水 ")) {
                                                    board[i - 1][j] = board[i][j];
                                                    board[i][j] = " 水 ";
                                                    break stop;
                                                } else {
                                                    System.out.println("请按照规则消灭敌方动物");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } //吃子的代码写法
                                        else if (board[i - 1][j].substring(2,3).equals("1") || board[i - 1][j].substring(2,3).equals("2") || board[i - 1][j].substring(2,3).equals("3") ||
                                                board[i - 1][j].substring(2,3).equals("4") || board[i - 1][j].substring(2,3).equals("5") || board[i - 1][j].substring(2,3).equals("6") ||
                                                board[i - 1][j].substring(2,3).equals("7") || board[i - 1][j].substring(2,3).equals("8")) {
                                            int q = Integer.parseInt(board[i - 1][j].substring(2,3));
                                            if(A == 8&&q == 1) {
                                                if ((i == 5 && j == 0) || (i == 4 && j == 1) ) {
                                                    board[i-1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }else if ((A >= q) || (A == 1 && q == 8)) {
                                                board[i - 1][j] = board[i][j];
                                                board[i][j] = "    ";
                                                break stop;
                                            } else {
                                                if ((i == 5 && j == 0) || (i == 4 && j == 1)) {
                                                    board[i - 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } else if (i == 4 && j == 8) {
                                            System.out.println("左方胜利");
                                            board[i - 1][j] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break;
                                        } else {
                                            board[i - 1][j] = board[i][j];
                                            board[i][j] = "    ";
                                            break stop;
                                        }
                                    }
                                }
                                if (nextPosition.substring(1, 2).equals("s")) {
                                    if (i == 6) {
                                        System.out.println("动物不能走出地图边界");
                                        n = n - 1;
                                        break stop;
                                    } else {
                                        if (!board[i + 1][j].substring(0, 1).equals(" ")) {
                                            System.out.println("己方动物不能进入友方单位所在的格子");
                                            n = n - 1;
                                            break stop;
                                        } else if (i == 2 && j == 0) {
                                            System.out.println("己方动物不能进入己方兽穴");
                                            n = n - 1;
                                            break stop;
                                        } else if ((i == 4 && j == 0) || (i == 3 && j == 1) || (i == 3 && j == 7) || (i == 4 && j == 8)) {
                                            board[i + 1][j] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break stop;
                                        } //当棋子在水中或将要进入水中时的代码
                                        else if ((i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 0) && (j == 3 || j == 4 || j == 5)) {
                                            //棋子将要进入水中时
                                            if ((i == 3 || i == 0) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i][j].substring(0, 1).equals("2") || board[i][j].substring(0, 1).equals("3") || board[i][j].substring(0, 1).equals("4") ||
                                                        board[i][j].substring(0, 1).equals("5") || board[i][j].substring(0, 1).equals("8")) {
                                                    System.out.println("该子不能下水");
                                                    n = n - 1;
                                                    break stop;
                                                } else if (board[i][j].substring(0, 1).equals("1")) {
                                                    board[i + 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else if (board[i][j].substring(0, 1).equals("6")) {
                                                    if (board[i + 3][j].substring(2,3).equals("7") || board[i + 3][j].substring(2,3).equals("6") || board[i + 3][j].substring(2,3).equals("8")||!board[i+3][j].substring(0,1).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i + 1][j].substring(2,3).equals("1") || board[i + 2][j].substring(2,3).equals("1")) {
                                                        System.out.println("敌方鼠在虎跳河路线中时，虎不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i + 3][j] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                } else {
                                                    if (board[i + 3][j].substring(2,3).equals("7") || board[i + 3][j].substring(2,3).equals("8")||!board[i+3][j].substring(0,1).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i + 1][j].substring(2,3).equals("1") || board[i + 2][j].substring(2,3).equals("1")) {
                                                        System.out.println("敌方鼠在狮跳河路线中时，狮不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i + 3][j] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                }
                                            } //在水中的老鼠的行棋代码
                                            else if ((i == 1 || i == 2 || i == 4 || i == 5) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i + 1][j].equals("    ") || board[i + 1][j].equals(" 鼠1")||board[i + 1][j].equals(" 水 ")) {
                                                    board[i + 1][j] = board[i][j];
                                                    board[i][j] = " 水 ";
                                                    break stop;
                                                } else {
                                                    System.out.println("请按照规则消灭敌方动物");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } //吃子的代码写法
                                        else if (board[i + 1][j].substring(2,3).equals("1") || board[i + 1][j].substring(2,3).equals("2") || board[i + 1][j].substring(2,3).equals("3") ||
                                                board[i + 1][j].substring(2,3).equals("4") || board[i + 1][j].substring(2,3).equals("5") || board[i + 1][j].substring(2,3).equals("6") ||
                                                board[i + 1][j].substring(2,3).equals("7") || board[i + 1][j].substring(2,3).equals("8")) {
                                            int q = Integer.parseInt(board[i +1 ][j].substring(2,3));
                                            if(A == 8&&q ==1){
                                                if ((i == 1 && j == 0) || (i == 2 && j == 1)) {
                                                    board[i + 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            } else if (A >= q || (A == 1 && q == 8)) {
                                                board[i + 1][j] = board[i][j];
                                                board[i][j] = "    ";
                                                break stop;
                                            } else {
                                                if ((i == 1 && j == 0) || (i == 2 && j == 1)) {
                                                    board[i + 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } else if (i == 2 && j == 8) {
                                            System.out.println("左方胜利");
                                            board[i + 1][j] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break;
                                        } else {
                                            board[i + 1][j] = board[i][j];
                                            board[i][j] = "    ";
                                            break stop;
                                        }
                                    }
                                }
                                if (nextPosition.substring(1, 2).equals("d")) {
                                    if (j == 8) {
                                        System.out.println("动物不能走出地图边界");
                                        n = n - 1;
                                        break stop;
                                    } else {
                                        if (!board[i][j + 1].substring(0, 1).equals(" ")) {
                                            System.out.println("己方动物不能进入友方单位所在的格子");
                                            n = n - 1;
                                            break stop;
                                        } else if ((i == 4 && j == 0) || (i == 3 && j == 1) || (i == 2 && j == 0)) {
                                            board[i][j + 1] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break stop;
                                        } //当棋子在水中或将要进入水中时的代码
                                        else if ((i == 1 && j == 3) || (i == 1 && j == 4) || (i == 1 && j == 5) || (i == 1 && j == 2) || (i == 2 && j == 4) || (i == 2 && j == 5) ||
                                                (i == 2 && j == 3) || (i == 2 && j == 2) || (i == 4 && j == 2) || (i == 4 && j == 3) || (i == 4 && j == 4) || (i == 4 && j == 5) ||
                                                (i == 5 && j == 2) || (i == 5 && j == 3) || (i == 5 && j == 4) || (i == 5 && j == 5)) {
                                            //棋子将要进入水中时
                                            if ((i == 1 && j == 2) || (i == 2 && j == 2) || (i == 4 && j == 2) || (i == 5 && j == 2)) {
                                                if (board[i][j].substring(0, 1).equals("2") || board[i][j].substring(0, 1).equals("3") || board[i][j].substring(0, 1).equals("4") ||
                                                        board[i][j].substring(0, 1).equals("5") || board[i][j].substring(0, 1).equals("8")) {
                                                    System.out.println("该子不能下水");
                                                    n = n - 1;
                                                    break stop;
                                                } else if (board[i][j].substring(0, 1).equals("1")) {
                                                    board[i][j + 1] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else if (board[i][j].substring(0, 1).equals("6")) {
                                                    if (board[i][j + 4].substring(2,3).equals("7") || board[i][j + 4].substring(2,3).equals("6") || board[i][j + 4].substring(2,3).equals("8")||!board[i][j + 4].substring(0,1).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i][j + 1].substring(2,3).equals("1") || board[i][j + 2].substring(2,3).equals("1") || board[i][j + 3].substring(2,3).equals("1")) {
                                                        System.out.println("敌方鼠在虎跳河路线中时，虎不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i][j + 4] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                } else {
                                                    if (board[i][j + 4].substring(2,3).equals("7") || board[i][j + 4].substring(2,3).equals("8")||!board[i][j + 4].substring(0,1).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i][j + 1].substring(2,3).equals("1") || board[i][j + 2].substring(2,3).equals("1") || board[i][j + 3].substring(2,3).equals("1")) {
                                                        System.out.println("敌方鼠在狮跳河路线中时，狮不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i][j + 4] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                }
                                            } //在水中的老鼠的行棋代码
                                            else if ((i == 1 && j == 3) || (i == 1 && j == 4) || (i == 1 && j == 5) || (i == 2 && j == 4) || (i == 2 && j == 5) ||
                                                    (i == 2 && j == 3) || (i == 4 && j == 3) || (i == 4 && j == 4) || (i == 4 && j == 5) ||
                                                    (i == 5 && j == 3) || (i == 5 && j == 4) || (i == 5 && j == 5)) {
                                                if (board[i][j + 1].equals("    ") || board[i][j + 1].equals(" 鼠1")|| board[i][j + 1].equals(" 水 ")) {
                                                    board[i][j + 1] = board[i][j];
                                                    board[i][j] = " 水 ";
                                                    break stop;
                                                } else {
                                                    System.out.println("请按照规则消灭敌方动物");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        }//吃子的代码写法
                                        else if (board[i][j + 1].substring(2,3).equals("1") || board[i][j + 1].substring(2,3).equals("2") || board[i][j + 1].substring(2,3).equals("3") ||
                                                board[i][j + 1].substring(2,3).equals("4") || board[i][j + 1].substring(2,3).equals("5") || board[i][j + 1].substring(2,3).equals("6") ||
                                                board[i][j + 1].substring(2,3).equals("7") || board[i][j + 1].substring(2,3).equals("8")) {
                                            int q = Integer.parseInt(board[i][j + 1].substring(2,3));
                                            if(A == 8&&q == 1){
                                                System.out.println("动物不允许送死");
                                                n = n - 1;
                                                break stop;
                                            } else if (A >= q || (A == 1 && q == 8)) {
                                                board[i][j + 1] = board[i][j];
                                                board[i][j] = "    ";
                                                break stop;
                                            } else {
                                                System.out.println("动物不允许送死");
                                                n = n - 1;
                                                break stop;
                                            }
                                        } else if (i == 3 && j == 7) {
                                            System.out.println("左方胜利");
                                            board[i][j + 1] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break;
                                        } else {
                                            board[i][j + 1] = board[i][j];
                                            board[i][j] = "    ";
                                            break stop;
                                        }
                                    }
                                }break stop;
                            }else if(i == 6&&j == 8){
                                if(!board[6][8].substring(0,1).equals(nextPosition.substring(0,1))){
                                    System.out.println("该子已被吃掉");
                                    n = n-1;
                                    break stop;
                                }
                            }
                        }
                    }
                }else {
                    System.out.println("输入指令错误,请重输");
                    n = n - 1;
                }
            }else {
                System.out.print("右方行棋: ");
                String nextPosition = scanner.next();
                System.out.println(nextPosition);
                if (nextPosition.equals("undo")) {
                    if (n == 0) {
                        System.out.println("已经退回到开局，不能再悔棋了！");
                        n = n-1;
                    } else {
                        for (int i = 0; i < 7; i++) {
                            for (int j = 0; j < 9; j++) {
                                board[i][j] = boardHistory[n - 1][i][j];
                            }
                        }
                        n = n - 2;
                        k = k + 1;
                    }
                } else if (nextPosition.equals("redo")) {
                    if (k == 0) {
                        System.out.println("已回到最后记录，不能再取消悔棋了");
                        n = n-1;
                    } else {
                        for (int i = 0; i < 7; i++) {
                            for (int j = 0; j < 9; j++) {
                                board[i][j] = boardHistory[n + 1][i][j];
                            }
                        }
                        k = k - 1;
                    }
                } else if (nextPosition.equals("help")) {
                    System.out.println("指令介绍：");
                    System.out.println("1.移动指令");
                    System.out.println("    移动指令由两个部分组成。");
                    System.out.println("    第一个部分是数字1-8，根据战斗力分别对应属（1），猫（2），狼（3），狗（4），豹（5），虎（6），狮（7），象（8）");
                    System.out.println("    第二个部分是字母wasd中的一个，w对应上方向，a对应左方向，s对应下方向，d对应右方向");
                    System.out.println("    比如指令“1d”表示鼠向右走，4w表示狗向左走");
                    System.out.println("2.游戏指令");
                    System.out.println("    输入restart重新开始游戏");
                    System.out.println("    输入help查看帮助");
                    System.out.println("    输入undo悔棋");
                    System.out.println("    输入redo取消悔棋");
                    System.out.println("    输入exit退出游戏");
                    n = n-1;
                } else if (nextPosition.equals("exit")) {
                    break;
                } else if (nextPosition.equals("restart")) {
                    n = -1;
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            board[i][j] = boardHistory[0][i][j];

                        }
                    }
                } else if ((nextPosition.substring(0,1).equals("1") || nextPosition.substring(0,1).equals("2") || nextPosition.substring(0,1).equals("3") ||
                        nextPosition.substring(0,1).equals("4") || nextPosition.substring(0,1).equals("5") || nextPosition.substring(0,1).equals("6") ||
                        nextPosition.substring(0, 1).equals("7") || nextPosition.substring(0, 1).equals("8")) && (nextPosition.substring(1, 2).equals("a") ||
                        nextPosition.substring(1, 2).equals("w") || nextPosition.substring(1, 2).equals("s") || nextPosition.substring(1, 2).equals("d"))
                        && nextPosition.length() == 2) {
                    int A = Integer.parseInt(nextPosition.substring(0, 1));
                    stop:
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (board[i][j].substring(2, 3).equals(nextPosition.substring(0, 1))) {
                                if (nextPosition.substring(1, 2).equals("a")) {
                                    if (j == 0) {
                                        System.out.println("动物不能走出地图边界");
                                        n = n - 1;
                                        break stop;
                                    } else {
                                        if (!board[i][j - 1].substring(2, 3).equals(" ")) {
                                            System.out.println("己方动物不能进入友方单位所在的格子");
                                            n = n - 1;
                                            break stop;
                                        } else if ((i == 3 && j == 7) || (i == 2 && j == 8) || (i == 4 && j == 8)) {
                                            board[i][j - 1] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break stop;
                                        }//当棋子在水中或将要进入水中时的代码
                                        else if ((i == 1 || i == 2 || i == 4 || i == 5) && (j == 3 || j == 4 || j == 5 || j == 6)) {
                                            //棋子将要进入水中时
                                            if ((i == 1 || i == 2 || i == 4 || i == 5) && j == 6) {
                                                if (board[i][j].substring(2, 3).equals("2") || board[i][j].substring(2, 3).equals("3") || board[i][j].substring(2, 3).equals("4") ||
                                                        board[i][j].substring(2, 3).equals("5") || board[i][j].substring(2, 3).equals("8")) {
                                                    System.out.println("该子不能下水");
                                                    n = n - 1;
                                                    break stop;
                                                } else if (board[i][j].substring(2, 3).equals("1")) {
                                                    board[i][j - 1] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else if (board[i][j].substring(2, 3).equals("6")) {
                                                    if (board[i][j - 4].substring(0, 1).equals("7") || board[i][j - 4].substring(0, 1).equals("6") || board[i][j - 4].substring(0, 1).equals("8") || !board[i][j - 4].substring(2, 3).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i][j - 1].substring(0, 1).equals("1") || board[i][j - 2].substring(0, 1).equals("1") || board[i][j - 3].substring(0, 1).equals("1")) {
                                                        System.out.println("敌方鼠在虎跳河路线中时，虎不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i][j - 4] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                } else {
                                                    if (board[i][j - 4].substring(0, 1).equals("7") || board[i][j - 4].substring(0, 1).equals("8") || !board[i][j - 4].substring(2, 3).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i][j - 1].substring(0, 1).equals("1") || board[i][j - 2].substring(0, 1).equals("1") || board[i][j - 3].substring(0, 1).equals("1")) {
                                                        System.out.println("敌方鼠在狮跳河路线中时，狮不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i][j - 4] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                }
                                            }//在水中的老鼠的行棋代码
                                            else if ((i == 1 || i == 2 || i == 4 || i == 5) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i][j - 1].equals("    ") || board[i][j - 1].equals("1鼠 ")||board[i][j - 1].equals(" 水 ")) {
                                                    board[i][j - 1] = board[i][j];
                                                    board[i][j] = " 水 ";
                                                    break stop;
                                                } else {
                                                    System.out.println("请按照规则消灭敌方动物");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        }//吃子的代码写法
                                        else if (board[i][j - 1].substring(0, 1).equals("1") || board[i][j - 1].substring(0, 1).equals("2") || board[i][j - 1].substring(0, 1).equals("3") ||
                                                board[i][j - 1].substring(0, 1).equals("4") || board[i][j - 1].substring(0, 1).equals("5") || board[i][j - 1].substring(0, 1).equals("6") ||
                                                board[i][j - 1].substring(0, 1).equals("7") || board[i][j - 1].substring(0, 1).equals("8")) {
                                            int q = Integer.parseInt(board[i][j - 1].substring(0, 1));
                                            if(A == 8&&q == 1){
                                                System.out.println("动物不允许送死");
                                                n = n - 1;
                                                break stop;
                                            } else if (A >= q || (A == 1 && q == 8)) {
                                                board[i][j - 1] = board[i][j];
                                                board[i][j] = "    ";
                                                break stop;
                                            } else {
                                                System.out.println("动物不允许送死");
                                                n = n - 1;
                                                break stop;
                                            }
                                        } else if (i == 3 && j == 1) {
                                            System.out.println("右方胜利");
                                            board[i][j - 1] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break;
                                        } else {
                                            board[i][j - 1] = board[i][j];
                                            board[i][j] = "    ";
                                            break stop;
                                        }
                                    }
                                }
                                if (nextPosition.substring(1, 2).equals("w")) {
                                    if (i == 0) {
                                        System.out.println("动物不能走出地图边界");
                                        n = n - 1;
                                        break stop;
                                    } else {
                                        if (!board[i - 1][j].substring(2, 3).equals(" ")) {
                                            System.out.println("己方动物不能进入友方单位所在的格子");
                                            n = n - 1;
                                            break stop;
                                        } else if (i == 4 && j == 8) {
                                            System.out.println("己方动物不能进入己方兽穴");
                                            n = n - 1;
                                            break stop;
                                        } else if ((i == 2 && j == 0) || (i == 3 && j == 1) || (i == 3 && j == 7) || (i == 2 && j == 8)) {
                                            board[i - 1][j] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break stop;
                                        } //当棋子在水中或将要进入水中时的代码
                                        else if ((i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) && (j == 3 || j == 4 || j == 5)) {
                                            //棋子将要进入水中时
                                            if ((i == 3 || i == 6) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i][j].substring(2, 3).equals("2") || board[i][j].substring(2, 3).equals("3") || board[i][j].substring(2, 3).equals("4") ||
                                                        board[i][j].substring(2, 3).equals("5") || board[i][j].substring(2, 3).equals("8")) {
                                                    System.out.println("该子不能下水");
                                                    n = n - 1;
                                                    break stop;
                                                } else if (board[i][j].substring(2, 3).equals("1")) {
                                                    board[i - 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else if (board[i][j].substring(2, 3).equals("6")) {
                                                    if (board[i - 3][j].substring(0, 1).equals("7") || board[i - 3][j].substring(0, 1).equals("6") || board[i - 3][j].substring(0, 1).equals("8") || !board[i - 3][j].substring(2, 3).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i - 1][j].substring(0, 1).equals("1") || board[i - 2][j].substring(0, 1).equals("1")) {
                                                        System.out.println("敌方鼠在虎跳河路线中时，虎不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i - 3][j] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                } else {
                                                    if (board[i - 3][j].substring(0, 1).equals("7") || board[i - 3][j].substring(0, 1).equals("8") || !board[i - 3][j].substring(2, 3).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i - 1][j].substring(0, 1).equals("1") || board[i - 2][j].substring(0, 1).equals("1")) {
                                                        System.out.println("敌方鼠在狮跳河路线中时，狮不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i - 3][j] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                }
                                            } //在水中的老鼠的行棋代码
                                            else if ((i == 1 || i == 2 || i == 4 || i == 5) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i - 1][j].equals("    ") || board[i - 1][j].equals("1鼠 ")||board[i - 1][j].equals(" 水 ")) {
                                                    board[i - 1][j] = board[i][j];
                                                    board[i][j] = " 水 ";
                                                    break stop;
                                                } else {
                                                    System.out.println("请按照规则消灭敌方动物");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } //吃子的代码写法
                                        else if (board[i - 1][j].substring(0, 1).equals("1") || board[i - 1][j].substring(0, 1).equals("2") || board[i - 1][j].substring(0, 1).equals("3") ||
                                                board[i - 1][j].substring(0, 1).equals("4") || board[i - 1][j].substring(0, 1).equals("5") || board[i - 1][j].substring(0, 1).equals("6") ||
                                                board[i - 1][j].substring(0, 1).equals("7") || board[i - 1][j].substring(0, 1).equals("8")) {
                                            int q = Integer.parseInt(board[i - 1][j].substring(0, 1));
                                            if(A == 8&&q == 1) {
                                                if ((i == 5 && j == 8) || (i == 4 && j == 7) ) {
                                                    board[i-1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            } else if (A >= q || (A == 1 && q == 8)) {
                                                board[i - 1][j] = board[i][j];
                                                board[i][j] = "    ";
                                                break stop;
                                            } else {
                                                if ((i == 5 && j == 8) || (i == 4 && j == 7)) {
                                                    board[i - 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } else if (i == 4 && j == 0) {
                                            System.out.println("右方胜利");
                                            board[i - 1][j] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break;
                                        } else {
                                            board[i - 1][j] = board[i][j];
                                            board[i][j] = "    ";
                                            break stop;
                                        }
                                    }
                                }
                                if (nextPosition.substring(1, 2).equals("s")) {
                                    if (i == 6) {
                                        System.out.println("动物不能走出地图边界");
                                        n = n - 1;
                                        break stop;
                                    } else {
                                        if (!board[i + 1][j].substring(2, 3).equals(" ")) {
                                            System.out.println("己方动物不能进入友方单位所在的格子");
                                            n = n - 1;
                                            break stop;
                                        } else if (i == 2 && j == 8) {
                                            System.out.println("己方动物不能进入己方兽穴");
                                            n = n - 1;
                                            break stop;
                                        } else if ((i == 4 && j == 0) || (i == 3 && j == 1) || (i == 3 && j == 7) || (i == 4 && j == 8)) {
                                            board[i + 1][j] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break stop;
                                        } //当棋子在水中或将要进入水中时的代码
                                        else if ((i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 0) && (j == 3 || j == 4 || j == 5)) {
                                            //棋子将要进入水中时
                                            if ((i == 3 || i == 0) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i][j].substring(2, 3).equals("2") || board[i][j].substring(2, 3).equals("3") || board[i][j].substring(2, 3).equals("4") ||
                                                        board[i][j].substring(2, 3).equals("5") || board[i][j].substring(2, 3).equals("8")) {
                                                    System.out.println("该子不能下水");
                                                    n = n - 1;
                                                    break stop;
                                                } else if (board[i][j].substring(2, 3).equals("1")) {
                                                    board[i + 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else if (board[i][j].substring(2, 3).equals("6")) {
                                                    if (board[i + 3][j].substring(0, 1).equals("7") || board[i + 3][j].substring(0, 1).equals("6") || board[i + 3][j].substring(0, 1).equals("8") || !board[i + 3][j].substring(2, 3).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i + 1][j].substring(0, 1).equals("1") || board[i + 2][j].substring(0, 1).equals("1")) {
                                                        System.out.println("敌方鼠在虎跳河路线中时，虎不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i + 3][j] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                } else {
                                                    if (board[i + 3][j].substring(0, 1).equals("7") || board[i + 3][j].substring(0, 1).equals("8") || !board[i + 3][j].substring(2, 3).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i + 1][j].substring(0, 1).equals("1") || board[i + 2][j].substring(0, 1).equals("1")) {
                                                        System.out.println("敌方鼠在狮跳河路线中时，狮不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i + 3][j] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                }
                                            } //在水中的老鼠的行棋代码
                                            else if ((i == 1 || i == 2 || i == 4 || i == 5) && (j == 3 || j == 4 || j == 5)) {
                                                if (board[i + 1][j].equals("    ") || board[i + 1][j].equals("1鼠 ")|| board[i + 1][j].equals(" 水 ")) {
                                                    board[i + 1][j] = board[i][j];
                                                    board[i][j] = " 水 ";
                                                    break stop;
                                                } else {
                                                    System.out.println("请按照规则消灭敌方动物");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        }//吃子的代码写法
                                        else if (board[i + 1][j].substring(0, 1).equals("1") || board[i + 1][j].substring(0, 1).equals("2") || board[i + 1][j].substring(0, 1).equals("3") ||
                                                board[i + 1][j].substring(0, 1).equals("4") || board[i + 1][j].substring(0, 1).equals("5") || board[i + 1][j].substring(0, 1).equals("6") ||
                                                board[i + 1][j].substring(0, 1).equals("7") || board[i + 1][j].substring(0, 1).equals("8")) {
                                            int q = Integer.parseInt(board[i + 1][j].substring(0, 1));
                                            if(A == 8&&q ==1){
                                                if ((i == 1 && j == 8) || (i == 2 && j == 7)) {
                                                    board[i + 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }else if (A >= q || (A == 1 && q == 8)) {
                                                board[i + 1][j] = board[i][j];
                                                board[i][j] = "    ";
                                                break stop;
                                            } else {
                                                if ((i == 1 && j == 8) || (i == 2 && j == 7)) {
                                                    board[i + 1][j] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } else if (i == 2 && j == 0) {
                                            System.out.println("右方胜利");
                                            board[i + 1][j] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break;
                                        } else {
                                            board[i + 1][j] = board[i][j];
                                            board[i][j] = "    ";
                                            break stop;
                                        }
                                    }
                                }
                                if (nextPosition.substring(1, 2).equals("d")) {
                                    if (j == 8) {
                                        System.out.println("动物不能走出地图边界");
                                        n = n - 1;
                                        break stop;
                                    } else {
                                        if (!board[i][j + 1].substring(2, 3).equals(" ")) {
                                            System.out.println("己方动物不能进入友方单位所在的格子");
                                            n = n - 1;
                                            break stop;
                                        } else if (i == 3 && j == 7) {
                                            System.out.println("己方动物不能进入己方兽穴");
                                        } else if ((i == 4 && j == 0) || (i == 3 && j == 1) || (i == 2 && j == 0)) {
                                            board[i][j + 1] = board[i][j];
                                            board[i][j] = " 陷 ";
                                            break stop;
                                        }//当棋子在水中或将要进入水中时的代码
                                        else if ((i == 1 && j == 3) || (i == 1 && j == 4) || (i == 1 && j == 5) || (i == 1 && j == 2) || (i == 2 && j == 4) || (i == 2 && j == 5) ||
                                                (i == 2 && j == 3) || (i == 2 && j == 2) || (i == 4 && j == 2) || (i == 4 && j == 3) || (i == 4 && j == 4) || (i == 4 && j == 5) ||
                                                (i == 5 && j == 2) || (i == 5 && j == 3) || (i == 5 && j == 4) || (i == 5 && j == 5)) {
                                            //棋子将要进入水中时
                                            if ((i == 1 && j == 2) || (i == 2 && j == 2) || (i == 4 && j == 2) || (i == 5 && j == 2)) {
                                                if (board[i][j].substring(2, 3).equals("2") || board[i][j].substring(2, 3).equals("3") || board[i][j].substring(2, 3).equals("4") ||
                                                        board[i][j].substring(2, 3).equals("5") || board[i][j].substring(2, 3).equals("8")) {
                                                    System.out.println("该子不能下水");
                                                    n = n - 1;
                                                    break stop;
                                                } else if (board[i][j].substring(2, 3).equals("1")) {
                                                    board[i][j + 1] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                } else if (board[i][j].substring(2, 3).equals("6")) {
                                                    if (board[i][j + 4].substring(0, 1).equals("7") || board[i][j + 4].substring(0, 1).equals("6") || board[i][j + 4].substring(0, 1).equals("8") || !board[i][j + 4].substring(2, 3).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i][j + 1].substring(0, 1).equals("1") || board[i][j + 2].substring(0, 1).equals("1") || board[i][j + 3].substring(0, 1).equals("1")) {
                                                        System.out.println("敌方鼠在虎跳河路线中时，虎不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i][j + 4] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                } else {
                                                    if (board[i][j + 4].substring(0, 1).equals("7") || board[i][j + 4].substring(0, 1).equals("8") || !board[i][j + 4].substring(2, 3).equals(" ")) {
                                                        System.out.println("请按照规则消灭敌方动物");
                                                        n = n - 1;
                                                        break stop;
                                                    } else if (board[i][j + 1].substring(0, 1).equals("1") || board[i][j + 2].substring(0, 1).equals("1") || board[i][j + 3].substring(0, 1).equals("1")) {
                                                        System.out.println("敌方鼠在狮跳河路线中时，狮不能跳河");
                                                        n = n - 1;
                                                        break stop;
                                                    } else {
                                                        board[i][j + 4] = board[i][j];
                                                        board[i][j] = "    ";
                                                        break stop;
                                                    }
                                                }
                                            } //在水中的老鼠的行棋代码
                                            else if ((i == 1 && j == 3) || (i == 1 && j == 4) || (i == 1 && j == 5) || (i == 2 && j == 4) || (i == 2 && j == 5) ||
                                                    (i == 2 && j == 3) || (i == 4 && j == 3) || (i == 4 && j == 4) || (i == 4 && j == 5) ||
                                                    (i == 5 && j == 3) || (i == 5 && j == 4) || (i == 5 && j == 5)) {
                                                if (board[i][j + 1].equals("    ") || board[i][j + 1].equals("1鼠 ")|| board[i][j + 1].equals(" 水 ")) {
                                                    board[i][j + 1] = board[i][j];
                                                    board[i][j] = " 水 ";
                                                    break stop;
                                                } else {
                                                    System.out.println("请按照规则消灭敌方动物");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } //吃子的代码写法
                                        else if (board[i][j + 1].substring(0, 1).equals("1") || board[i][j + 1].substring(0, 1).equals("2") || board[i][j + 1].substring(0, 1).equals("3") ||
                                                board[i][j + 1].substring(0, 1).equals("4") || board[i][j + 1].substring(0, 1).equals("5") || board[i][j + 1].substring(0, 1).equals("6") ||
                                                board[i][j + 1].substring(0, 1).equals("7") || board[i][j + 1].substring(0, 1).equals("8")) {
                                            int q = Integer.parseInt(board[i][j + 1].substring(0, 1));
                                            if(A == 8&&q == 1){
                                                if((i ==2&&j == 7)||(i ==3&&j == 6)||(i ==4&&j == 7)){
                                                    board[i][j + 1] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                }else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }else if (A >= q || (A == 1 && q == 8)) {
                                                board[i][j + 1] = board[i][j];
                                                board[i][j] = "    ";
                                                break stop;
                                            } else {
                                                if((i ==2&&j == 7)||(i ==3&&j == 6)||(i ==4&&j == 7)){
                                                    board[i][j + 1] = board[i][j];
                                                    board[i][j] = "    ";
                                                    break stop;
                                                }else {
                                                    System.out.println("动物不允许送死");
                                                    n = n - 1;
                                                    break stop;
                                                }
                                            }
                                        } else {
                                            board[i][j + 1] = board[i][j];
                                            board[i][j] = "    ";
                                            break stop;
                                        }
                                    }
                                }break stop;
                            } else if (i == 6 && j == 8) {
                                if (!board[6][8].substring(0, 1).equals(nextPosition.substring(0, 1))) {
                                    System.out.println("该子已被吃掉");
                                    n = n - 1;
                                    break stop;
                                }
                            }
                        }
                    }
                }else {
                    System.out.println("输入指令错误，请重输");
                    n = n - 1;
                }
            }
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 9; j++) {
                        System.out.print(board[i][j]);
                    }
                    System.out.println();
                }
                n++;
            }
        }
    }