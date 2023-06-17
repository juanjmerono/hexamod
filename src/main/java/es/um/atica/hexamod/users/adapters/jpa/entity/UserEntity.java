package es.um.atica.hexamod.users.adapters.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import es.um.atica.hexamod.users.domain.User;

@Entity
@Table(name="USERS",schema="HEXAMOD")
public class UserEntity {
    private String id;
    private String name;
    private int age;

    private UserEntity() {}
    private UserEntity(String id, String name,int age) {
        this.id = id; this.name = name; this.age = age;
    }

    public static UserEntity of (User usr) {
        return new UserEntity(
            usr.getId(),
            usr.getName(),
            usr.getAge());
    }

    public User toModel() {
        return User.of(this.id, this.name, this.age);
    }

    @Id
    @Column(name = "identificador")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Column(name = "nombre")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Column(name = "edad")
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

}
