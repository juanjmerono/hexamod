package es.um.atica.hexamod.tasks.application;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import es.um.atica.hexamod.shared.usecase.UseCase;
import es.um.atica.hexamod.tasks.domain.Task;

@UseCase
public class TasksServiceImpl implements TasksService {
    
    private OwnTasksReadRepository ownTasksReadRepository;
    private OwnShortTasksReadRepository ownShortTasksReadRepository;
    private PDFService pdfService;

    public TasksServiceImpl(OwnTasksReadRepository ownTasksReadRepository,
        OwnShortTasksReadRepository ownShortTasksReadRepository,
        PDFService pdfService) {
            this.ownShortTasksReadRepository = ownShortTasksReadRepository;
            this.ownTasksReadRepository = ownTasksReadRepository;
            this.pdfService = pdfService;
    }

    @Override
    public Iterable<Task> loadAllTaskFromUserPaginated(String user, String search, int page, int pageSize) {
        return ownTasksReadRepository.findAllFiltered(user, search, page, pageSize);
    }

    @Override
    public Iterable<Task> loadAllShortTasksFromUserPaginated(String user, int page, int pageSize) {
        return ownShortTasksReadRepository.findAll(user, page, pageSize);
    }

    @Override
    public ByteArrayInputStream loadAllTaskFromUserInPDF(String user) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("tasks", ownTasksReadRepository.findAll(user));
        map.put("title", "My Tasks");
        return pdfService.pdfFromHtml("tasks/template",map);
    }

}
