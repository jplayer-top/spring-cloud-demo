package top.jplayer.zuul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Obl on 2020/1/6.
 * top.jplayer.zuul
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@RestController
public class ZuulTestController {
    @Value("${service.type}")
    public String type;

    @GetMapping(value = "getType" )
    public String getType() {
        return type;
    }
}
