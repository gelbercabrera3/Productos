package org.gelbercabrera.ferreteria.entities;

import java.util.Date;
import java.util.Map;

public class Post {
    Date date;
    String email_poster;
    String name_poster;
    String name;
    String descripction;
    String price;
    String urlImage;
    Integer likesNum;
    Map<String, Boolean> likes;

    public Post() {
    }

    public Post(Date date, String email_poster, String name_poster
            , String content, Integer likesNum, Map<String, Boolean> likes) {
        this.date = date;
        this.email_poster = email_poster;
        this.name_poster = name_poster;
        this.likesNum = likesNum;
        this.likes = likes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail_poster() {
        return email_poster;
    }

    public void setEmail_poster(String email_poster) {
        this.email_poster = email_poster;
    }

    public String getName_poster() {
        return name_poster;
    }

    public void setName_poster(String name_poster) {
        this.name_poster = name_poster;
    }

    public Integer getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(Integer likesNum) {
        this.likesNum = likesNum;
    }

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripction() {
        return descripction;
    }

    public void setDescripction(String descripction) {
        this.descripction = descripction;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
