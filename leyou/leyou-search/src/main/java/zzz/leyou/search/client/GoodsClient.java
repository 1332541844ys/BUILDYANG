package zzz.leyou.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import zzz.leyou.item.api.GoodsApi;

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
