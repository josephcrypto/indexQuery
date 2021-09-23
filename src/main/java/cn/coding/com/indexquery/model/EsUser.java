package cn.coding.com.indexquery.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "logviewer_user")
public class EsUser {

    @Id
    private String id;
    @Field(name = "user_name", type = FieldType.Text)
    private String userName;
    private Integer enable;
    private String password;

}
