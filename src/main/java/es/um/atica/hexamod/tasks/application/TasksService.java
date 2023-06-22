package es.um.atica.hexamod.tasks.application;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.atica.hexamod.tasks.domain.Task;

@Service
public class TasksService {
    
    @Autowired
    private OwnTasksReadRepository tasksReadRepository;

    @Autowired
    private PDFService pdfService;

    public Iterable<Task> findAll(String user, int page, int pageSize) {
        return tasksReadRepository.findAll(user, page, pageSize);
    }

    public ByteArrayInputStream loadAsStream(String user, int page, int pageSize) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("tasks", tasksReadRepository.findAll(user, page, pageSize));
        map.put("title", "My Tasks");
        return pdfService.pdfFromHtml("tasks/template",map);
    }

}
