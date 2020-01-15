package top.jplayer.im_netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import top.jplayer.im_netty.service.NettyClient;

/**
 * Created by Obl on 2020/1/15.
 * PACKAGE_NAME
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@EnableDiscoveryClient
@Slf4j
@SpringBootApplication
public class ImNettyApplication implements CommandLineRunner {
    @Value("${netty.port}")
    private int port;

    @Value("${netty.url}")
    private String url;

    @Autowired
    NettyClient mNettyClient;

    public static void main(String[] args) {
        SpringApplication.run(ImNettyApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = mNettyClient.start(url, port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> mNettyClient.destroy()));
        if (future != null) {
            future.channel().closeFuture().syncUninterruptibly();
        }
    }

}
