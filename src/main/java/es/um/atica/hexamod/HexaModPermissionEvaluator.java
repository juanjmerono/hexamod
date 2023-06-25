package es.um.atica.hexamod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.um.atica.hexamod.shared.security.CustomPermissionEvaluator;
import es.um.atica.hexamod.tasks.adapters.security.TasksPermission;
import es.um.atica.hexamod.users.adapters.security.UsersPermission;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class HexaModPermissionEvaluator implements PermissionEvaluator {

    private List<CustomPermissionEvaluator> evaluators;
    private CustomPermissionEvaluator defaultEvaluator = new CustomPermissionEvaluator();

    public HexaModPermissionEvaluator(UsersPermission usersPermission,TasksPermission tasksPermission) {
        evaluators = new ArrayList<>();
        evaluators.add(usersPermission);
        evaluators.add(tasksPermission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return evaluators.stream()
            .filter(ev -> ev.support(permission))
            .findFirst().orElseGet(() -> { return defaultEvaluator; })
            .hasPermission(authentication,targetDomainObject,permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return evaluators.stream()
            .filter(ev -> ev.support(permission))
            .findFirst().orElseGet(() -> { return defaultEvaluator; })
            .hasPermission(authentication, targetId, targetType, permission);
    }
    
}
