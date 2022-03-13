package cn.wzpmc.bk;

import java.util.Scanner;

public class Test03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入一个月份(按回车结束): ");
        int month = sc.nextInt();
        if (month < 0 || month > 12){
            System.out.println("对不起,您输入的值不合法");
            System.exit(0);
        }
        if (month >= 3 && month <= 5){
            System.out.println("春天");
        }else if (month >= 6 && month <= 8){
            System.out.println("夏天");
        }else if (month >= 9 && month <= 11){
            System.out.println("秋天");
        }else if (month <= 2 || month==12){
            System.out.println("冬天");
        }
    }
}
