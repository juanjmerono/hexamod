package es.um.atica.hexamod.tasks.adapters.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.um.atica.hexamod.tasks.adapters.jpa.entity.TaskEntity;
import es.um.atica.hexamod.tasks.application.OwnTasksReadRepository;
import es.um.atica.hexamod.tasks.domain.Task;

@Service
@org.springframework.context.annotation.Primary
public class OwnTasksReadRepositoryImpl implements OwnTasksReadRepository {

    @Autowired
    private JPAOwnTasksReadRepository jpaTasksReadRepository;

    @Override
    public Iterable<Task> findAll(String user, int page, int pageSize) {
        return jpaTasksReadRepository.findAllByUser(user, PageRequest.of(page, pageSize)).map(TaskEntity::toModel);
    }
    
}
