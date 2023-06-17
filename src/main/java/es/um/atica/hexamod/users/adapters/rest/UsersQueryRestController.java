package es.um.atica.hexamod.users.adapters.rest;

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

import es.um.atica.hexamod.users.adapters.rest.dto.UserDTO;
import es.um.atica.hexamod.users.application.UsersService;
import es.um.atica.hexamod.users.domain.User;

@RestController
@RequestMapping(value="/hexamod/v1/user")
public class UsersQueryRestController {

    private static final String DEFAULT_PAGE_SIZE = "5";

    @Autowired
    private UsersModelAssembler usersModelAssembler;

    @Autowired
    private UsersService usersService;

    @GetMapping
    @PreAuthorize("hasPermission('ALL', 'users.GET_LIST')")
    public CollectionModel<EntityModel<UserDTO>> allUsers(@AuthenticationPrincipal Jwt jwt,
        @RequestParam(name="page",required = false, defaultValue = "0") int page,
		@RequestParam(name="size",required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize) throws Exception {
            Page<User> pageUser = (Page<User>)usersService.findAll(page,pageSize);
            return usersModelAssembler
                    .toCollectionModel(
                        new PageImpl<UserDTO>(StreamSupport.stream(pageUser.spliterator(), false)
                                .map(UserDTO::of)
                                .collect(Collectors.toList()),
                            pageUser.getPageable(),
                            pageUser.getTotalElements()));

    }
    
}
