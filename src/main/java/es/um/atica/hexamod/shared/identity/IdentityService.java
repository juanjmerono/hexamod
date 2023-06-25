package es.um.atica.hexamod.shared.identity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class IdentityService {
    
    private Map<String, String> userMap;
    
    public IdentityService() {
        userMap = new HashMap<>();
        userMap.put("admin@acme.es","30497182-c376-11ed-afa1-0242ac220010");
        userMap.put("user@acme.es","30497182-c376-11ed-afa1-0242ac220012");
    }

    public String getUserIdFromSubject(String userSubject) {
        return userMap.getOrDefault(userSubject, "-");
    }

    public boolean isUserAdmin(String userSubject) {
        return "admin@acme.es".equals(userSubject);
    }

}
