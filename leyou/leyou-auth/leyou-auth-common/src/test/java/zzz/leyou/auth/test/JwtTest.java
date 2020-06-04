package zzz.leyou.auth.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zzz.leyou.common.pojo.UserInfo;
import zzz.leyou.common.utils.JwtUtils;
import zzz.leyou.common.utils.RsaUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
public class JwtTest {

    private static final String pubKeyPath = "F:\\rsa\\rsa.pub";

    private static final String priKeyPath = "F:\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU5MDg5MjcyMX0.GnkY0c2eS5PJSR-XsPB0uEQYgespxjO_8YyJQAJcpsmERND8oZKYmrebpnQp7-S4lTYben5g4iAhaT9939ObAWIl6Y9Ob_OYHrrYvjRq4jOqiku5zGxvSQjTKfv2VmMKHMDeiRhVvutPmNPMwrZt993vdE63r8x8S-h1NG3JXEU";
        //解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}