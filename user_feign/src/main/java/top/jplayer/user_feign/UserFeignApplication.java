package top.jplayer.user_feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Obl on 2020/1/3.
 * top.jplayer.user_service
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@Slf4j
@EnableScheduling
@EnableAsync
public class UserFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserFeignApplication.class, args);
        log.info("用户Feign启动成功");
    }
}
