package com.seetatech.ad.data.http;

import java.util.List;

/**
 * 获取商品详情返回结果
 * Created by hexuren on 18-5-9.
 */

public class GetGoodsInfoResponse {
    private int res;//结果
    private List<Product> records;//商品信息

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public List<Product> getRecords() {
        return records;
    }

    public void setRecords(List<Product> records) {
        this.records = records;
    }

    public static class Product {
        private String product_id;//商品id
        private String product_name;//商品名称
        private String product_code;//商品编码
        private String product_classify;//分类名
        private String product_price;//价格（单位：分）
        private String weight;//重量（单位：克）
        private String is_by_weight;//是否称重
        private String image_url;//图片url
        private String comment;//备注信息
        private String create_time;//创建时间

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_code() {
            return product_code;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        public String getProduct_classify() {
            return product_classify;
        }

        public void setProduct_classify(String product_classify) {
            this.product_classify = product_classify;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getIs_by_weight() {
            return is_by_weight;
        }

        public void setIs_by_weight(String is_by_weight) {
            this.is_by_weight = is_by_weight;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
