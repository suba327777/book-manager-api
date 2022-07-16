package com.book.manager.bookmanager.presentation.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BookManagerAuthenticationEntryPoint:AuthenticationEntryPoint {
    // 未認証状態のユーザーで認証が必要なapiにアクセスしたときの処理
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        //401
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}