package es.um.atica.hexamod.tasks.adapters.security;

import org.springframework.security.core.Authentication;

import es.um.atica.hexamod.shared.security.CustomPermissionEvaluator;

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
