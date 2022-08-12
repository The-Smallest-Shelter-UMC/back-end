package umc_sjs.smallestShelter.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimal is a Querydsl query type for Animal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimal extends EntityPathBase<Animal> {

    private static final long serialVersionUID = -1244382344L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnimal animal = new QAnimal("animal");

    public final QAge age;

    public final EnumPath<Status> bark = createEnum("bark", Status.class);

    public final EnumPath<Status> bite = createEnum("bite", Status.class);

    public final DateTimePath<java.sql.Timestamp> createDate = createDateTime("createDate", java.sql.Timestamp.class);

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final ListPath<Illness, QIllness> illnessList = this.<Illness, QIllness>createList("illnessList", Illness.class, QIllness.class, PathInits.DIRECT2);

    public final BooleanPath isAdopted = createBoolean("isAdopted");

    public final StringPath mainImgUrl = createString("mainImgUrl");

    public final StringPath name = createString("name");

    public final ListPath<Post, QPost> postList = this.<Post, QPost>createList("postList", Post.class, QPost.class, PathInits.DIRECT2);

    public final EnumPath<Status> separation = createEnum("separation", Status.class);

    public final EnumPath<Status> socialization = createEnum("socialization", Status.class);

    public final EnumPath<Species> species = createEnum("species", Species.class);

    public final EnumPath<Status> toilet = createEnum("toilet", Status.class);

    public final QUser uploadUser;

    public QAnimal(String variable) {
        this(Animal.class, forVariable(variable), INITS);
    }

    public QAnimal(Path<? extends Animal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnimal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnimal(PathMetadata metadata, PathInits inits) {
        this(Animal.class, metadata, inits);
    }

    public QAnimal(Class<? extends Animal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.age = inits.isInitialized("age") ? new QAge(forProperty("age")) : null;
        this.uploadUser = inits.isInitialized("uploadUser") ? new QUser(forProperty("uploadUser")) : null;
    }

}

