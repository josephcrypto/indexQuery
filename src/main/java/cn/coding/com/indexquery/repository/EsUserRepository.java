package cn.coding.com.indexquery.repository;

import cn.coding.com.indexquery.model.EsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsUserRepository extends ElasticsearchRepository<EsUser, String> {

    EsUser findFirstByUserName(String username);
    EsUser findFirstById(String id);
}
