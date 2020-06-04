package zzz.leyou.item.service;

import zzz.leyou.common.pojo.PageResult;
import zzz.leyou.item.pojo.Brand;

import java.util.List;


public interface BrandService {
    //根据查询条件分页并排序查询品牌信息
    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);
    //新增品牌
    void saveBrand(Brand brand, List<Long> cids);
    //查询品牌
    List<Brand> queryBrandsByCid(Long cid);

    Brand queryBrandById(Long id);
}
