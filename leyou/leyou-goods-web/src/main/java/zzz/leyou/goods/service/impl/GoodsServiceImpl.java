package zzz.leyou.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzz.leyou.goods.client.BrandClient;
import zzz.leyou.goods.client.CategoryClient;
import zzz.leyou.goods.client.GoodsClient;
import zzz.leyou.goods.client.SpecificationClient;
import zzz.leyou.goods.service.GoodsService;
import zzz.leyou.item.pojo.*;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecificationClient specificationClient;

    @Override
    public Map<String, Object> loadData(Long spuId) {
        Map<String,Object> model = new HashMap<>();
        //根据spuId查询spu
        Spu spu = this.goodsClient.querySpuById(spuId);
        //查询spuDetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);

        //查询分类：Map<String,Object>categories
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = this.categoryClient.queryNamesByIds(cids);
        //初始化为分类map
        List<Map<String,Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",cids.get(i));
            map.put("name",names.get(i));
            categories.add(map);
        }
        //查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());
        //查询skus
        List<Sku> skus = this.goodsClient.querySkuBySpuId(spuId);
        //查询参数组
        List<SpecGroup> groups = this.specificationClient.queryGroupsWithParam(spu.getCid3());
        //查询特殊规格参数
        List<SpecParam> params = this.specificationClient.queryParamsByGid(null, spu.getCid3(), false, null);
        //初始化params特殊规格参数得map
        Map<Long, String> paramMap = new HashMap<>();
        params.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });
        model.put("spu",spu);
        model.put("spuDetail",spuDetail);
        model.put("categories",categories);
        model.put("brand",brand);
        model.put("skus",skus);
        model.put("groups",groups);
        model.put("paramMap",paramMap);
        return model;
    }
}
