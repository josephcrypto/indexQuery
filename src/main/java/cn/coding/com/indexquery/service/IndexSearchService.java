package cn.coding.com.indexquery.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class IndexSearchService {

    //private static final String BET_RECORDS_INDEX = "bet_records01 to bet_records05"
    public static final String BET_RECORDS_INDEX = "bets-01";

    private ElasticsearchOperations elasticsearchOperations;

    public IndexSearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<JSONObject> fetchSuggestions(String query) {
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("user", query+ "*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0,5))
                .build();

        SearchHits<JSONObject> searchSuggestions = elasticsearchOperations.search(searchQuery,JSONObject.class,
                IndexCoordinates.of(BET_RECORDS_INDEX));

        List<JSONObject> suggestions = new ArrayList<JSONObject>();

        searchSuggestions.getSearchHits().forEach(searchHit ->{
            suggestions.add(searchHit.getContent());
        });
        return suggestions;
    }

    public JSONObject fetchAll(String query, int page, int size) {
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("message_copy", query + "*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(page, size))
                .withSort(SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC))
                .withTrackTotalHits(true)
                .build();

        SearchHits<JSONObject> searchHits = elasticsearchOperations.search(searchQuery, JSONObject.class,
                IndexCoordinates.of(BET_RECORDS_INDEX));

        JSONObject responseObject = new JSONObject();
        List<JSONObject> lists = new ArrayList<>();

        searchHits.getSearchHits().forEach(searchHit -> {
            JSONObject jsonObject = searchHit.getContent();
            jsonObject.put("id", searchHit.getId());
            lists.add(jsonObject);
        });

        responseObject.put("total", searchHits.getTotalHits());
        responseObject.put("datas", lists);

        return responseObject;
    }

    public JSONObject getById(String id) {
        Query searchQuery = new StringQuery(
                "{\"match\":{\"_id\":{\"query\":\""+ id + "\"}}}\""
        );

        SearchHits<JSONObject> searchHits = elasticsearchOperations.search(searchQuery, JSONObject.class,
                IndexCoordinates.of(BET_RECORDS_INDEX));
        JSONObject jsonObject = searchHits.getSearchHits().get(0).getContent();

        JSONObject responseObj = JSONObject.parseObject(jsonObject.getString("message_copy"));
        responseObj.put("id", searchHits.getSearchHits().get(0).getId());
        return responseObj;
    }

    public JSONObject searchQuery(String userid, String tableno, String xuehao, String juhao, String username, String bid, int page, int size){
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        if(!userid.isEmpty()) {
            builder.must(QueryBuilders.matchQuery("userid", userid));
        }
        if(!tableno.isEmpty()){
            builder.must(QueryBuilders.matchQuery("tableno", tableno));
        }
        if (!xuehao.isEmpty()){
            builder.must(QueryBuilders.matchQuery("data.xuehao", xuehao));
        }
        if (!juhao.isEmpty()){
            builder.must(QueryBuilders.matchQuery("data.juhao", juhao));
        }
        if (!username.isEmpty()){
            builder.must(QueryBuilders.matchQuery("username", username));
        }
        if (!bid.isEmpty()){
            builder.must(QueryBuilders.matchQuery("data.bid", bid));
        }

 //       		builder.must(QueryBuilders.rangeQuery("timestamp")
//				.gte("2021-09-17 00:00:00")
//				.lte("2021-09-18 23:59:59").format("yyyy-MM-dd HH:mm:ss")
//				.includeLower(false).includeUpper(false));
        FieldSortBuilder sort = SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC);
        PageRequest pageRequest = PageRequest.of(page, size);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(builder);
        nativeSearchQueryBuilder.withPageable(pageRequest);
        nativeSearchQueryBuilder.withSort(sort);
        nativeSearchQueryBuilder.withTrackTotalHits(true);
        Query query = nativeSearchQueryBuilder.build();

        SearchHits<JSONObject> searchHits = elasticsearchOperations.search(query, JSONObject.class, IndexCoordinates.of(BET_RECORDS_INDEX));

        JSONObject responseObj = new JSONObject();
        List<JSONObject> list = new ArrayList<>();

        searchHits.getSearchHits().forEach(searchHit ->{
            JSONObject jsonObject = searchHit.getContent();
            jsonObject.put("id", searchHit.getId());
            list.add(JSONObject.parseObject(jsonObject.getString("message_copy")));
        });

        responseObj.put("total", searchHits.getTotalHits());
        responseObj.put("datas", list);

        return responseObj;
    }
}
