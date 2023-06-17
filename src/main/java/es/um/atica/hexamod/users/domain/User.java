package es.um.atica.hexamod.users.domain;

import java.util.UUID;

public class User {
    
    private UUID id;
    private UserName name;
    private UserAge age;

    private User(UUID id, UserName name, UserAge age) {
        this.id = id; this.name = name; this.age = age;
    }

    public static User of(String id, String name, int age) {
        return new User(UUID.fromString(id), UserName.of(name),UserAge.of(age));
    }

    public String getId() { return id.toString(); }
    public String getName() { return name.getValue(); }
    public int getAge() { return age.getValue(); }

}
