package com.example.demo.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "product")
public class Product {

    @Id
    private String id;

    @NotBlank
    @Size(max = 200)
    private String productName;

    @NotBlank
    @Size(max = 200)
    private String productDescription;

    @NotBlank
    @Size(max = 200)
    private String amount;

    private Set<String> favouriteBy = new HashSet<>();

    @NotBlank
    @Size(max = 20)
    private String userId;

    @NotBlank
    @Size(max = 10)
    private int status = 1;

    @CreatedDate
    @Size(max = 120)
    private Date createdOn = new Date();

    @LastModifiedDate
    @Size(max = 120)
    private Date updatedOn = new Date();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Set<String> getFavouriteBy() {
        return favouriteBy;
    }

    public void setFavouriteBy(Set<String> favouriteBy) {
        this.favouriteBy = favouriteBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
