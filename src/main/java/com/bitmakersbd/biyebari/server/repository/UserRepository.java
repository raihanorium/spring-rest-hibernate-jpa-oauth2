package com.bitmakersbd.biyebari.server.repository;


import com.bitmakersbd.biyebari.server.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findOneByEmailAndPassword(String email, String password);

    public User findOneByContactNoAndPassword(String contactNo, String password);

    public User findOneByIdAndPassword(Long id, String password);

    public User findOneByEmail(String email);

    public User findOneByContactNo(String contactNo);

    @Query(value = "select u from User u where u.firstName like %?1% or u.lastName like %?1%")
    public Page<User> searchUsers(String keyword, Pageable pageable);
}
