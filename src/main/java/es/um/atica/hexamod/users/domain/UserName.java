package es.um.atica.hexamod.users.domain;

public class UserName {
    
    private String value;

    private UserName(String name) {
        if (name==null || name.isEmpty()) throw new IllegalArgumentException("Nombre de usuario no válido");
        this.value = name;
    }

    public static UserName of(String name) {
        return new UserName(name);
    }

    public String getValue() { return this.value; }

}
