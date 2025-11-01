package com.rishi.rpf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rishi.rpf.entity.User;
import com.rishi.rpf.services.CustomUserRepository;

@Repository
public interface UserRepository extends JpaRepository<User, String>,CustomUserRepository  {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    // List<User> findByStateContainingAndCityContainingAndAreaContainingAndCollegeContainingAndReligionContainingAndOccupationContainingAndGenderContainingAndAgeAndFoodPreferenceContaining(
    //     String state, String city, String area, String college, String religion, String occupation, String gender, int age, String foodPreference
    // );
    // extra method db relatedoperations
    // custom query methods
    // custom finder methods

  //  Optional<User> findByEmailToken(String id);

}
