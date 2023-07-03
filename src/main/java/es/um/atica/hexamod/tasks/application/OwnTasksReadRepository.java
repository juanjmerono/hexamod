package es.um.atica.hexamod.tasks.application;

import es.um.atica.hexamod.tasks.domain.Task;

public interface OwnTasksReadRepository {
    
    // Return all tasks for specified user paginated
    public Iterable<Task> findAllFiltered(String user, String search, int page, int pageSize);
    // Return all tasks for specified user
    public Iterable<Task> findAll(String user);

}
