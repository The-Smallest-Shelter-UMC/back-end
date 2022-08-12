package umc_sjs.smallestShelter.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIllness is a Querydsl query type for Illness
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIllness extends EntityPathBase<Illness> {

    private static final long serialVersionUID = -1465505916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIllness illness = new QIllness("illness");

    public final QAnimal animal;

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath name = createString("name");

    public QIllness(String variable) {
        this(Illness.class, forVariable(variable), INITS);
    }

    public QIllness(Path<? extends Illness> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIllness(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIllness(PathMetadata metadata, PathInits inits) {
        this(Illness.class, metadata, inits);
    }

    public QIllness(Class<? extends Illness> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animal = inits.isInitialized("animal") ? new QAnimal(forProperty("animal"), inits.get("animal")) : null;
    }

}

