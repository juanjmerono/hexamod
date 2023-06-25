package es.um.atica.hexamod.users.adapters.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import es.um.atica.hexamod.shared.identity.IdentityService;
import es.um.atica.hexamod.shared.security.CustomPermissionEvaluator;

@Service
public class UsersPermission extends CustomPermissionEvaluator {

    private static final String PREFIX = "users";

    @Autowired
    private IdentityService identityService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // Only for admins
        return (authentication.getPrincipal() instanceof Jwt) && identityService.isUserAdmin(((Jwt)authentication.getPrincipal()).getSubject());
    }

    @Override
    public boolean support(Object permission) {
        return permission.toString().startsWith(PREFIX+".");
    }
    
}
