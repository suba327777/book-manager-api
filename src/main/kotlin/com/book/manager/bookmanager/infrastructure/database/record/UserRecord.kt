/*
 * Auto-generated file. Created by MyBatis Generator
 */
package com.book.manager.bookmanager.infrastructure.database.record

import com.book.manager.bookmanager.domain.enum.RoleType

data class UserRecord(
    var id: Long? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var roleType: RoleType? = null
)