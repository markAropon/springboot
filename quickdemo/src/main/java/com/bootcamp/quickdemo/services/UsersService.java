
package com.bootcamp.quickdemo.services;

import com.bootcamp.quickdemo.dto.UserDTO;
import java.util.List;

public interface UsersService {
	UserDTO createUser(UserDTO userDto);
	UserDTO getUserById(Long id);
	List<UserDTO> getAllUsers();
	UserDTO updateUser(Long id, UserDTO userDto);
	void deleteUser(Long id);
}