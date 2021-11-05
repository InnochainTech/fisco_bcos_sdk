package inno.fiscobcos.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author peifeng
 * @Date 2021/7/16 9:48
 */
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args){
        try {
            SpringApplication.run(MainApplication.class, args);
            System.out.println("SpringBoot Start…");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
