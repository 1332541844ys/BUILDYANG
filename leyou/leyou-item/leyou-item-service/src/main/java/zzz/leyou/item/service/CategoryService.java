package zzz.leyou.item.service;

import zzz.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {
    //查询根据id
    List<Category> queryCategoriesByPid(Long pid);
    //根据多个分类id查询所有分类
    public List<String> queryNamesByIds(List<Long> ids);
}
