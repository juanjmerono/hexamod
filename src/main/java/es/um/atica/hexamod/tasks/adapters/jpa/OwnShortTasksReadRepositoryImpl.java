package es.um.atica.hexamod.tasks.adapters.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.um.atica.hexamod.tasks.adapters.jpa.entity.TaskEntity;
import es.um.atica.hexamod.tasks.application.OwnShortTasksReadRepository;
import es.um.atica.hexamod.tasks.domain.Task;

@Service
@org.springframework.context.annotation.Primary
public class OwnShortTasksReadRepositoryImpl implements OwnShortTasksReadRepository {

    @Autowired
    private JPATasksReadRepository jpaTasksReadRepository;

    @Override
    public Iterable<Task> findAll(String user, int page, int pageSize) {
        return jpaTasksReadRepository.findAll(TaskSpecificationBuilder.isShort(user), PageRequest.of(page, pageSize)).map(TaskEntity::toModel);
    }
    
}
