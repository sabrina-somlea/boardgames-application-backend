package ro.ubb.boardgameapp.converter;

import org.springframework.stereotype.Component;
import ro.ubb.boardgameapp.dto.UserDto;
import ro.ubb.boardgameapp.model.User;


@Component
public class UserConverter extends BaseConverter<User, UserDto> {


    @Override
    public User convertDtoToEntity(UserDto dto) {
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthday(dto.getBirthday())
                .gender(dto.getGender())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
//                .profileImage(dto.getProfileImage())
                .build();
        user.setId(dto.getId());
        return user;
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        UserDto userDto = UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .email(user.getEmail())
                .username(user.getUsername())
//                .profileImage(user.getProfileImage())
                .build();
        userDto.setId(user.getId());
        return userDto;
    }
}
