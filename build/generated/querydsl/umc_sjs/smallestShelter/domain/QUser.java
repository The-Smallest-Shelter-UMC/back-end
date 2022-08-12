package umc_sjs.smallestShelter.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1614100409L;

    public static final QUser user = new QUser("user");

    public final StringPath address = createString("address");

    public final DateTimePath<java.sql.Timestamp> createDate = createDateTime("createDate", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    public final ListPath<FavoriteAnimal, QFavoriteAnimal> favoriteAnimalList = this.<FavoriteAnimal, QFavoriteAnimal>createList("favoriteAnimalList", FavoriteAnimal.class, QFavoriteAnimal.class, PathInits.DIRECT2);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath name = createString("name");

    public final EnumPath<OrganizationName> organizationName = createEnum("organizationName", OrganizationName.class);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<Animal, QAnimal> uploadAnimalList = this.<Animal, QAnimal>createList("uploadAnimalList", Animal.class, QAnimal.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

