package com.fsx.repository;

import com.fsx.enums.AccountStatus;
import com.fsx.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);


    Page<User> findByAccountStatus(AccountStatus status, Pageable pageable);

    @Query("""
    SELECT u 
    FROM User u 
    WHERE u.accountStatus = :status 
    AND LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))""")
    Page<User> searchUsers(AccountStatus status, String keyword, Pageable pageable);
}
