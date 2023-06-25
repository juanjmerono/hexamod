package es.um.atica.hexamod.tasks.application;

import java.io.ByteArrayInputStream;

import es.um.atica.hexamod.tasks.domain.Task;

public interface TasksService {
    public Iterable<Task> loadAllTaskFromUserPaginated(String user, int page, int pageSize);
    public Iterable<Task> loadAllShortTasksFromUserPaginated(String user, int page, int pageSize);
    public ByteArrayInputStream loadAllTaskFromUserInPDF(String user) throws Exception;
}
