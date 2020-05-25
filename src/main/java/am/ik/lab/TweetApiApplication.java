package am.ik.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

import java.time.Clock;

@SpringBootApplication
public class TweetApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetApiApplication.class, args);
    }


    @Bean
    public IdGenerator idGenerator() {
        return new JdkIdGenerator();
    }

    @Bean
    public Clock systemClock() {
        return Clock.systemDefaultZone();
    }
}
