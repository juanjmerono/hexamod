package es.um.atica.hexamod.tasks.adapters.rest.dto;

import es.um.atica.hexamod.tasks.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TaskDTO {
    
    private String id;    
    private String user;
    private String description;
    private int duration;

    public static TaskDTO of (Task task) {
        return TaskDTO.builder()
            .id(task.getId())
            .user(task.getUser())
            .description(task.getDescription())
            .duration(task.getDuration())
            .build();
    }

}
