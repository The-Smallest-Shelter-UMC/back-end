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



    //post


    /**
     * 3000 : Response 오류
     */
    //member
    USERS_EXISTS_USERNAME(false,3001,"중복된 아이디입니다."),
    USERS_EXISTS_EMAIL(false,3002,"중복된 이메일입니다."),
    MODIFY_FAIL_USER(false, 3020, "회원 수정을 실패하였습니다."),
    DELETE_FAIL_USER(false, 3021, "회원 삭제를 실패하였습니다."),



    //animal



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