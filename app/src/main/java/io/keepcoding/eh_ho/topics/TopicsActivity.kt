package io.keepcoding.eh_ho.topics

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.databinding.ActivityTopicsBinding
import io.keepcoding.eh_ho.di.DIProvider
import kotlinx.coroutines.Runnable

class TopicsActivity : AppCompatActivity() {

    private val binding: ActivityTopicsBinding by lazy {
        ActivityTopicsBinding.inflate(
            layoutInflater
        )
    }
    private val topicsAdapter = TopicsAdapter()
    private val vm: TopicsViewModel by viewModels { DIProvider.topicsViewModelProviderFactory }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        iniRefreshListener()
        binding.topics.apply {
            adapter = topicsAdapter
        }
        vm.state.observe(this) {
            when (it) {
                is TopicsViewModel.State.LoadingTopics -> renderLoading(it)
                is TopicsViewModel.State.TopicsReceived -> topicsAdapter.submitList(it.topics)
                is TopicsViewModel.State.NoTopics -> renderEmptyState()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.loadTopics()
    }

    private fun iniRefreshListener(){
        val swipeContainer = binding.refresh
        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            val handler = Handler()
            Handler().postDelayed(Runnable{
                if(swipeContainer.isRefreshing()){
                    // TODO: 19/5/21  se debe borrar el contenido de las vista y volver a cargarlo
                    // vm.loadTopics()
                    swipeContainer.isRefreshing = false
                }
            }, 2000)
        })
    }

    private fun renderEmptyState() {
        // Render empty state
    }

    private fun renderLoading(loadingState: TopicsViewModel.State.LoadingTopics) {
        (loadingState as? TopicsViewModel.State.LoadingTopics.LoadingWithTopics)?.let {
            topicsAdapter.submitList(
                it.topics
            )
        }
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent = Intent(context, TopicsActivity::class.java)
    }
}
