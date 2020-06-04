package zzz.leyou.goods.client;

import org.springframework.cloud.openfeign.FeignClient;
import zzz.leyou.item.api.SpecificationApi;

@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {

}
