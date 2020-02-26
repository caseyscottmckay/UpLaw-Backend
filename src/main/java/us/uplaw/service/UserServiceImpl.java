package us.uplaw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.uplaw.exception.RestException;
import us.uplaw.exception.ServiceException;
import us.uplaw.model.User;
import us.uplaw.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User createUser(final User user) {
    return userRepository.save(user);
  }

  @Override
  public User getById(final Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new RestException(ServiceException.USER_NOT_FOUND);
    }
    return userRepository.getOne(userId);
  }

  @Override
  public Optional<User> getByEmail(final String emailAddress) {
    return userRepository.findUserByEmail(emailAddress);
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public User updateUser(final User user) {
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(final Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new RestException(ServiceException.USER_NOT_FOUND);
    }
    userRepository.deleteById(userId);
  }

}
