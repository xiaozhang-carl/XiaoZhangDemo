package com.test.jiashiapi;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-21 Time: 10:54
 * ToDo:
 */
public class UserInfoBean {
    /**
     * user_id : 2164
     * email :
     * head : Uploads/head/1453273847.png
     * user_name : 15986302511
     * mobile_phone : 15986302511
     * user_type : 普通会员
     * user_rank : 铜牌
     * sex : 1
     * birthday : 0000-00-00
     * true_name : 15986302511
     * enablePoint : 7471
     * enableValue : -6176.00
     * order_unpaid : 28
     */

    private InfoEntity info;
    /**
     * info : {"user_id":"2164","email":"","head":"Uploads/head/1453273847.png","user_name":"15986302511","mobile_phone":"15986302511","user_type":"普通会员","user_rank":"铜牌","sex":"1","birthday":"0000-00-00","true_name":"15986302511","enablePoint":"7471","enableValue":"-6176.00","order_unpaid":"28"}
     * status : 1
     * url :
     */

    private int status;
    private String url;

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public int getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public static class InfoEntity {
        private String user_id;
        private String email;
        private String head;
        private String user_name;
        private String mobile_phone;
        private String user_type;
        private String user_rank;
        private String sex;
        private String birthday;
        private String true_name;
        private String enablePoint;
        private String enableValue;
        private String order_unpaid;

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setMobile_phone(String mobile_phone) {
            this.mobile_phone = mobile_phone;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public void setUser_rank(String user_rank) {
            this.user_rank = user_rank;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setTrue_name(String true_name) {
            this.true_name = true_name;
        }

        public void setEnablePoint(String enablePoint) {
            this.enablePoint = enablePoint;
        }

        public void setEnableValue(String enableValue) {
            this.enableValue = enableValue;
        }

        public void setOrder_unpaid(String order_unpaid) {
            this.order_unpaid = order_unpaid;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getEmail() {
            return email;
        }

        public String getHead() {
            return head;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getMobile_phone() {
            return mobile_phone;
        }

        public String getUser_type() {
            return user_type;
        }

        public String getUser_rank() {
            return user_rank;
        }

        public String getSex() {
            return sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getTrue_name() {
            return true_name;
        }

        public String getEnablePoint() {
            return enablePoint;
        }

        public String getEnableValue() {
            return enableValue;
        }

        public String getOrder_unpaid() {
            return order_unpaid;
        }
    }
}
