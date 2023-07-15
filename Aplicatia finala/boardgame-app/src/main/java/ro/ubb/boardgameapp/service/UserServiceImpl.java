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
    public Optional<User> getUserLoggedInInfo() {
        String currentUserLoggedInInfo = JwtAuthenticationFilter.CURRENT_USER_LOGGED_IN;
        return userRepository.findIdByUsername(currentUserLoggedInInfo);
    }

    @Override
    public List<User> searchUsers(String searchQuery) {
        return userRepository.searchUsers(searchQuery);
    }

    @Override
    public void sendFriendRequest(User currentUser, User friendUser) {
         {
            currentUser.getFriendsRequests().add(friendUser);
            friendUser.getFriendRequestOf().add(currentUser);
            userRepository.save(currentUser);
            userRepository.save(friendUser);
        }
    }

    @Override
    public void acceptFriendRequest(User currentUser, User friendUser) {
        currentUser.getFriendsRequests().remove(friendUser);
        friendUser.getFriendRequestOf().remove(currentUser);
        currentUser.getFriends().add(friendUser);
        friendUser.getFriendOf().add(currentUser);
        userRepository.save(currentUser);
        userRepository.save(friendUser);

    }

    @Override
    public void declineFriendRequest(User currentUser, User friendUser) {
        currentUser.getFriendsRequests().remove(friendUser);
        friendUser.getFriendRequestOf().remove(currentUser);
        userRepository.save(currentUser);
        userRepository.save(friendUser);
    }
    @Override
    public void deleteFriend(User currentUser, User friendUser) {
        currentUser.getFriends().remove(friendUser);
        friendUser.getFriendOf().remove(currentUser);
        userRepository.save(currentUser);
        userRepository.save(friendUser);
    }

    @Override
    public Set<User> getAllFriendRequestsByUsername(String username) {
            Optional<User> userOptional = userRepository.findOptionalByUsername(username);
            User user = userOptional.get();
            return user.getFriendsRequests();
        }


    @Override
    public Set<User> getAllFriendsByUsername(String username) {
        Optional<User> userOptional = userRepository.findOptionalByUsername(username);
        User user = userOptional.get();
        return user.getFriends();
    }


}
