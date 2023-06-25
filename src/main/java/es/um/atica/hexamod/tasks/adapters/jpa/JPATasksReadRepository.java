package es.um.atica.hexamod.tasks.adapters.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.um.atica.hexamod.tasks.adapters.jpa.entity.TaskEntity;

@Repository
public interface JPATasksReadRepository extends PagingAndSortingRepository<TaskEntity,String>, JpaSpecificationExecutor<TaskEntity>{
    
}
