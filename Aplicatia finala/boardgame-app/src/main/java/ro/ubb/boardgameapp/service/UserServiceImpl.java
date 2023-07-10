package ro.ubb.boardgameapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.boardgameapp.config.JwtAuthenticationFilter;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
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
}
