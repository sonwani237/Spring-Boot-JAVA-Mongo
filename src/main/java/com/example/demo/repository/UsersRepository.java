package com.example.demo.repository;

import com.example.demo.dal.UserDAL;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepository implements UserDAL {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<User> getAllUser() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status")
                .is(1));
        return mongoTemplate.find(query, User.class);
    }

}
