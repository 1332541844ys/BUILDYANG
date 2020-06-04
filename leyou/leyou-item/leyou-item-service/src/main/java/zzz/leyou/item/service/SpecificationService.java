package zzz.leyou.item.service;

import zzz.leyou.item.pojo.SpecGroup;
import zzz.leyou.item.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {
    //根据分类id查询参数组
    List<SpecGroup> queryGroupsByCid(Long cid);

    List<SpecParam> queryParamsByGid(Long gid,Long cid,Boolean generic,Boolean searching);
    //保存参数
    void saveParams(SpecParam specParam);
    //修改
    void updateParams(SpecParam specParam);
    //删除
    void deleteParams(Long gid);

    List<SpecGroup> queryGroupsWithParam(Long cid);
}
