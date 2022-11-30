package com.project.kopring.global.error.type

enum class ErrorCode(
        val message: String,
        val status: Int
) {

    // USER
    DUPLICATE_EMAIL("중복된 이메일 입니다.", 409),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 404),
    PASSWORD_NOT_CORRECT("비밀번호가 일치하지 않습니다.", 400),


    // TOKEN
    REFRESH_TOKEN_EXPIRED("만료된 refreshToken 입니다.", 403),
    INVALID_TOKEN("유효하지 않은 accessToken 입니다.", 403),
    EXPIRATION_TOKEN("만료된 accessToken 입니다.", 403),


    // POST
    POST_NOT_FOUND("게시글을 찾을 수 없습니다.", 404),


    // SERVER
    INTERVAL_SERVER_ERROR("서버오류 입니다.", 500)

}