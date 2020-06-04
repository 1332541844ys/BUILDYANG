package zzz.leyou.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import zzz.leyou.search.pojo.Goods;

public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
