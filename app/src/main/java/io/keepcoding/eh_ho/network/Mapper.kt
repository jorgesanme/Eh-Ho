package io.keepcoding.eh_ho.network

import io.keepcoding.eh_ho.model.LogIn
import io.keepcoding.eh_ho.model.Topic
import io.keepcoding.eh_ho.model.User
import okhttp3.Response
import okhttp3.internal.threadName
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

fun Response.toSignInModel(): LogIn = when (this.isSuccessful) {
    true -> LogIn.Success(
        JSONObject(this.body?.string()).getJSONObject("user").getString("username")
    )
    false -> LogIn.Error(this.body?.string() ?: "Some Error parsing response")
}

fun IOException.toSignInModel(): LogIn = LogIn.Error(this.toString())

fun Response.toSignUpModel(): LogIn = when (this.isSuccessful) {
    true -> LogIn.Success(
        JSONObject(this.body?.string()).getJSONObject("user").getString("username")
    )
    false -> LogIn.Error(this.body?.string() ?: "Some Error parsing response")
}

fun IOException.toSignUpModel(): LogIn = LogIn.Error(this.toString())

fun Response.toTopicsModel(): Result<List<Topic>> = when (this.isSuccessful) {
    true -> Result.success(parseTopics(body?.string()))
        .also { println("JcLog: BackendResult -> $it") }

    false -> Result.failure(IOException(this.body?.string() ?: "Some Error parsing response"))
}

fun IOException.toTopicsModel(): Result<List<Topic>> = Result.failure(this)

fun parseTopics(json: String?): List<Topic> = json?.let {
    val topicsJsonArray: JSONArray =
        JSONObject(it).getJSONObject("topic_list").getJSONArray("topics")
    (0 until topicsJsonArray.length()).map { index ->
        val topicJsonObject = topicsJsonArray.getJSONObject(index)
        Topic(
            id = topicJsonObject.getInt("id"),
            title = topicJsonObject.getString("title"),
            postsCount = topicJsonObject.getInt("posts_count"),
            replyCount = topicJsonObject.getInt("views"),
            lastPostedAt = topicJsonObject.getString("last_posted_at"),
            bumped = topicJsonObject.getBoolean("bumped"),
            pinned = topicJsonObject.getBoolean("pinned")

        )
    }
} ?: emptyList<Topic>()

fun parseUser(json: String?): List<User> = json?.let {
    val userJsonArray: JSONArray =
        JSONObject(it).getJSONObject("users").getJSONArray("posters")
    (0 until userJsonArray.length()).map { index ->
        val userJsonArray = userJsonArray.getJSONObject(index)
        User(
            userId = userJsonArray.getInt("user_id"),
            userAvatar = userJsonArray.getString("image_url"),
            userName = userJsonArray.getString("username")
            )
    }
} ?: emptyList<User>()
