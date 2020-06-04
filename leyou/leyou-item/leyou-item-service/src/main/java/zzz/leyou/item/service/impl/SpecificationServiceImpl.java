package zzz.leyou.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzz.leyou.item.mapper.SpecGroupMapper;
import zzz.leyou.item.mapper.SpecParamMapper;
import zzz.leyou.item.pojo.SpecGroup;
import zzz.leyou.item.pojo.SpecParam;
import zzz.leyou.item.service.SpecificationService;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecGroupMapper groupMapper;
    @Autowired
    private SpecParamMapper paramMapper;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup record = new SpecGroup();
        record.setCid(cid);
        return this.groupMapper.select(record);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
    @Override
    public List<SpecParam> queryParamsByGid(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.paramMapper.select(record);
    }
    //保存数据
    @Transactional
    @Override
    public void saveParams(SpecParam specParam) {
        this.paramMapper.insertSelective(specParam);
    }
    //修改
    @Transactional
    @Override
    public void updateParams(SpecParam specParam) {
        this.paramMapper.updateByPrimaryKeySelective(specParam);
    }
    //删除
    @Transactional
    @Override
    public void deleteParams(Long gid) {
        this.paramMapper.deleteByPrimaryKey(gid);

    }

    @Override
    public List<SpecGroup> queryGroupsWithParam(Long cid) {
        List<SpecGroup> groups = this.queryGroupsByCid(cid);
        groups.forEach(group->{
            List<SpecParam> params = this.queryParamsByGid(group.getId(), null, null, null);
            group.setParams(params);
        });
        return groups;
    }
}
