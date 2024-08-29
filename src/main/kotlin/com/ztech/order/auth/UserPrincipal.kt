package com.ztech.order.auth

open class UserPrincipal(
     val aid: Int,
     val cid: Int?,
     val sid: Int?,
     val username: String,
     val password: String?
) 