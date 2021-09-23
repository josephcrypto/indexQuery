package cn.coding.com.indexquery.encryption.bean;

import cn.coding.com.indexquery.encryption.enums.EncryptBodyMethod;
import cn.coding.com.indexquery.encryption.enums.SHAEncryptType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <p>加密注解信息</p>
 * @author licoy.cn
 * @version 2018/9/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncryptAnnotationInfoBean {

    private EncryptBodyMethod encryptBodyMethod;

    private String key;

    private SHAEncryptType shaEncryptType;

}
