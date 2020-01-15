package top.jplayer.im_netty.handler;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Obl on 2020/1/15.
 * top.jplayer.im_netty.handler
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@ChannelHandler.Sharable
@Slf4j
public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.error("与客户端建立连接，通道开启！");
        //添加到channelGroup通道组
        NettyHandlerPool.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Map paramMap = getUrlParams(uri);
            log.error("FullHttpRequest 携带参数：" + JSON.toJSONString(paramMap));
            if (paramMap != null && paramMap.containsKey("id")) {
                String id = (String) paramMap.get("id");
                List<String> list = NettyNamePool.nameListMap.get(id);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(ctx.channel().id().asShortText());
                NettyNamePool.nameListMap.put(id, list);
                log.error(NettyNamePool.nameListMap.toString());
            }
            if (uri.contains("?")) {
                String newUri = uri.substring(0, uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }

        } else if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            log.error("TextWebSocketFrame 消息类型" + frame.text());
            sendMessage(frame.text());
        }
        super.channelRead(ctx, msg);
    }

    private void sendMessage(String message) {
        log.error("sendMessage");
        NettyHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame(message), channel1 -> {
            List<String> list = NettyNamePool.nameListMap.get("111");
            if (list != null) {
                return list.contains(channel1.id().asShortText());
            } else {
                return false;
            }
        });
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        log.error("messageReceived" + textWebSocketFrame.text());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.error("与客户端断开连接，通道关闭！");
        //添加到channelGroup 通道组
        NettyHandlerPool.remove(ctx.channel());
    }


    private static Map getUrlParams(String url) {
        Map<String, String> map = new HashMap<>();
        url = url.replace("?", ";");
        if (!url.contains(";")) {
            return map;
        }
        if (url.split(";").length > 0) {
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr) {
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key, value);
            }
            return map;

        } else {
            return map;
        }
    }

}
