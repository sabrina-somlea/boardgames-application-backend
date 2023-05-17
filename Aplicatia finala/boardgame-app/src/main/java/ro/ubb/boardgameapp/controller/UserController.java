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
import java.util.Set;
import java.util.UUID;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")

public class UserController {
   @Autowired
   private UserService userService;
   @Autowired
   private UserConverter userConverter;

   @GetMapping("/users")
   Set<UserDto> getAllUsers() {
      List<User> allUsers = userService.getAllUsers();
      Set<UserDto> userDtos = userConverter.convertEntitiesToDtos(allUsers);
      return userDtos;

   }

   @RequestMapping(value = "/users", method = RequestMethod.POST)
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
//   @GetMapping("/check-username")
//   public ResponseEntity<Boolean> isUsernameUnique(@RequestParam String username) {
//      boolean isUnique = userService.findByUsername(username) == null;
//      return ResponseEntity.ok(isUnique);
//   }
//@CrossOrigin(origins = "*")
@GetMapping("/check-username")
   public ResponseEntity<Boolean> usernameExists(@RequestParam String username) {
      boolean usernameExists = userService.findByUsername(username) !=null;
      return ResponseEntity.ok(usernameExists);
   }
}
