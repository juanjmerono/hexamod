package es.um.atica.hexamod.tasks.application;

import es.um.atica.hexamod.tasks.domain.Task;

public interface OwnShortTasksReadRepository {
    
    // Return all short tasks for specified user paginated
    public Iterable<Task> findAll(String user, int page, int pageSize);

}
