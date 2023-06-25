package es.um.atica.hexamod.users.application;

import es.um.atica.hexamod.shared.usecase.UseCase;
import es.um.atica.hexamod.users.domain.User;

@UseCase
public class UsersServiceImpl implements UsersService {
    
    private UsersReadRepository usersReadRepository;

    public UsersServiceImpl(UsersReadRepository usersReadRepository) {
        this.usersReadRepository = usersReadRepository;
    }

    @Override
    public Iterable<User> loadAllUsersPaginated(int page, int pageSize) {
        return usersReadRepository.findAll(page, pageSize);
    }

}
