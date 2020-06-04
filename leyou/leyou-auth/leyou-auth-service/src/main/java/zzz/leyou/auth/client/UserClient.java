package zzz.leyou.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import zzz.leyou.user.api.UserApi;
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
