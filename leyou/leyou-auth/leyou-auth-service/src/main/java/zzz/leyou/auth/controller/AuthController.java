package zzz.leyou.auth.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zzz.leyou.auth.config.JwtProperties;
import zzz.leyou.auth.service.AuthService;
import zzz.leyou.common.pojo.UserInfo;
import zzz.leyou.common.utils.CookieUtils;
import zzz.leyou.common.utils.JwtUtils;
import zzz.leyou.user.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProperties prop;
    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username")String username,
                                         @RequestParam("password")String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response){
        String token = this.authService.accredit(username,password);
        if (StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CookieUtils.setCookie(request,response,prop.getCookieName(),token,prop.getExpire()*60,null,true);
        return ResponseEntity.ok().build();
    }
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN")String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response){
        try {
            //通过jst工具类解析jwt，使用公钥解析
            UserInfo user = JwtUtils.getInfoFromToken(token, this.prop.getPublicKey());
            if (user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            //刷新jwt中的有效时间
            token = JwtUtils.generateToken(user, this.prop.getPrivateKey(), this.prop.getExpire());
            CookieUtils.setCookie(request,response,prop.getCookieName(),token,prop.getExpire()*60,null,true);
            //刷新cookie中的有效时间
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
