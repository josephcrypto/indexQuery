package cn.coding.com.indexquery.encryption.annotation.encrypt;

import cn.coding.com.indexquery.encryption.enums.SHAEncryptType;

import java.lang.annotation.*;

/**
 * @author licoy.cn
 * @version 2018/9/4
 * @see EncryptBody
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SHAEncryptBody {

    SHAEncryptType value() default SHAEncryptType.SHA256;

}
