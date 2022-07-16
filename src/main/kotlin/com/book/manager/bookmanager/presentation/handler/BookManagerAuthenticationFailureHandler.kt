package com.book.manager.bookmanager.presentation.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BookManagerAuthenticationFailureHandler:AuthenticationFailureHandler {
    //認証失敗時の処理
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        //401を設定している
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}