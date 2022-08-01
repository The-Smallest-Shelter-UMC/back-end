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

    POST_EMPTY_IMG(false, 20, "게시물에 포함된 이미지가 없습니다"),
    POST_NOT_EXIST(false,20, "해당하는 게시물이 존재하지 않습니다"),
    EMPTY_URL_INFO(false, 20, "uri에 충분한 정보가 담기지 않았습니다. ex.queryString, pathValue"),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
