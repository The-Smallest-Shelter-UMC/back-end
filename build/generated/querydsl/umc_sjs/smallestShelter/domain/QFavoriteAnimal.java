package umc_sjs.smallestShelter.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteAnimal is a Querydsl query type for FavoriteAnimal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteAnimal extends EntityPathBase<FavoriteAnimal> {

    private static final long serialVersionUID = -1542123468L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteAnimal favoriteAnimal = new QFavoriteAnimal("favoriteAnimal");

    public final QAnimal animal;

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final QUser likeUser;

    public QFavoriteAnimal(String variable) {
        this(FavoriteAnimal.class, forVariable(variable), INITS);
    }

    public QFavoriteAnimal(Path<? extends FavoriteAnimal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteAnimal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteAnimal(PathMetadata metadata, PathInits inits) {
        this(FavoriteAnimal.class, metadata, inits);
    }

    public QFavoriteAnimal(Class<? extends FavoriteAnimal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animal = inits.isInitialized("animal") ? new QAnimal(forProperty("animal"), inits.get("animal")) : null;
        this.likeUser = inits.isInitialized("likeUser") ? new QUser(forProperty("likeUser")) : null;
    }

}

