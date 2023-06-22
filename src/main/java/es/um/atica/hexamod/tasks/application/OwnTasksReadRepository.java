package es.um.atica.hexamod.tasks.application;

import es.um.atica.hexamod.tasks.domain.Task;

public interface OwnTasksReadRepository {
    
    public Iterable<Task> findAll(String user, int page, int pageSize);
    public Iterable<Task> findAll(String user);

}
