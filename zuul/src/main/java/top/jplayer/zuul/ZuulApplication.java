package top.jplayer.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
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
@EnableZuulProxy
@Slf4j
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
        log.info("Zuul服务中心启动成功");
    }

}
