package cn.coding.com.indexquery.encryption.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>加密数据配置读取类</p>
 * <p>在SpringBoot项目中的application.yml中添加配置信息即可</p>
 * <pre>
 *     encrypt:
 *      body:
 *       aes-key: 12345678 # AES加密秘钥
 *       des-key: 12345678 # DES加密秘钥
 * </pre>
 * @author licoy.cn
 * @version 2018/9/6
 */
//@ConfigurationProperties(prefix = "encrypt.body")
@Configuration
@Data
public class EncryptBodyConfig {

    @Value("${encrypt.body.aes-key}")
    private String aesKey;

    @Value("${encrypt.body.des-key}")
    private String desKey;

    @Value("${encrypt.body.iv-key}")
    private String ivKey;

    private String encoding = "UTF-8";

    public String getIvKey(){
        return ivKey;
    }

    public String getAesKey(){
        return aesKey;
    }

}
