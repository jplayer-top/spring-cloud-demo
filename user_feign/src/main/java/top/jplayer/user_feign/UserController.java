package top.jplayer.user_feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Obl on 2020/1/4.
 * top.jplayer.user_feign
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@RestController
public class UserController {
    @Autowired
    private UserFeign mUserFeign;

    @GetMapping(value = "login")
    public String test() throws InterruptedException {
        return mUserFeign.test();
    }

}
