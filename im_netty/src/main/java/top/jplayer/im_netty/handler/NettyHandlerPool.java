package top.jplayer.im_netty.handler;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by Obl on 2020/1/15.
 * top.jplayer.im_netty.handler
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class NettyHandlerPool {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public static void add(Channel channel) {
        channelGroup.add(channel);
    }

    public static void remove(Channel channel) {
        channelGroup.remove(channel);
    }

}
