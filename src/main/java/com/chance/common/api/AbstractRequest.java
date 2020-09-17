package com.chance.common.api;

import java.io.Serializable;

/**
 * @Description: 请求参数抽象实体，Controller与RPC接口实现类之间调用传递参数实体类的抽象父类，所有通过Controller调用RPC接口参数封装必须继承此类
 * @Author: chance
 * @Date: 2020-09-17 14:06
 * @Version 1.0
 */
public abstract class AbstractRequest implements Serializable {

    /**
     * 品牌编码
     */
    protected String brandCode;

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }
}
