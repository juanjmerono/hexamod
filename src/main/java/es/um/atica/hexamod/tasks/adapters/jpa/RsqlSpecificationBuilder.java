package es.um.atica.hexamod.tasks.adapters.jpa;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public class RsqlSpecificationBuilder<T> {
    
    public Specification<T> createSpecification(Node node) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node);
        }
        return null;
    }

    public Specification<T> createSpecification(LogicalNode logicalNode) {        
        List<Specification> specs = logicalNode.getChildren()
          .stream()
          .map(node -> createSpecification(node))
          .filter(Objects::nonNull)
          .collect(Collectors.toList());

        Specification<T> result = specs.get(0);
        if (logicalNode.getOperator() == LogicalOperator.AND) {
            for (int i = 1; i < specs.size(); i++) {
                result = Specification.where(result).and(specs.get(i));
            }
        } else if (logicalNode.getOperator() == LogicalOperator.OR) {
            for (int i = 1; i < specs.size(); i++) {
                result = Specification.where(result).or(specs.get(i));
            }
        }

        return result;
    }

    public Specification<T> createSpecification(ComparisonNode comparisonNode) {
        return filterOperator(comparisonNode.getSelector(), comparisonNode.getOperator(), comparisonNode.getArguments());
    }

    private Specification<T> filterOperator(String field, ComparisonOperator op, List<String> values) {
        return (root, query, criteriaBuilder) -> {
          if (op == RSQLOperators.EQUAL) {
            return criteriaBuilder.like(root.get(field),values.get(0).replace("*", "%"));
          }
          if (op == RSQLOperators.LESS_THAN) {
            return criteriaBuilder.lessThan(root.get(field),values.get(0));
          }
          return criteriaBuilder.equal(root.get(field),values.get(0)); 
        };        
    }

}
