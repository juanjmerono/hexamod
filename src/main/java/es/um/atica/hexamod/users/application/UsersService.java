package es.um.atica.hexamod.users.application;

import es.um.atica.hexamod.users.domain.User;

public interface UsersService {
    public Iterable<User> loadAllUsersPaginated(int page, int pageSize);
}
