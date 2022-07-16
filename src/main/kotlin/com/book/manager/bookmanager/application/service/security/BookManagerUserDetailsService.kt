package com.book.manager.bookmanager.application.service.security

import com.book.manager.bookmanager.application.service.AuthenticationService
import com.book.manager.bookmanager.domain.Enum.RoleType
import com.book.manager.bookmanager.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class BookManagerUserDetailsService(private val authenticationService: AuthenticationService) : UserDetailsService {
    //ユーザー名を引数で受け取り
    override fun loadUserByUsername(username: String): UserDetails? {
        //ユーザー情報を取得し
        val user = authenticationService.findUser(username)
        //オブジェクトを生成し返却
        return user?.let { BookManagerUserDetails(user) }
    }
}
//ログイン時に入力された値から取得したユーザー情報としてセッションに保持される
data class BookManagerUserDetails(val id: Long, val email: String, val pass: String, val roleType: RoleType):UserDetails{

    constructor(user:User):this(user.id,user.email,user.password,user.roleType)
    // 権限の取得。許可が必要なパスの場合、この関数で取得した権限の情報でチェックされる
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        //ADMINかUSERか
        return AuthorityUtils.createAuthorityList(this.roleType.toString())
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }
    //パスワードの取得、ログイン時に入力したパスワードとの比較に使用される
    override fun getPassword(): String {
        return this.pass
    }

    override fun isAccountNonExpired(): Boolean {
        return  true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}