package cn.wzpmc.bk;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xinsin
 * @version 1.0.0
 * @date 2022/3/16
 */
public class TestRegex {
/*
        .	匹配除换行符以外的任意字符
        \w	匹配字母或数字或下划线
        \s	匹配任意的空白符
        \d	匹配数字
        \b	匹配单词的开始或结束
        ^	匹配字符串的开始
        $	匹配字符串的结束
        *	重复零次或更多次
        +	重复一次或更多次
        ?	重复零次或一次
        {n}	重复n次
        {n,}	重复n次或更多次
        {n,m}	重复n到m次
        *	重复零次或更多次
        +	重复一次或更多次
        ?	重复零次或一次
        {n}	重复n次
        {n,}	重复n次或更多次
        {n,m}	重复n到m次
*/
    @Test
    public void Test(){
        Pattern pattern = Pattern.compile("^([val]+)\\s+([A-Za-z0-9_]+)\\s*([=]+)\\s*[\"']?([^\"']+)[\"']?$");
        Matcher var_abc = pattern.matcher("val    abc    =     '3102jcosdncvonv'");
        while (var_abc.find()){
            System.out.println(String.format("分组1出现的位置索引: %d, 分组1内容<%s>", var_abc.start(1), var_abc.group(1)));
            System.out.println(String.format("分组2出现的位置索引: %d, 分组2内容<%s>", var_abc.start(2), var_abc.group(2)));
            System.out.println(String.format("分组3出现的位置索引: %d, 分组3内容<%s>", var_abc.start(3), var_abc.group(3)));
            System.out.println(String.format("分组4出现的位置索引: %d, 分组4内容<%s>", var_abc.start(4), var_abc.group(4)));
        }
    }
}
