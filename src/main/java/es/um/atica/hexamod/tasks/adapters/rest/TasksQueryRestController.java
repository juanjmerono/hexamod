package es.um.atica.hexamod.tasks.adapters.rest;

import java.io.ByteArrayInputStream;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.um.atica.hexamod.shared.identity.IdentityService;
import es.um.atica.hexamod.tasks.adapters.rest.dto.TaskDTO;
import es.um.atica.hexamod.tasks.application.TasksServiceImpl;
import es.um.atica.hexamod.tasks.domain.Task;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name="Tasks Service")
@RestController
@RequestMapping(value="/hexamod/v1/task")
public class TasksQueryRestController {

    private static final String DEFAULT_PAGE_SIZE = "5";

    @Autowired
    private TasksModelAssembler tasksModelAssembler;

    @Autowired
    private TasksServiceImpl tasksService;

    @Autowired
    private IdentityService identityService;

    @Operation(
        description = "Get all user tasks paginated",
        responses = {
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "200", ref = "ok"),
        }
    )
    @GetMapping
    @PreAuthorize("hasPermission('OWN', 'tasks.GET_OWN_TASKS')")
    public CollectionModel<EntityModel<TaskDTO>> allUserTasks(@AuthenticationPrincipal Jwt jwt,
        @RequestParam(name="search",required = false, defaultValue = "") String search,
        @RequestParam(name="page",required = false, defaultValue = "0") int page,
		@RequestParam(name="size",required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize) throws Exception {
            String userId = identityService.getUserIdFromSubject(jwt.getSubject());
            Page<Task> pageUser = (Page<Task>)tasksService.loadAllTaskFromUserPaginated(userId, search, page, pageSize);
            return tasksModelAssembler
                    .toCollectionModel(
                        new PageImpl<TaskDTO>(StreamSupport.stream(pageUser.spliterator(), false)
                                .map(TaskDTO::of)
                                .collect(Collectors.toList()),
                            pageUser.getPageable(),
                            pageUser.getTotalElements()));
    }

    @Operation(
        description = "Get all user short tasks paginated",
        responses = {
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "200", ref = "ok"),
        }
    )
    @GetMapping("/short")
    @PreAuthorize("hasPermission('OWN', 'tasks.GET_OWN_TASKS')")
    public CollectionModel<EntityModel<TaskDTO>> allUserShortTasks(@AuthenticationPrincipal Jwt jwt,
        @RequestParam(name="page",required = false, defaultValue = "0") int page,
		@RequestParam(name="size",required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize) throws Exception {
            String userId = identityService.getUserIdFromSubject(jwt.getSubject());
            Page<Task> pageUser = (Page<Task>)tasksService.loadAllShortTasksFromUserPaginated(userId, page,pageSize);
            return tasksModelAssembler
                    .toCollectionModel(
                        new PageImpl<TaskDTO>(StreamSupport.stream(pageUser.spliterator(), false)
                                .map(TaskDTO::of)
                                .collect(Collectors.toList()),
                            pageUser.getPageable(),
                            pageUser.getTotalElements()));

    }

    @Operation(
        description = "Get all user tasks in pdf format",
        responses = {
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "200", ref = "ok"),
        }
    )
    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasPermission('OWN', 'tasks.GET_OWN_TASKS')")
    public @ResponseBody ResponseEntity<Resource> allUsersPDF(@AuthenticationPrincipal Jwt jwt) throws Exception {
            String userId = identityService.getUserIdFromSubject(jwt.getSubject());
            ByteArrayInputStream bis = tasksService.loadAllTaskFromUserInPDF(userId);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"tasks.pdf\"");
            responseHeaders.set(HttpHeaders.CONTENT_LENGTH, ""+bis.available());
            return ResponseEntity.ok()
                .headers(responseHeaders)               
               .body(new InputStreamResource(bis));
    }

}
