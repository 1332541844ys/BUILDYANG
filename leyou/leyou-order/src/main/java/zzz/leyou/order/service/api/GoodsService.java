package zzz.leyou.order.service.api;


import org.springframework.cloud.openfeign.FeignClient;
import zzz.leyou.item.api.GoodsApi;

@FeignClient(value = "leyou-gateway", path = "/api/item")
public interface GoodsService extends GoodsApi {
}
