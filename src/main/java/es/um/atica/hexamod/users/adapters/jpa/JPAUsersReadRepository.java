package es.um.atica.hexamod.users.adapters.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.um.atica.hexamod.users.adapters.jpa.entity.UserEntity;

@Repository
public interface JPAUsersReadRepository extends PagingAndSortingRepository<UserEntity,String>, JpaSpecificationExecutor<UserEntity>{
    
}
