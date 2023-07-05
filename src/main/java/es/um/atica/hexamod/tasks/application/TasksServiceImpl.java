package es.um.atica.hexamod.tasks.application;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import es.um.atica.hexamod.shared.usecase.UseCase;
import es.um.atica.hexamod.tasks.adapters.rest.dto.TaskDTO;
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
        // Complete task list
        Iterable<Task> tasksIterator = ownTasksReadRepository.findAll(user);
        // Convert to list
        List<Task> tasksList = StreamSupport
                .stream(tasksIterator.spliterator(), false)
                .collect(Collectors.toList());
        // Duplicate tasks 1000 times to load big pdf
        List<Task> output =
            Collections.nCopies(1000,tasksList)
               .stream()
               .flatMap(List::stream)
               .collect(Collectors.toList());
        // Variables to PDF
        HashMap<String,Object> map = new HashMap<>();
        map.put("tasks", output);
        map.put("title", "My Tasks");
        return pdfService.pdfFromHtml("tasks/template",map);
    }

}
