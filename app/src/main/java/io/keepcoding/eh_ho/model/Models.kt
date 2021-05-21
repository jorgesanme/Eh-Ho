package io.keepcoding.eh_ho.model

sealed class LogIn {
    data class Success(val userName: String) : LogIn()
    data class Error(val error: String) : LogIn()
}

data class Topic(
    val id: Int,
    val title: String,
    val postsCount: Int,
    val replyCount: Int,
    val lastPostedAt: String,
    val bumped: Boolean,
    val pinned: Boolean

)
data class User(
    val userId: Int,
    val userName: String,
    val userAvatar: String
)