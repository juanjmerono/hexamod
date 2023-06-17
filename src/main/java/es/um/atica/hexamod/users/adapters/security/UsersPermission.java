package es.um.atica.hexamod.users.adapters.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import es.um.atica.hexamod.shared.security.CustomPermissionEvaluator;

public class UsersPermission extends CustomPermissionEvaluator {

    private static final String PREFIX = "users";

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // Only for admins
        return (authentication.getPrincipal() instanceof Jwt) && "admin@acme.es".equals(((Jwt)authentication.getPrincipal()).getSubject());
    }

    @Override
    public boolean support(Object permission) {
        return permission.toString().startsWith(PREFIX+".");
    }
    
}
