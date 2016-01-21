package com.test.githubapi;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-18 Time: 10:18
 * ToDo:
 */
public class SomeResponse {

    /**
     * message : Not Found
     * documentation_url : https://developer.github.com/v3
     */

    private String message;
    private String documentation_url;


    public void setMessage(String message) { this.message = message;}


    public void setDocumentation_url(String documentation_url) {
        this.documentation_url = documentation_url;
    }


    public String getMessage() { return message;}


    public String getDocumentation_url() { return documentation_url;}


    @Override public String toString() {
        return "SomeResponse{" +
                "message='" + message + '\'' +
                ", documentation_url='" + documentation_url + '\'' +
                '}';
    }
}
