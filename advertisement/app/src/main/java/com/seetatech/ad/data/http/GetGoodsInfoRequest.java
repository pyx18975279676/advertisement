package com.seetatech.ad.data.http;

import com.alibaba.fastjson.JSON;
import com.seetatech.ad.setting.AppSetting;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xjh on 18-7-11.
 */

public class GetGoodsInfoRequest extends BaseRequest<GetGoodsInfoResponse> {
    private String counter_code;
    private Set<String> product_codes;

    public String getCounter_code() {
        return counter_code;
    }

    public void setCounter_code(String counter_code) {
        this.counter_code = counter_code;
    }

    public Set<String> getProduct_codes() {
        return product_codes;
    }

    public void setProduct_codes(Set<String> product_codes) {
        this.product_codes = product_codes;
    }

    @Override
    protected String getUrl() {
        return AppSetting.getShopServerAddress() + "/devend/product/get";
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("counter_code", counter_code);
        params.put("product_codes", JSON.toJSONString(product_codes));
        return params;
    }
}
