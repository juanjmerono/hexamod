package es.um.atica.hexamod.tasks.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import es.um.atica.hexamod.tasks.adapters.rest.dto.TaskDTO;

@Component
public class TasksModelAssembler implements RepresentationModelAssembler<TaskDTO,EntityModel<TaskDTO>> {
    
    private static final String BASE_PATH = "/hexamod/v1/user/";

    @Autowired
    private PagedResourcesAssembler<TaskDTO> pagedResourcesAssembler;

    @Override
    public EntityModel<TaskDTO> toModel(TaskDTO entity) {
        return EntityModel.of(entity,
            linkTo(TasksQueryRestController.class).slash(BASE_PATH + entity.getId()).withSelfRel()
            //linkTo(UsersCommandRestController.class).slash(BASE_PATH + entity.getId()).withRel("put"),
            //linkTo(UsersCommandRestController.class).slash(BASE_PATH + entity.getId()).withRel("delete")
            );
    }

    @Override
    public CollectionModel<EntityModel<TaskDTO>> toCollectionModel(Iterable<? extends TaskDTO> entities) {
        PagedModel<EntityModel<TaskDTO>> model = pagedResourcesAssembler.toModel((Page)entities, this);
        /*try {
            model.add(linkTo(methodOn(UsersCommandRestController.class).createUser(null, null, null, null)).withRel("post"));
        } catch (Exception ex) {}*/
        return model; 

    }

}