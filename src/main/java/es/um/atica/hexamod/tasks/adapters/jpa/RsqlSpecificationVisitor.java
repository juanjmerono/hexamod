package es.um.atica.hexamod.tasks.adapters.jpa;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class RsqlSpecificationVisitor<T> implements RSQLVisitor<Specification<T>, Void> {

    private RsqlSpecificationBuilder<T> builder;

    public RsqlSpecificationVisitor() {
        builder = new RsqlSpecificationBuilder<T>();
    }

    @Override
    public Specification<T> visit(AndNode node, Void param) {
        return builder.createSpecification(node);
    }

    @Override
    public Specification<T> visit(OrNode node, Void param) {
        return builder.createSpecification(node);
    }

    @Override
    public Specification<T> visit(ComparisonNode node, Void param) {
        return builder.createSpecification(node);
    }
    
}
