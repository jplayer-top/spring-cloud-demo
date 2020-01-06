package top.jplayer.user_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Obl on 2020/1/4.
 * top.jplayer.user_service
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@RestController
public class UserService {
    @GetMapping(value = "test")
    public String test() throws InterruptedException {
        Thread.sleep(2000);
        return "asdasd";
    }
}
