package us.uplaw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.uplaw.exception.ResourceNotFoundException;
import us.uplaw.model.User;
import us.uplaw.payload.PagedResponse;
import us.uplaw.payload.PollResponse;
import us.uplaw.payload.UserIdentityAvailability;
import us.uplaw.payload.UserProfile;
import us.uplaw.payload.UserSummary;
import us.uplaw.repository.PollRepository;
import us.uplaw.repository.UserRepository;
import us.uplaw.repository.VoteRepository;
import us.uplaw.security.CurrentUser;
import us.uplaw.security.UserPrincipal;
import us.uplaw.service.PollService;
import us.uplaw.util.AppConstants;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PollRepository pollRepository;

  @Autowired
  private VoteRepository voteRepository;

  @Autowired
  private PollService pollService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    return userSummary;
  }

  @GetMapping("/checkUsernameAvailability")
  public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
    Boolean isAvailable = !userRepository.existsByUsername(username);
    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping("/checkEmailAvailability")
  public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
    Boolean isAvailable = !userRepository.existsByEmail(email);
    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping("/{username}")
  public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    long pollCount = pollRepository.countByCreatedBy(user.getId());
    long voteCount = voteRepository.countByUserId(user.getId());

    UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

    return userProfile;
  }

  @GetMapping("/{username}/polls")
  public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return pollService.getPollsCreatedBy(username, currentUser, page, size);
  }

  @GetMapping("/{username}/votes")
  public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                     @CurrentUser UserPrincipal currentUser,
                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return pollService.getPollsVotedBy(username, currentUser, page, size);
  }

}
