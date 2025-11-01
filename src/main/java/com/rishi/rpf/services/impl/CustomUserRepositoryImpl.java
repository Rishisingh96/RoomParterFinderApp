package com.rishi.rpf.services.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.services.CustomUserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> filterUsers(String state, String city, String area, String college,
                                  String religion, String occupation, String gender,
                                  Integer age, String foodPreference) {
        
        StringBuilder jpql = new StringBuilder("SELECT DISTINCT u FROM user u JOIN FETCH u.contacts c WHERE 1=1");

        // filter on Contact fields (state, city, area, college, religion, occupation, gender, age, foodType)
        if (state != null && !state.isEmpty()) jpql.append(" AND c.state LIKE :state");
        if (city != null && !city.isEmpty()) jpql.append(" AND c.city LIKE :city");
        if (area != null && !area.isEmpty()) jpql.append(" AND c.area LIKE :area");
        if (college != null && !college.isEmpty()) jpql.append(" AND c.college LIKE :college");
        if (religion != null && !religion.isEmpty()) jpql.append(" AND c.religion LIKE :religion");
        if (occupation != null && !occupation.isEmpty()) jpql.append(" AND c.occupation LIKE :occupation");
        if (gender != null && !gender.isEmpty()) jpql.append(" AND c.gender LIKE :gender");
        if (age != null) jpql.append(" AND c.age = :age");
        if (foodPreference != null && !foodPreference.isEmpty()) jpql.append(" AND c.foodType LIKE :foodPreference");

        TypedQuery<User> query = entityManager.createQuery(jpql.toString(), User.class);

        if (state != null && !state.isEmpty()) query.setParameter("state", "%" + state + "%");
        if (city != null && !city.isEmpty()) query.setParameter("city", "%" + city + "%");
        if (area != null && !area.isEmpty()) query.setParameter("area", "%" + area + "%");
        if (college != null && !college.isEmpty()) query.setParameter("college", "%" + college + "%");
        if (religion != null && !religion.isEmpty()) query.setParameter("religion", "%" + religion + "%");
        if (occupation != null && !occupation.isEmpty()) query.setParameter("occupation", "%" + occupation + "%");
        if (gender != null && !gender.isEmpty()) query.setParameter("gender", "%" + gender + "%");
        if (age != null) query.setParameter("age", age);
        if (foodPreference != null && !foodPreference.isEmpty()) query.setParameter("foodPreference", "%" + foodPreference + "%");

        return query.getResultList();
    }
}
