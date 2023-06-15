package com.example.demo.model.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.Date;
import java.sql.SQLException;


@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String logo;

    private String text;

    private Blob img;

//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Post() {
    }

    public Post(String logo, String text, Blob img, User user) {
        this.logo = logo;
        this.text = text;
        this.img = img;
        this.user = user;
    }

    public Post(String logo, String text, String img, User user) throws SQLException {
        this.logo = logo;
        this.text = text;
        this.user = user;
        byte[] byteData = img.getBytes();
        this.img = new SerialBlob(byteData);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getText() {
        return text;
    }

    public void setImg(String img) throws SQLException {
        byte[] byteData = img.getBytes();
        this.img = new SerialBlob(byteData);
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
}
