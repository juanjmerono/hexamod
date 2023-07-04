package es.um.atica.hexamod.tasks.adapters.jpa;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import es.um.atica.hexamod.tasks.adapters.jpa.entity.TaskEntity;

public class TaskSpecificationBuilder {

    // Own Tasks & Short
    public static Specification<TaskEntity> isShort(String userId) {
        return Specification.where(ownTasks(userId)).and(shortCriteria());
    }

    // Own Tasks & Filter
    public static Specification<TaskEntity> ownTasksFiltered(String userId, String search) {
      try {
        Node rootNode = new RSQLParser().parse(search);
        Specification<TaskEntity> spec = rootNode.accept(new RsqlSpecificationVisitor<TaskEntity>());
        return Specification.where(ownTasks(userId)).and(spec);
      } catch (Exception pe) {
        return Specification.where(ownTasks(userId));
      }
    }

    // Own Tasks
    public static Specification<TaskEntity> ownTasks(String userId) {
        return (root, query, criteriaBuilder) -> {
          return criteriaBuilder.equal(root.get("user"),userId);
        };        
    }
    
    // Short Task 18 or less duration
    private static Specification<TaskEntity> shortCriteria() {
        return (root, query, criteriaBuilder) -> {
          return criteriaBuilder.le(root.get("duration"),18);
        };        
    }

}
