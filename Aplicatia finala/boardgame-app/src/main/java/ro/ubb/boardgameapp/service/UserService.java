package ro.ubb.boardgameapp.service;

import ro.ubb.boardgameapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    User saveUser(User user);

    User updateUser(String username, User user);

    void deleteUser(UUID id);

    User findByUsername(String username);

User findUserById(UUID id);
   User getUserLoggedInInfo();

    User updatePassword(UUID id, String initialPassword, String newPassword);
    List<User> searchUsers(String searchQuery);

    void sendFriendRequest(String username, UUID UserId);

    void acceptFriendRequest(String username, UUID UserFriendId);

    void declineFriendRequest(String username, UUID declinedUserId);
    void removeFriendRequest(UUID declinedUserId, String username);

    void deleteFriend(String username, UUID deletedFriendId);

    Set<User> getAllFriendRequestsByUsername(String username);

    Set<User> getAllFriendRequestsSentByUsername(String username);

    Set<User> getAllFriendsByUsername(String username);
}
