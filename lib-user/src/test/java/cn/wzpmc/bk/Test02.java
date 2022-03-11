package cn.wzpmc.bk;

import java.util.Scanner;

public class Test02 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Test02 test = new Test02();
    public static void main(String[] args){
        while (true){
            System.out.print("请输入运算符: ");
            String s = scanner.nextLine();
            switch (s){
                case "+":
                    int[] ints = test.jiSuan();
                    System.out.println("您计算的结果为 " + ints[0] + "+" + ints[1] + "=" + test.add(ints));
                    break;
                case "-":
                    int[] ints1 = test.jiSuan();
                    System.out.println("您计算的结果为 " + ints1[0] + "-" + ints1[1] + "=" + test.cut(ints1));
                    break;
                case "*":
                    int[] ints2 = test.jiSuan();
                    System.out.println("您计算的结果为 " + ints2[0] + "*" + ints2[1] + "=" + test.multiply(ints2));
                    break;
                case "/":
                    int[] ints3 = test.jiSuan();
                    System.out.println("您计算的结果为 " + ints3[0] + "/" + ints3[1] + "=" + test.divide(ints3));
                    break;
            }
        }
    }
    private int[] jiSuan(){
        System.out.print("请输入第一个数: ");
        int i = scanner.nextInt();
        System.out.print("请输入第二个数: ");
        int i1 = scanner.nextInt();
        return new int[]{i,i1};
    }
    //加
    private int add(int[] ints){
        return ints[0]+ints[1];
    }
    //乘
    private int multiply(int[] ints){
        return ints[0]*ints[1];
    }
    //减
    private int cut(int[] ints){
        return ints[0]-ints[1];
    }
    //除
    private int divide(int[] ints){
        return ints[0]/ints[1];
    }
}
