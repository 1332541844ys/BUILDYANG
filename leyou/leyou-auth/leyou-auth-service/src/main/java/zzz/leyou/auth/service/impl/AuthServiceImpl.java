package zzz.leyou.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzz.leyou.auth.client.UserClient;
import zzz.leyou.auth.config.JwtProperties;
import zzz.leyou.auth.service.AuthService;
import zzz.leyou.common.pojo.UserInfo;
import zzz.leyou.common.utils.JwtUtils;
import zzz.leyou.user.pojo.User;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties prop;
    @Override
    public String accredit(String username, String password) {

        //1.根据用户名和密码查询
        User user = this.userClient.queryUser(username, password);
        //2.判断user
        if (user == null) {
            return null;
        }
        try {
            //3.jwtUtils生成jwt类型的token
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            return JwtUtils.generateToken(userInfo,this.prop.getPrivateKey(),this.prop.getExpire());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
