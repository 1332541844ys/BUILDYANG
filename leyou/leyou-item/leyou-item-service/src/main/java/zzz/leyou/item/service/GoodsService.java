package zzz.leyou.item.service;

import zzz.leyou.common.pojo.PageResult;
import zzz.leyou.item.bo.SpuBo;
import zzz.leyou.item.pojo.Sku;
import zzz.leyou.item.pojo.Spu;
import zzz.leyou.item.pojo.SpuDetail;

import java.util.List;

public interface GoodsService {
    //根据条件分页查询spu
    PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows);
    //新增商品
    void saveGoods(SpuBo spuBo);
    //根据spuId查询spuDetail
    SpuDetail querySpuDetailBySpuId(Long spuId);
    //根据spu的id查询sku的集合
    List<Sku> querySkuBySpuId(Long spuId);
    //更新商品信息
    void updateGoods(SpuBo spuBo);
    //根据id查询spu
    Spu querySpuById(Long id);

    Sku querySkuBySkuId(Long skuId);
}
