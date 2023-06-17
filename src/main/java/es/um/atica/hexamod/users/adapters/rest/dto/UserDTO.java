package es.um.atica.hexamod.users.adapters.rest.dto;

import es.um.atica.hexamod.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserDTO {

    private String id;
    private String name;
    private Integer age;

    public static UserDTO of(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .build();
    }

}
