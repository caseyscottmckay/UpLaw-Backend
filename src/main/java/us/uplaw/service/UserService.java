package us.uplaw.service;

import java.util.List;
import java.util.Optional;

import us.uplaw.model.User;

public interface UserService {

  User createUser(User user);

  User getById(Long userId);

  Optional<User> getByEmail(String emailAddress);

  List<User> getUsers();

  User updateUser(User user);

  void deleteUser(Long userId);

}
