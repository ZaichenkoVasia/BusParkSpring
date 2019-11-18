package ua.mycompany.buspark.model.service.mapper;

import org.mapstruct.Mapper;
import ua.mycompany.buspark.model.domain.User;
import ua.mycompany.buspark.model.entity.UserEntity;

@Mapper
public interface UserMapper {
    User userEntityToUser(UserEntity userEntity);

    UserEntity userToUserEntity(User user);
}
