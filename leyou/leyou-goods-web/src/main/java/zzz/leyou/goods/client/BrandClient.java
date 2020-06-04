package zzz.leyou.goods.client;

import org.springframework.cloud.openfeign.FeignClient;
import zzz.leyou.item.api.BrandApi;


@FeignClient("item-service")
public interface BrandClient extends BrandApi {

}
