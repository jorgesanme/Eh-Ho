package io.keepcoding.eh_ho.topics

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.databinding.ViewTopicBinding
import io.keepcoding.eh_ho.extensions.inflater
import io.keepcoding.eh_ho.model.Topic
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class TopicsAdapter(diffUtilItemCallback: DiffUtil.ItemCallback<Topic> = DIFF) :
    ListAdapter<Topic, TopicsAdapter.TopicViewHolder>(diffUtilItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder =
        TopicViewHolder(parent)

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) =
        holder.bind(getItem(position))



    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean =
                oldItem == newItem
        }
    }

    class TopicViewHolder(
        parent: ViewGroup,
        private val binding: ViewTopicBinding = ViewTopicBinding.inflate(
            parent.inflater,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: Topic) {
            binding.title.text = topic.title
            binding.topicCount.text = topic.postsCount.toString()
            binding.replayCount.text = topic.replyCount.toString()
            binding.lastPostDate.text = dateFormating(topic.lastPostedAt)
            //val avatarUrl: String = "https://mdiscourse.keepcoding.io/ ${user.userAvatar}"
            val image1 = "https://www.bookaris.com/images/HA/images/hoteles/129691_fotpe1_web1"
            val image2 = "https://www.bookaris.com/images/HA/images/hoteles/129691_fotpe1_web1.jpg"
            Picasso.get()
                .load(image1)
                .placeholder(R.drawable.user_icon)
                .error(R.drawable.error)
                .into(binding.avatarImage)
        }

    }
}



@SuppressLint("NewApi")
fun dateFormating(date: String): String {
    val dateAdapted = date.replace("Z", "0")
    val espDate = LocalDateTime
        .parse(dateAdapted)
        .toLocalDate().format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale("es", "ES"))
        )
    return espDate
}