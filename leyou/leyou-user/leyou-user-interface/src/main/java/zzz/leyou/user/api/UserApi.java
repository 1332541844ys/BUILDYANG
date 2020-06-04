package zzz.leyou.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zzz.leyou.user.pojo.User;

public interface UserApi {
    @GetMapping("query")
    public User queryUser(@RequestParam("username")String username,
                                          @RequestParam("password")String password);
}
