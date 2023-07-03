package es.um.atica.hexamod.tasks.adapters.jpa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.jpa.domain.Specification;

import es.um.atica.hexamod.tasks.adapters.jpa.entity.TaskEntity;

public class TaskSpecificationBuilder {

    // Own Tasks & Short
    public static Specification<TaskEntity> isShort(String userId) {
        return Specification.where(ownTasks(userId)).and(shortCriteria());
    }

    // Own Tasks & Filter
    public static Specification<TaskEntity> ownTasksFiltered(String userId, String search) {
      return toPredicate(ownTasks(userId),search);
    }

    // Own Tasks
    public static Specification<TaskEntity> ownTasks(String userId) {
        return (root, query, criteriaBuilder) -> {
          return criteriaBuilder.equal(root.get("user"),userId);
        };        
    }

    // Filter critria
    // search=field1<val1,field2>val2,...
    private static Specification<TaskEntity> toPredicate(Specification<TaskEntity> base, String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        Specification<TaskEntity> current = base;
        while (matcher.find()) {
          current = Specification.where(current)
            .and(filter(matcher.group(1), 
                        matcher.group(2), 
                        matcher.group(3)));
        }        
        return current;
    }
        
    private static Specification<TaskEntity> filter(String field, String operation, String value) {
        return (root, query, criteriaBuilder) -> {
          switch(operation) {
            case "<": return criteriaBuilder.lessThan(root.get(field),value);
            case ">": return criteriaBuilder.greaterThan(root.get(field),value);
            default: return criteriaBuilder.equal(root.get(field),value);
          }
        };        
    }

    // Short Task 18 or less duration
    private static Specification<TaskEntity> shortCriteria() {
        return (root, query, criteriaBuilder) -> {
          return criteriaBuilder.le(root.get("duration"),18);
        };        
    }

}
