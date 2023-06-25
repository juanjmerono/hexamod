package es.um.atica.hexamod.tasks.adapters.jpa;

import org.springframework.data.jpa.domain.Specification;

import es.um.atica.hexamod.tasks.adapters.jpa.entity.TaskEntity;

public class TaskSpecificationBuilder {

    public static Specification<TaskEntity> isShort(String userId) {
        return Specification.where(ownTasks(userId)).and(shortCriteria());
    }

    // Short Task 18 or less duration
    private static Specification<TaskEntity> shortCriteria() {
        return (root, query, criteriaBuilder) -> {
          return criteriaBuilder.le(root.get("duration"),18);
        };        
    }

    public static Specification<TaskEntity> ownTasks(String userId) {
        return (root, query, criteriaBuilder) -> {
          return criteriaBuilder.equal(root.get("user"),userId);
        };        
    }

    
}
