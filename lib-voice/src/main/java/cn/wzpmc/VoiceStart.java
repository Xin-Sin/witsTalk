package cn.wzpmc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wzp
 * Created On 2022/5/14
 * @version 1.0
 */
@SpringBootApplication
public class VoiceStart {
    private static VoiceNetty voiceNetty;
    @Autowired
    public VoiceStart(VoiceNetty voiceNetty){
        VoiceStart.voiceNetty = voiceNetty;
    }
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(VoiceStart.class);
        springApplication.run(args);
        springApplication.addListeners(voiceNetty);
        voiceNetty.start();
    }
}
