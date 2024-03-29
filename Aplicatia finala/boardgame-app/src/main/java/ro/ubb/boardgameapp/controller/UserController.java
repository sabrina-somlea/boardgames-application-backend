package ro.ubb.boardgameapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.boardgameapp.converter.BoardGameConverter;
import ro.ubb.boardgameapp.converter.UserConverter;
import ro.ubb.boardgameapp.dto.BoardGameDto;
import ro.ubb.boardgameapp.dto.UpdatePasswordDto;
import ro.ubb.boardgameapp.dto.UserDto;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.service.BoardGameSessionService;
import ro.ubb.boardgameapp.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins ="*")
//@CrossOrigin(origins = "http://localhost:4200")

public class UserController {
   @Autowired
   private UserService userService;
   @Autowired
   private UserConverter userConverter;
   @Autowired
   private BoardGameConverter boardGameConverter;
   @Autowired
   private BoardGameSessionService boardGameSessionService;
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
   UserDto saveUser(@Valid @RequestBody UserDto userDto) throws IOException {
      User user = userConverter.convertDtoToEntity(userDto);
//      user.setProfileImage(profileImage.getBytes());
      User savedUser = userService.saveUser(user);
      return userConverter.convertEntityToDto(savedUser);
   }

   @RequestMapping(value = "/users/{username}", method = RequestMethod.PUT)
   UserDto updateUser(@PathVariable String username, @RequestBody UserDto userDto) {
      return userConverter.convertEntityToDto(
              userService.updateUser(username,
                      userConverter.convertDtoToEntity(userDto))
      );
   }

   @PutMapping("/users/id={id}")
   public User updatePassword(@PathVariable(value = "id") UUID userId,
                             @RequestBody UpdatePasswordDto updatePasswordDto) {
      User updateUser = userService.updatePassword(userId, updatePasswordDto.getInitialPassword(), updatePasswordDto.getNewPassword());
      return updateUser;
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
   public UserDto getUserLoggedIn() {
     User user =  userService.getUserLoggedInInfo();
     UserDto userDto = userConverter.convertEntityToDto(user);
     return userDto;
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

//   @GetMapping("/{userId}/top-games")
//   public List<BoardGame> getTop3PlayedGamesByUser(@PathVariable UUID userId) {
//      return userService.getTop3PlayedGamesByUser(userId);
//   }
   @GetMapping("/{username}/top-games")
//   public List<BoardGameDto> getTop3PlayedGamesByUsername(@PathVariable String username) {
//      List<BoardGame> top3BoardGames = boardGameSessionService.getTop3PlayedGamesByUsername(username);
//      List<BoardGameDto> top3BoardGameDtos = top3BoardGames.stream()
//              .map(boardGameConverter::convertEntityToDto)
//              .collect(Collectors.toList());
//      return top3BoardGameDtos;
//   }
   public Map<String, Long> getTop3PlayedGamesWithPlayCountByUsername(@PathVariable String username) {
      Map<BoardGame, Long> topGamesWithPlayCount = boardGameSessionService.getTop3PlayedGamesByUsername(username);

      return topGamesWithPlayCount.entrySet().stream()
              .collect(Collectors.toMap(
                      entry -> entry.getKey().getName(), // Numele jocului
                      Map.Entry::getValue
              ));
   }

   @GetMapping("/{userId}/won-sessions-count")
   public Long getNumberOfWonSessionsForUser(@PathVariable UUID userId) {
      return boardGameSessionService.countWonSessionsByUserId(userId);
   }

   @GetMapping("/{userId}/played-sessions-count")
   public Long getNumberOfPlayedSessionsForUser(@PathVariable UUID userId) {
      return boardGameSessionService.countPlayedSessionsByUserId(userId);
   }

   @GetMapping("/{userId}/friends-count")
   public Long getNumberOfFriendsForUser(@PathVariable UUID userId) {
      return userService.countFriendsForUser(userId);
   }

   @GetMapping("/{userId}/boardgames-count")
   public Long getNumberOfBoardGamesForUser(@PathVariable UUID userId) {
      return boardGameSessionService.countBoardGamesForUser(userId);
   }
}
