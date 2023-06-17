package es.um.atica.hexamod.users.domain;

public class UserAge {
    
    private int value;

    private UserAge(int age) {
        if (age<=17 || age>110) throw new IllegalArgumentException("Edad de usuario no válida");
        this.value = age;
    }

    public static UserAge of(int age) { return new UserAge(age); }

    public int getValue() { return this.value; }
}
