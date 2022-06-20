package com.example.demo.repository;

import com.example.demo.dal.ProductDAL;
import com.example.demo.model.FavouriteProduct;
import com.example.demo.model.Product;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ProductRepository implements ProductDAL {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Product> getAllProducts() {
        Query query = new Query();
        query.addCriteria(where("status").is(1));
        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> getProducts(String userId) {
        Query query = new Query();
        query.addCriteria(where("status").is(1));
        query.addCriteria(where("userId").is(userId));
        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public Product addProduct(Product product) {
        return mongoTemplate.save(product);
    }

    @Override
    public FavouriteProduct addFavourite(FavouriteProduct likedProduct) {
        return mongoTemplate.save(likedProduct);
    }

    @Override
    public FavouriteProduct addRemoveFavourite(String id, int status) {
        Query query = new Query();
        query.addCriteria(where("_id").is(id));
        Update update = new Update();
        update.set("status", status);
        return mongoTemplate.findAndModify(query, update, FavouriteProduct.class);
    }

    @Override
    public FavouriteProduct removeFavourite(String id) {
        Query query = new Query();
        query.addCriteria(where("_id").is(id));
        Update update = new Update();
        update.set("status", 0);
        return mongoTemplate.findAndModify(query, update, FavouriteProduct.class);
    }

    @Override
    public List<FavouriteProduct> getFavourites(String userId) {
        Aggregation agg = newAggregation(l -> new Document("$lookup", new Document("from", "product")
                        .append("let", new Document("productObjId", new Document("$toObjectId", "$productId")))
                        .append("pipeline", Collections.singletonList(new Document("$match", new Document("$expr", new Document("$eq", Arrays.asList("$_id", "$$productObjId"))))))
                        .append("as", "products")
                ),
                match(where("userId").is(userId)),
                match(where("status").is(1)),
                unwind("products", Boolean.FALSE)
        );
        AggregationResults<FavouriteProduct> results = mongoTemplate.aggregate(agg, "favourite_product", FavouriteProduct.class);
        return results.getMappedResults();
    }

    @Override
    public FavouriteProduct getFavouriteProduct(String id, String userId) {
        Query query = new Query();
        query.addCriteria(where("userId").is(userId));
        query.addCriteria(where("productId").is(id));
        return mongoTemplate.findOne(query, FavouriteProduct.class);
    }

}
