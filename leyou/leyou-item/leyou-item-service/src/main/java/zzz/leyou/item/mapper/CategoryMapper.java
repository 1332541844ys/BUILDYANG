package zzz.leyou.item.mapper;

import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import zzz.leyou.item.pojo.Category;

public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {
}
