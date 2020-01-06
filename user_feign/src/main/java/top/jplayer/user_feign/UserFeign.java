package top.jplayer.user_feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created by Obl on 2020/1/4.
 * top.jplayer.user_feign
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
@FeignClient(name = "userService", fallback = UserFeign.UserFeignClientFallback.class)
public interface UserFeign {
    @GetMapping(value = "test")
    String test();

    @Component
    class UserFeignClientFallback implements UserFeign {
        @Override
        public String test() {
            return "异常";
        }
    }

}
