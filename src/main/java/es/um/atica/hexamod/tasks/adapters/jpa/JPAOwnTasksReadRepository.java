package es.um.atica.hexamod.tasks.adapters.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.um.atica.hexamod.tasks.adapters.jpa.entity.TaskEntity;

@Repository
public interface JPAOwnTasksReadRepository extends PagingAndSortingRepository<TaskEntity,String>, JpaSpecificationExecutor<TaskEntity>{
 
    public Page<TaskEntity> findAllByUser(String user, Pageable pageable);

}
