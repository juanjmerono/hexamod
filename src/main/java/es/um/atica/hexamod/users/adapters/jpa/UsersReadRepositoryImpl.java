package es.um.atica.hexamod.users.adapters.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.um.atica.hexamod.users.adapters.jpa.entity.UserEntity;
import es.um.atica.hexamod.users.application.UsersReadRepository;
import es.um.atica.hexamod.users.domain.User;

@Service
@org.springframework.context.annotation.Primary
public class UsersReadRepositoryImpl implements UsersReadRepository {

    @Autowired
    private JPAUsersReadRepository jpaUsersReadRepository;

    @Override
    public Iterable<User> findAll(int page, int pageSize) {
        return jpaUsersReadRepository.findAll(PageRequest.of(page, pageSize)).map(UserEntity::toModel);
    }
    
}
