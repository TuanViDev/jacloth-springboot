package com.vanlang.webbanquanao.Service;

import com.vanlang.webbanquanao.Model.User;
import com.vanlang.webbanquanao.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public User findUserById(long id)
    {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(long id)
    {
        userRepository.deleteById(id);
    }

    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }
}
