package us.uplaw.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.User;
import us.uplaw.exception.RestException;
import us.uplaw.repository.UserRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockTest {

  @InjectMocks UserServiceImpl userService;

  @Mock
  UserRepository userRepository;

  @Test
  public void createUser() {

  }

  @Test
  public void getById() {
    when(userRepository.existsById(1L)).thenReturn(true);
    userService.getById(1L);
    verify(userRepository).getOne(1L);
  }

  @Test(expected = RestException.class)
  public void getByUnknownId() {
    userService.getById(10L);
  }

  @Test
  public void getByName() {

  }


}
