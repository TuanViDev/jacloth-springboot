package com.vanlang.webbanquanao.Repository;


import com.vanlang.webbanquanao.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query(value = "SELECT * FROM users WHERE username=?",nativeQuery = true)
    public User findByUsername(String username);
}
