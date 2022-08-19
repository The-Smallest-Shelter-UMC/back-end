package umc_sjs.smallestShelter.response;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    NOT_MATCHING_JWT_USER(false, 2004, "유저와 JWT가 매칭되지 않습니다."),
    INVALID_ENUM_VALUE(false, 2005, "올바르지 않은 ENUM값 입니다."),
    NON_EXISTING_VALUE(false, 2006, "요청에 대한 값이 존재하지 않습니다."),
    // 임시로 적어두었습니다! by 가나
    EMPTY_URL_VALUE(false, 2007, "URL에 비어있는 값이 있습니다. path variable 또는 query string"),
    Expired_JWT_Token(false, 2008, "만료된 JWT 토큰입니다. 다시 로그인 해주세요."),

    //users
    USERS_EMPTY_USERNAME(false, 2010, "아이디를 입력해주세요."),
    USERS_EMPTY_NAME(false, 2011, "이름을 입력해주세요."),
    USERS_EMPTY_EMAIL(false, 2012, "이메일을 입력해주세요."),
    USERS_EMPTY_PASSWORD(false, 2013, "비밀번호를 입력해주세요."),
    USERS_EMPTY_PHONENUMBER(false, 2014, "전화번호를 입력해주세요."),
    USERS_EMPTY_PROFILEIMG(false, 2015, "이미지를 등록해주세요."),
    USERS_EMPTY_ADDRESS(false, 2016, "주소를 입력해주세요."),
    USERS_EMPTY_ROLE(false, 2017, "역할을 입력해주세요."),
    USERS_EMPTY_ORGANIZATIONNAME(false, 2018, "단체유저의 단체이름을 입력해주세요."),

    //animal
    ANIMAL_EMPTY_NAME(false, 2019, "동물 이름을 입력해주세요."),
    ANIMAL_EMPTY_YEAR(false, 2020, "동물 나이(몇 살)를 입력해주세요."),
    ANIMAL_EMPTY_MONTH(false, 2021, "동물 나이(몇 개월)을 입력해주세요."),
    ANIMAL_EMPTY_GENDER(false, 2022, "동물 성별을 입력해주세요."),
    ANIMAL_EMPTY_SPECIES(false, 2023, "동물 종류를 입력해주세요."),
    ANIMAL_EMPTY_MAINIMG(false, 2024, "동물 대표 사진을 입력해주세요."),
    ANIMAL_EMPTY_SOCIALIZATION(false, 2025, "동물 사회화 훈련 단계를 입력해주세요."),
    ANIMAL_EMPTY_SEPARATION(false, 2026, "동물 분리불안 단계를 입력해주세요."),
    ANIMAL_EMPTY_TOILET(false, 2027, "동물 배변훈련 단계를 입력해주세요."),
    ANIMAL_EMPTY_BARK(false, 2028, "동물 입질 단계를 입력해주세요."),
    ANIMAL_EMPTY_BITE(false, 2029, "동물 짖음 단계를 입력해주세요."),

    //post
    POST_EMPTY_IMG(false,33, "게시물에 이미지가 없습니다"),
    POST_CONTENT_LENGTH_OVER(false, 33, "게시글의 내용이 허용된 글자수를 넘었습니다"),
    POST_EMPTY_CONTNET(false,33,"게시글의 내용이 없습니다"),
    POST_NOT_EXIST(false,33,"해당하는 게시물이 존재하지 않습니다"),
    POSTIDX_ANIMALIDX_ILLEGAL(false, 33, "해당 동물의 게시물의 아닙니다: idx 불일치"),

    /**
     * 3000 : Response 오류
     */
    //member
    USERS_EXISTS_USERNAME(false,3001,"중복된 아이디입니다."),
    USERS_EXISTS_EMAIL(false,3002,"중복된 이메일입니다."),
    MODIFY_FAIL_USER(false, 3020, "회원 수정을 실패하였습니다."),
    DELETE_FAIL_USER(false, 3021, "회원 삭제를 실패하였습니다."),
    NON_EXISTING_USER(false, 3022, "해당하는 회원이 존재하지 않습니다."),

    //animal
    NON_EXISTING_ANIMAL(false, 3023, "해당하는 동물이 존재하지 않습니다."),

    //post



    /**
     * 4000 : Database, Server 오류
     */
    // Common
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),

    //member
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다.");


    //animal



    //post





    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
    }