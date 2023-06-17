package es.um.atica.hexamod.users.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.atica.hexamod.users.domain.User;

@Service
public class UsersService {
    
    @Autowired
    private UsersReadRepository usersReadRepository;

    public Iterable<User> findAll(int page, int pageSize) {
        return usersReadRepository.findAll(page, pageSize);
    }

}
