package zzz.leyou.goods.client;

import org.springframework.cloud.openfeign.FeignClient;
import zzz.leyou.item.api.CategoryApi;


@FeignClient("item-service")//远程调用
public interface CategoryClient extends CategoryApi {

}
