package com.test.jiashiapi;

import java.util.List;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-20 Time: 11:05
 * ToDo:
 */
public class Cat {
    /**
     * page : 1
     * page_count : 7
     * records : 40
     */

    private PagerEntity pager;
    /**
     * cat_id : 0
     * cat_name : 全部
     */

    private List<AttrInfoEntity> attr_info;
    /**
     * goods_id : 81
     * goods_name : 【家洁仕】无忧保洁A1
     * goods_price : 119.00
     * market_price : 119.00/次
     * goods_type : 3
     * cat_id : 49
     * description : 80平米以下,3小时/次/人，适用家庭日常保洁
     * goods_unit : /次
     * price : 119.00/次
     * buynum : 18414
     * thumb_img : Uploads/Album/20150606/thumb_img/201506061038206273.jpg
     */

    private List<GoodsListEntity> goods_list;


    public void setPager(PagerEntity pager) { this.pager = pager;}


    public void setAttr_info(List<AttrInfoEntity> attr_info) {
        this.attr_info = attr_info;
    }


    public void setGoods_list(List<GoodsListEntity> goods_list) {
        this.goods_list = goods_list;
    }


    public PagerEntity getPager() { return pager;}


    public List<AttrInfoEntity> getAttr_info() { return attr_info;}


    public List<GoodsListEntity> getGoods_list() { return goods_list;}


    public static class PagerEntity {
        private int page;
        private int page_count;
        private String records;


        public void setPage(int page) { this.page = page;}


        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }


        public void setRecords(String records) { this.records = records;}


        public int getPage() { return page;}


        public int getPage_count() { return page_count;}


        public String getRecords() { return records;}
    }

    public static class AttrInfoEntity {
        private int cat_id;
        private String cat_name;


        public void setCat_id(int cat_id) { this.cat_id = cat_id;}


        public void setCat_name(String cat_name) { this.cat_name = cat_name;}


        public int getCat_id() { return cat_id;}


        public String getCat_name() { return cat_name;}
    }

    public static class GoodsListEntity {
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String market_price;
        private String goods_type;
        private String cat_id;
        private String description;
        private String goods_unit;
        private String price;
        private int buynum;
        private String thumb_img;


        public void setGoods_id(String goods_id) { this.goods_id = goods_id;}


        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }


        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }


        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }


        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }


        public void setCat_id(String cat_id) { this.cat_id = cat_id;}


        public void setDescription(String description) {
            this.description = description;
        }


        public void setGoods_unit(String goods_unit) {
            this.goods_unit = goods_unit;
        }


        public void setPrice(String price) { this.price = price;}


        public void setBuynum(int buynum) { this.buynum = buynum;}


        public void setThumb_img(String thumb_img) {
            this.thumb_img = thumb_img;
        }


        public String getGoods_id() { return goods_id;}


        public String getGoods_name() { return goods_name;}


        public String getGoods_price() { return goods_price;}


        public String getMarket_price() { return market_price;}


        public String getGoods_type() { return goods_type;}


        public String getCat_id() { return cat_id;}


        public String getDescription() { return description;}


        public String getGoods_unit() { return goods_unit;}


        public String getPrice() { return price;}


        public int getBuynum() { return buynum;}


        public String getThumb_img() { return thumb_img;}
    }
}
