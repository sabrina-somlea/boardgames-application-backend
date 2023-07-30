package ro.ubb.boardgameapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.boardgameapp.converter.UserConverter;
import ro.ubb.boardgameapp.dto.UserDto;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins ="*")
//@CrossOrigin(origins = "http://localhost:4200")

public class UserController {
   @Autowired
   private UserService userService;
   @Autowired
   private UserConverter userConverter;

   //demo
   @GetMapping("/welcome")
   @CrossOrigin(origins = {"*"})
   public ResponseEntity<String> sayHello() {
      return ResponseEntity.ok("Merge!");

   }

   @GetMapping("/users")
   Set<UserDto> getAllUsers() {
      List<User> allUsers = userService.getAllUsers();
      Set<UserDto> userDtos = userConverter.convertEntitiesToDtos(allUsers);
      return userDtos;

   }

   @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
   @CrossOrigin(origins = {"*"})
   UserDto saveUser(@Valid @RequestBody UserDto userDto) {
      User user = userConverter.convertDtoToEntity(userDto);
      User savedUser = userService.saveUser(user);
      return userConverter.convertEntityToDto(savedUser);
   }

   @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
   UserDto updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
      return userConverter.convertEntityToDto(
              userService.updateUser(id,
                      userConverter.convertDtoToEntity(userDto))
      );
   }

   @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
   ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
      userService.deleteUser(id);
      return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping("/check-username")
   @CrossOrigin(origins = {"*"})
   public ResponseEntity<Boolean> usernameExists(@RequestParam String username) {
      boolean usernameExists = userService.findByUsername(username) != null;
      return ResponseEntity.ok(usernameExists);
   }

   @GetMapping("/userLoggedIn")
   @CrossOrigin(origins = {"*"})
   public Optional<User> getUserLoggedIn() {
      return this.userService.getUserLoggedInInfo();
   }

   @GetMapping("/searchUser")
   public Set<UserDto> searchUsers(@RequestParam String searchQuery) {
//      return userService.searchUsers(searchQuery);
      List<User> usersFound = userService.searchUsers(searchQuery);
      Set<UserDto> usersFoundDtos = userConverter.convertEntitiesToDtos(usersFound);
      return usersFoundDtos;
   }

   @PostMapping("/send-friend-request/{username}/{requestedUserId}")
   public void sendFriendRequest(@PathVariable(value= "username") String username,@PathVariable(value= "requestedUserId") UUID requestedUserId) {
      userService.sendFriendRequest(username, requestedUserId);
   }

   @PostMapping("/accept-friend-request/{username}/{acceptedUserId}")
   public void acceptFriendRequest(@PathVariable(value= "username") String username,@PathVariable(value= "acceptedUserId") UUID acceptedUserId) {
      userService.acceptFriendRequest(username ,acceptedUserId);
   }

//   @DeleteMapping("/{username}/decline-friend-request")
//   public void declineFriendRequest(@PathVariable String username, @RequestBody UserDto userDto) {
//      User currentUser = userService.findByUsername(username);
//      User friendUser = userService.findByUsername(userDto.getUsername());
//      userService.declineFriendRequest(currentUser, friendUser);
//   }

   @DeleteMapping("/decline-friend-request{username}/{declinedUserId}")
   public void declineFriendRequest(@PathVariable(value= "username") String username,@PathVariable(value= "declinedUserId") UUID declinedUserId) {
      userService.declineFriendRequest(username, declinedUserId);
   }

   @DeleteMapping("/remove-friend-request/{declinedUserId}/{username}")
   public void removeFriendRequest(@PathVariable(value= "declinedUserId") UUID declinedUserId, @PathVariable(value= "username") String username) {
      userService.removeFriendRequest(declinedUserId, username);
   }

//   @DeleteMapping("/{username}/delete-friend")
//   public void deleteFriend(@PathVariable String username, @RequestBody UserDto userDto) {
//      User currentUser = userService.findByUsername(username);
//      User friendUser = userService.findByUsername(userDto.getUsername());
//      userService.deleteFriend(currentUser, friendUser);
//   }

   @DeleteMapping("/delete-friend/{username}/{deletedUserId}")
   public void deleteFriend(@PathVariable(value= "username") String username,@PathVariable(value= "deletedUserId") UUID deletedUserId) {
      userService.deleteFriend(username, deletedUserId);
   }

   @GetMapping("/{username}/user-friend-requests")
   public Set<UserDto> getUserFriendRequests(@PathVariable String username) {
      Set<User> allFriendsRequests = userService.getAllFriendRequestsByUsername(username);
      Set<UserDto> userDtos = userConverter.convertEntitiesToDtos(allFriendsRequests);
      return userDtos;
   }

   @GetMapping("/{username}/user-friend-requests-sent")
   public Set<UserDto> getUserFriendRequestsSent(@PathVariable String username) {
      Set<User> allFriendsRequests = userService.getAllFriendRequestsSentByUsername(username);
      Set<UserDto> userDtos = userConverter.convertEntitiesToDtos(allFriendsRequests);
      return userDtos;
   }
   @GetMapping("/{username}/user-friends-list")
   public Set<UserDto> getUserFriendList(@PathVariable String username) {
      Set<User> allFriendsList = userService.getAllFriendsByUsername(username);
      Set<UserDto> allFriendsDtos = userConverter.convertEntitiesToDtos(allFriendsList);
      return allFriendsDtos;


   }
}
