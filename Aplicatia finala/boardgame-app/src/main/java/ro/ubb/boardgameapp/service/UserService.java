package ro.ubb.boardgameapp.service;

import ro.ubb.boardgameapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    User saveUser(User user);

    User updateUser(UUID id, User user);

    void deleteUser(UUID id);

    User findByUsername(String username);

User findUserById(UUID id);
    Optional<User> getUserLoggedInInfo();

    List<User> searchUsers(String searchQuery);

    void sendFriendRequest(User currentUser, User friendUser);

    void acceptFriendRequest(User currentUser, User friendUser);

    void declineFriendRequest(String username, UUID declinedUserId);

    void deleteFriend(String username, UUID deletedFriendId);

    Set<User> getAllFriendRequestsByUsername(String username);

    Set<User> getAllFriendsByUsername(String username);
}
