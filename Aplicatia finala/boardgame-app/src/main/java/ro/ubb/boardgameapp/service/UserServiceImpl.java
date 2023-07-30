package ro.ubb.boardgameapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.boardgameapp.config.JwtAuthenticationFilter;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        String plainPassword = user.getPassword();
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        user.setPassword(hashedPassword);
        User saveUser = userRepository.save(user);
        return saveUser;
    }
@Transactional
    @Override
    public User updateUser(UUID id, User user) {
        User updateUser = userRepository.findById(id).orElseThrow();
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setGender(user.getGender());
        updateUser.setBirthday(user.getBirthday());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        return updateUser;
    }

    @Override
    public void deleteUser(UUID id) {
    userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findUserById(id);
    }


    @Override
    public Optional<User> getUserLoggedInInfo() {
        String currentUserLoggedInInfo = JwtAuthenticationFilter.CURRENT_USER_LOGGED_IN;
        return userRepository.findIdByUsername(currentUserLoggedInInfo);
    }

    @Override
    public List<User> searchUsers(String searchQuery) {
        return userRepository.searchUsers(searchQuery);
    }

//    @Override
//    public void sendFriendRequest(User currentUser, User friendUser) {
//         {
////            currentUser.getFriendsRequests().add(friendUser);
//            friendUser.getFriendsRequests().add(currentUser);
//            userRepository.save(currentUser);
//            userRepository.save(friendUser);
//        }
//    }
    @Override
    public void sendFriendRequest(String username, UUID userRequestedId) {
        Optional<User> currentUser = userRepository.findOptionalByUsername(username);
        User user = currentUser.get();
        Optional<User> requestedUser = userRepository.findById(userRequestedId);
        User requestedFriend = requestedUser.get();
            requestedFriend.getFriendsRequests().add(user);
//            user.getFriendRequestOf().add(requestedFriend);
            userRepository.save(user);
            userRepository.save(requestedFriend);

    }
//    @Override
//    public void acceptFriendRequest(User currentUser, User friendUser) {
//        currentUser.getFriendsRequests().remove(friendUser);
//        friendUser.getFriendsRequests().remove(currentUser);
//        currentUser.getFriends().add(friendUser);
//        friendUser.getFriends().add(currentUser);
//        userRepository.save(currentUser);
//        userRepository.save(friendUser);
//
//    }
@Override
public void acceptFriendRequest(String username, UUID userFriendId) {
    Optional<User> currentUser = userRepository.findOptionalByUsername(username);
    User user = currentUser.get();
    Optional<User> acceptedUser = userRepository.findById(userFriendId);
    User acceptedFriend = acceptedUser.get();
    user.getFriendsRequests().remove(acceptedFriend);
    acceptedFriend.getFriendsRequests().remove(user);
    user.getFriends().add(acceptedFriend);
    acceptedFriend.getFriends().add(user);
    userRepository.save(user);
    userRepository.save(acceptedFriend);

}
//    @Override
//    public void declineFriendRequest(User currentUser, User friendUser) {
//        currentUser.getFriendsRequests().remove(friendUser);
////        friendUser.getFriendRequestOf().remove(currentUser);
//        userRepository.save(currentUser);
//        userRepository.save(friendUser);
//    }

    @Override
    public void declineFriendRequest(String username, UUID declinedUserId) {
        Optional<User> currentUser = userRepository.findOptionalByUsername(username);
        User user = currentUser.get();
        Optional<User> declinedUser = userRepository.findById(declinedUserId);
        User deniedUser = declinedUser.get();
        user.getFriendsRequests().remove(deniedUser);
//        friendUser.getFriendRequestOf().remove(currentUser);
        userRepository.save(user);
        userRepository.save(deniedUser);
    }

    @Override
    public void removeFriendRequest(UUID declinedUserId, String username) {
        Optional<User> currentUser = userRepository.findOptionalByUsername(username);
        User user = currentUser.get();
        Optional<User> declinedUser = userRepository.findById(declinedUserId);
        User deniedUser = declinedUser.get();
        deniedUser.getFriendsRequests().remove(user);
//        friendUser.getFriendRequestOf().remove(currentUser);
        userRepository.save(user);
        userRepository.save(deniedUser);
    }
    @Override
    public void deleteFriend(String username, UUID deletedUserId) {
        Optional<User> currentUser = userRepository.findOptionalByUsername(username);
        User user = currentUser.get();
        Optional<User> deletedUser = userRepository.findById(deletedUserId);
        User deniedUser = deletedUser.get();
        user.getFriends().remove(deniedUser);
//        friendUser.getFriendOf().remove(currentUser);
        userRepository.save(user);
        userRepository.save(deniedUser);
    }

    @Override
    public Set<User> getAllFriendRequestsByUsername(String username) {
            Optional<User> userOptional = userRepository.findOptionalByUsername(username);
            User user = userOptional.get();
            return user.getFriendsRequests();
        }

    @Override
    public Set<User> getAllFriendRequestsSentByUsername(String username) {
        Optional<User> userOptional = userRepository.findOptionalByUsername(username);
        User user = userOptional.get();
        return user.getFriendRequestOf();
    }


    @Override
    public Set<User> getAllFriendsByUsername(String username) {
        Optional<User> userOptional = userRepository.findOptionalByUsername(username);
        User user = userOptional.get();
        return user.getFriends();
    }


}
