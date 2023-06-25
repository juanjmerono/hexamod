package es.um.atica.hexamod.tasks.adapters.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import es.um.atica.hexamod.shared.security.CustomPermissionEvaluator;

@Service
public class TasksPermission extends CustomPermissionEvaluator {

    private static final String PREFIX = "tasks";

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return "tasks.GET_OWN_TASKS".equals(permission);
    }

    @Override
    public boolean support(Object permission) {
        return permission.toString().startsWith(PREFIX+".");
    }
    
}
