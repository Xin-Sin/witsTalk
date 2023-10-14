package top.xinsin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;
import top.xinsin.entity.BaseEntity;

import java.io.*;

@Configuration
public class GlobalBeanConfiguration {
    @Bean
    public File saveDirectory(@Value("${saveDir}") String saveDir){
        File file = new File(saveDir);
        if (!file.isDirectory()){
            if (!file.mkdirs()){
                throw new RuntimeException("创建储存文件夹失败");
            }
        }
        return file;
    }
    @Bean
    public File imageDirectory(@Autowired File saveDirectory){
        File file = new File(saveDirectory, "images");
        if (!file.isDirectory()){
            if (!file.mkdirs()){
                throw new RuntimeException("创建图片储存文件夹失败");
            }
        }
        return file;
    }
    @Bean
    public File portraitsDirectory(@Autowired File saveDirectory) {
        File file = new File(saveDirectory, "portraits");
        if (!file.isDirectory()){
            if (!file.mkdirs()){
                throw new RuntimeException("创建头像储存文件夹失败");
            }
        }
        return file;
    }
    @Bean
    public File fileDirectory(@Autowired File saveDirectory){
        File file = new File(saveDirectory, "files");
        if (!file.isDirectory()){
            if (!file.mkdirs()){
                throw new RuntimeException("创建文件储存文件夹失败");
            }
        }
        return file;
    }
    @Bean
    public File defaultUserPortraitFile(@Autowired File portraitsDirectory) {
        File file = new File(portraitsDirectory, "f4ff3cccf921f581d9eec8a0b67115e3749a332f996072e2214066568352901f5ed02ebb91cf52edf2703a64ed9d675d8bbbd3a216bf2f86b97d4d8aae327521");
        if (!file.isFile()){
            try {
                if (!file.createNewFile()){
                    throw new RuntimeException("创建默认头像失败");
                }
            } catch (IOException e) {
                throw new RuntimeException("创建默认头像失败", e);
            }
            try(FileOutputStream fos = new FileOutputStream(file);
                InputStream is = BaseEntity.class.getClassLoader().getResourceAsStream("default_portraits.svg")) {
                if (is != null) {
                    StreamUtils.copy(is, fos);
                }else {
                    throw new RuntimeException("创建默认头像失败，无法获取默认头像！");
                }
            } catch (IOException e) {
                throw new RuntimeException("创建默认头像失败", e);
            }
        }
        return file;
    }
}
