package umc_sjs.smallestShelter.config.jwt;

public interface JwtProperties {
    String SECRET = "smallShelter";
    int EXPIRATION_TIME = 60000*10*3; // 30분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}