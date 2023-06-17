package es.um.atica.hexamod.tasks.adapters.rest;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.hexamod.shared.identity.IdentityService;
import es.um.atica.hexamod.tasks.adapters.rest.dto.TaskDTO;
import es.um.atica.hexamod.tasks.application.TasksService;
import es.um.atica.hexamod.tasks.domain.Task;

@RestController
@RequestMapping(value="/hexamod/v1/task")
public class TasksQueryRestController {

    private static final String DEFAULT_PAGE_SIZE = "5";

    @Autowired
    private TasksModelAssembler tasksModelAssembler;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private IdentityService identityService;

    @GetMapping
    @PreAuthorize("hasPermission('OWN', 'tasks.GET_OWN_TASKS')")
    public CollectionModel<EntityModel<TaskDTO>> allUsers(@AuthenticationPrincipal Jwt jwt,
        @RequestParam(name="page",required = false, defaultValue = "0") int page,
		@RequestParam(name="size",required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize) throws Exception {
            String userId = identityService.getUserIdFromSubject(jwt.getSubject());
            Page<Task> pageUser = (Page<Task>)tasksService.findAll(userId, page,pageSize);
            return tasksModelAssembler
                    .toCollectionModel(
                        new PageImpl<TaskDTO>(StreamSupport.stream(pageUser.spliterator(), false)
                                .map(TaskDTO::of)
                                .collect(Collectors.toList()),
                            pageUser.getPageable(),
                            pageUser.getTotalElements()));

    }

    
}
