package com.project.jw.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Videos{
    @Id
    @GeneratedValue
    private Long video_id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String url;

    @JsonProperty
    private String downloadUrl;

    @JsonProperty
    private boolean status;

    @JsonProperty
    private String description;


    @Enumerated(value = EnumType.ORDINAL)
    private Region region;

    @JsonProperty
    @Enumerated(value = EnumType.ORDINAL)
    private Category category;

    private int price;



    //Setter and Getter
    public boolean isStatus() {
        return status;
    }
    public String getDescription() {
        return description;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Long video_id) {
        this.video_id = video_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    //Relational dan Database
//    @ManyToOne
//    @JoinColumn(name = "id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private User user;
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private  User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @OneToMany
    private List<Transaction> transaction = new ArrayList<>();

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }
}