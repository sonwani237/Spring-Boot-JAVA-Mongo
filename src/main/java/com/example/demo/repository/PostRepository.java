package com.example.demo.repository;

import com.example.demo.dal.PostDAL;
import com.example.demo.model.Post;
import com.example.demo.util.CustomAggregationOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;


@Repository
public class PostRepository implements PostDAL {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Post> getAllPosts(String userId) {
        String postQuery = " { $lookup: { let:{ productObjId: { '$toObjectId': '$productId' } }, from: 'product', " +
                "pipeline: [ { $match: { $expr: { $eq: [ '$_id', '$$productObjId' ] } } } ], as: 'products' } }," ;

        String userQuery = " { $lookup: { let:{ userObjId: { '$toObjectId': '$userId' } }, from: 'user', " +
                "pipeline: [ { $match: { $expr: { $eq: [ '$_id', '$$userObjId' ] } } } ], as: 'user' } }" ;

        String project = "{'$project': {postName: '$postName', postDescription: '$postDescription', productId: '$productId', " +
                "userId: '$userId', status: '$status', createdOn: '$createdOn',updatedOn: '$updatedOn',products: '$products',likedBy: " +
                "'$likedBy',user: '$user', likedCount: {'$size': '$likedBy'}," +
                " isLiked: {'$cond': { if: {'$in': ['"+userId+"','$likedBy']}, then: 'true', else: 'false'}}, " +
                " isFavourite: {'$cond': { if: {'$in': ['"+userId+"','$products.favouriteBy']}, then: 'true', else: 'false'}}}}";

        TypedAggregation<Post> aggregation = Aggregation.newAggregation(Post.class, new CustomAggregationOperation(postQuery),
                unwind("products"),
                match(Criteria.where("status").is(1)),
                new CustomAggregationOperation(userQuery),
                unwind("user"),
                new CustomAggregationOperation(project)
        );

        AggregationResults<Post> results = mongoTemplate.aggregate(aggregation, Post.class);

        return results.getMappedResults();
    }

    @Override
    public List<Post> getPosts(String userId) {
        String postQuery = " { $lookup: { let:{ productObjId: { '$toObjectId': '$productId' } }, from: 'product', " +
                "pipeline: [ { $match: { $expr: { $eq: [ '$_id', '$$productObjId' ] } } } ], as: 'products' } }," ;

        String userQuery = " { $lookup: { let:{ userObjId: { '$toObjectId': '$userId' } }, from: 'user', " +
                "pipeline: [ { $match: { $expr: { $eq: [ '$_id', '$$userObjId' ] } } } ], as: 'user' } }" ;

        String project = "{'$project': {postName: '$postName', postDescription: '$postDescription', productId: '$productId', " +
                "userId: '$userId', status: '$status', createdOn: '$createdOn',updatedOn: '$updatedOn',products: '$products',likedBy: " +
                "'$likedBy',user: '$user', likedCount: {'$size': '$likedBy'}," +
                " isLiked: {'$cond': { if: {'$in': ['"+userId+"','$likedBy']}, then: 'true', else: 'false'}}, " +
                " isFavourite: {'$cond': { if: {'$in': ['"+userId+"','$products.favouriteBy']}, then: 'true', else: 'false'}}}}";

        TypedAggregation<Post> aggregation = Aggregation.newAggregation(Post.class, new CustomAggregationOperation(postQuery),
                unwind("products"),
                match(Criteria.where("userId").is(userId)),
                match(Criteria.where("status").is(1)),
                new CustomAggregationOperation(userQuery),
                unwind("user"),
                new CustomAggregationOperation(project)
        );

        AggregationResults<Post> results = mongoTemplate.aggregate(aggregation, Post.class);

        return results.getMappedResults();
    }

    @Override
    public Post addPost(Post post) {
        return mongoTemplate.save(post);
    }

    @Override
    public Post likePost(String userId, String postId) {
        Query query = new Query();
        query.addCriteria(where("_id").is(postId));
        Update update = new Update();
        update.addToSet("likedBy", userId);
        update.set("updatedOn", new Date());
        return mongoTemplate.findAndModify(query, update, Post.class);
    }

    @Override
    public Post disLikePost(String userId, String postId) {
        Query query = new Query();
        query.addCriteria(where("_id").is(postId));
        Update update = new Update();
        update.pull("likedBy", userId);
        update.set("updatedOn", new Date());
        return mongoTemplate.findAndModify(query, update, Post.class);
    }

}
