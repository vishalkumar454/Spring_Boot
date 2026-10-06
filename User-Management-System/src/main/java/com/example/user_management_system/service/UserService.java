package com.example.user_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user_management_system.dto.UserUpdate;
import com.example.user_management_system.entity.User;
import com.example.user_management_system.exception.InvalidAgeException;
import com.example.user_management_system.exception.UserNotFoundException;
import com.example.user_management_system.repository.UserRepository;
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		if(user.getAge() < 18) {
			throw new InvalidAgeException("age is less than 18");
		}
		return userRepository.save(user);
	}
	
	public User findUser(long id) {
		Optional<User> optional = userRepository.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new UserNotFoundException("user with id " + id + " is not found");
	}
	
	public List<User> findAllUser(){
		return userRepository.findAll();
	}
	
	public User updateUser(long id, UserUpdate userUpdate) {
		User user = findUser(id);
		user.setAge(userUpdate.getAge());
		user.setEmail(userUpdate.getEmail());
		user.setName(userUpdate.getName());
		user.setPassword(userUpdate.getPassword());
		
		return userRepository.save(user);
		
	}
	
	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}
}
