package es.um.atica.hexamod.tasks.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.atica.hexamod.tasks.domain.Task;

@Service
public class TasksService {
    
    @Autowired
    private OwnTasksReadRepository tasksReadRepository;

    public Iterable<Task> findAll(String user, int page, int pageSize) {
        return tasksReadRepository.findAll(user, page, pageSize);
    }

}
