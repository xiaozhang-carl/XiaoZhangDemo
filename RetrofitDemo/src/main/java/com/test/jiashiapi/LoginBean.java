package com.test.jiashiapi;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-20 Time: 10:23
 * ToDo:
 */
public class LoginBean {
    /**
     * session_id : rhf1453256243
     * user_name : 15986302511
     * head : Uploads/head/1445847031.jpg
     * login_type : phone
     */

    private InfoEntity info;
    /**
     * info : {"session_id":"rhf1453256243","user_name":"15986302511","head":"Uploads/head/1445847031.jpg","login_type":"phone"}
     * status : 1
     * url :
     */

    private int status;
    private String url;


    public void setInfo(InfoEntity info) { this.info = info;}


    public void setStatus(int status) { this.status = status;}


    public void setUrl(String url) { this.url = url;}


    public InfoEntity getInfo() { return info;}


    public int getStatus() { return status;}


    public String getUrl() { return url;}


    public static class InfoEntity {
        private String session_id;
        private String user_name;
        private String head;
        private String login_type;


        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }


        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }


        public void setHead(String head) { this.head = head;}


        public void setLogin_type(String login_type) {
            this.login_type = login_type;
        }


        public String getSession_id() { return session_id;}


        public String getUser_name() { return user_name;}


        public String getHead() { return head;}


        public String getLogin_type() { return login_type;}
    }
}
