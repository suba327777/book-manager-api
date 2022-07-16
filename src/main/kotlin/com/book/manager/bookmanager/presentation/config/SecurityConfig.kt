package com.book.manager.bookmanager.presentation.config

import com.book.manager.bookmanager.application.service.AuthenticationService
import com.book.manager.bookmanager.application.service.security.BookManagerUserDetailsService
import com.book.manager.bookmanager.domain.Enum.RoleType
import com.book.manager.bookmanager.presentation.handler.BookManagerAccessDeniedHandler
import com.book.manager.bookmanager.presentation.handler.BookManagerAuthenticationEntryPoint
import com.book.manager.bookmanager.presentation.handler.BookManagerAuthenticationFailureHandler
import com.book.manager.bookmanager.presentation.handler.BookManagerAuthenticationSuccessHandler
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfig (private val authenticationService:AuthenticationService):WebSecurityConfigurerAdapter(){
    override fun configure(http: HttpSecurity) {
        //アクセス権限の設定
        http.authorizeRequests()
             //loginに対してpermitAllを設定し、未認証ユーザーを含むすべてのアクセスを許可
            .mvcMatchers("/login").permitAll()
             //adminから始まるapiに対してhasAuthorityを使い管理者権限のユーザーのみアクセスを許可
            .mvcMatchers("/admin/**").hasAnyAuthority(RoleType.ADMIN.toString())
             //.anyRequest().authenticated()で上記以外のapiは認証済みユーザー（全権限）のみアクセスを許可　
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
             //フォームログイン(ユーザー名、パスワードでのログイン）を有効化
            .formLogin()
             //ログインapiのpathを/loginに設定
            .loginProcessingUrl("/login")
             //ログインapiに渡すユーザー名のパラメータ名をemailに設定
            .usernameParameter("email")
             //ログインapiに渡すパスワードのパラメータ名をpassに設定
            .passwordParameter("pass")
             //認証成功時に実行するハンドラー
            .successHandler(BookManagerAuthenticationSuccessHandler())
            //認証失敗時に実行するハンドラー
            .failureHandler(BookManagerAuthenticationFailureHandler())
            .and()
            .exceptionHandling()
             //未認証時のハンドラーを設定
            .authenticationEntryPoint(BookManagerAuthenticationEntryPoint())
             //認可失敗時のハンドラーを設定
            .accessDeniedHandler(BookManagerAccessDeniedHandler())
            .and()
            .cors()
            .configurationSource(corsConfigurationSource())
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        //認証処理を実行するクラスの指定
        auth.userDetailsService(BookManagerUserDetailsService(authenticationService))
             //パスワードの暗号化アルゴリズムの設定
            .passwordEncoder(BCryptPasswordEncoder())
    }
    private fun corsConfigurationSource():CorsConfigurationSource{
        //corsに関する各種の許可設定
        val corsConfiguration=CorsConfiguration()
        //メソッドの許可
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
        //ヘッダの許可
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
        // アクセス元のドメイン許可
        corsConfiguration.addAllowedOrigin("http://localhost:8081")
        corsConfiguration.allowCredentials = true

        val corsConfigurationSource = UrlBasedCorsConfigurationSource()
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration)

        return corsConfigurationSource
    }
}