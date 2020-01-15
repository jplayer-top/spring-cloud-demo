package top.jplayer.im_netty.service;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import top.jplayer.im_netty.handler.NettyWebSocketHandler;

/**
 * Created by Obl on 2020/1/15.
 * top.jplayer.im_netty.service
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@Component
@Slf4j
public class NettyClient {

    /**
     * NioEventLoop并不是一个纯粹的I/O线程，它除了负责I/O的读写之外
     * 创建了两个NioEventLoopGroup，
     * 它们实际是两个独立的Reactor线程池。
     * 一个用于接收客户端的TCP连接，
     * 另一个用于处理I/O相关的读写操作，或者执行系统Task、定时任务Task等。
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup group = new NioEventLoopGroup();

    /**
     * 启动服务
     *
     * @param hostname
     * @param port
     * @return
     * @throws Exception
     */
    public ChannelFuture start(String hostname, int port) throws Exception {
        ChannelFuture cf;
        try {
            //ServerBootstrap负责初始化netty服务器，并且开始监听端口的socket请求
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(group, bossGroup)// 绑定线程池
                    .channel(NioServerSocketChannel.class) // 指定使用的channel
                    .localAddress(hostname,port)// 绑定监听端口
                    .childHandler(new ChannelInitializer<SocketChannel>() {// 绑定客户端连接时候触发操作
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            log.error("收到新连接" + socketChannel.remoteAddress().toString());
                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            socketChannel.pipeline().addLast(new HttpServerCodec());
                            //以块的方式来写的处理器
                            socketChannel.pipeline().addLast(new ChunkedWriteHandler());
                            socketChannel.pipeline().addLast(new HttpObjectAggregator(8192));
                            socketChannel.pipeline().addLast(new NettyWebSocketHandler());
                            socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10));
                        }
                    });
            cf = b.bind(port).sync();
            cf.channel().closeFuture().sync(); // 关闭服务器通道
            log.info("======启动成功!!!=========" + cf.channel().localAddress());
        } finally {
            log.info("Shutdown Netty Server...");
            group.shutdownGracefully().sync(); // 释放线程池资源
            bossGroup.shutdownGracefully().sync();
        }
        return cf;
    }

    /**
     * 停止服务
     */
    public void destroy() {
        log.info("Shutdown Netty Server...");
        bossGroup.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
    }
}
