package es.um.atica.hexamod.users.application;

import es.um.atica.hexamod.users.domain.User;

public interface UsersReadRepository {
    
    public Iterable<User> findAll(int page, int pageSize);

}
