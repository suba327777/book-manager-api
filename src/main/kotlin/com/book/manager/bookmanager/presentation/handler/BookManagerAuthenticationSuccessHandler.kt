package com.book.manager.bookmanager.presentation.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BookManagerAuthenticationSuccessHandler :AuthenticationSuccessHandler{
    //ログインapiで認証が成功したときの処理
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        //返却時のHTTPステータスを設定(200)返却
        response.status = HttpServletResponse.SC_OK
    }
}
