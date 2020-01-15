package top.jplayer.im_netty.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Obl on 2020/1/15.
 * top.jplayer.im_netty.handler
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@Component
@Slf4j
public class NettyNamePool {
    static {
        log.error("=====储存池创建======");
        nameListMap = new HashMap<>();
    }
    public static Map<String, List<String>> nameListMap;
}
