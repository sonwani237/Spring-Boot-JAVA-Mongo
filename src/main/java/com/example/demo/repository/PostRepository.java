package com.example.demo.repository;

import com.example.demo.dal.PostDAL;
import com.example.demo.model.Post;
import com.example.demo.util.CustomAggregationOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Repository
public class PostRepository implements PostDAL {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;


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

}
