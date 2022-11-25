package com.pratama.rvloadmore

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratama.rvloadmore.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val PAGE_START = 0
    private var isLoading = false
    private var isLastPage = false

    private var currentPage: Int = PAGE_START
    private var bigData = mutableListOf<TextItem>()
    private var bigDataChunk = listOf<List<TextItem>>()
    private var pagingData = mutableListOf<TextItem>()
    private lateinit var rvAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupRV()

        seedData()

        loadData()
    }


    private fun seedData() {
        for (i in 0..500) {
            bigData.add(TextItem(i, "number $i"))
        }
        bigDataChunk = bigData.chunked(10)
        Log.d("debug", "success seed data ${bigData.size}")
        Log.d("debug", "chunked ${bigDataChunk.size}")
        rvAdapter.notifyDataSetChanged()
    }

    private fun loadData() {
        pagingData.addAll(bigDataChunk[PAGE_START])
        Log.d("debug", "load data page $PAGE_START")
        Log.d("debug", "size data paging ${pagingData.size}")
        rvAdapter.notifyDataSetChanged()
    }

    private fun setupRV() {
        rvAdapter = MainAdapter(pagingData)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        binding.rv.itemAnimator = DefaultItemAnimator()
        binding.rv.adapter = rvAdapter
        binding.rv.layoutManager = linearLayoutManager

        binding.rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {

            override fun isLastPage() = isLastPage

            override fun isLoading() = isLoading

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1

                loadNextPage()
            }
        })
    }

    private fun loadNextPage() {
        val delay = 2_000L // 2 sec
        binding.loading.visibility = View.VISIBLE
        try {
            // delay 2sec
            Handler(Looper.getMainLooper()).postDelayed({
                bigDataChunk[currentPage].map {
                    pagingData.add(it)
                    binding.rv.post {
                        rvAdapter.notifyItemInserted(pagingData.size - 1)
                    }
                }
                Log.d("debug", "load data page $currentPage")
                Log.d("debug", "size data paging ${pagingData.size}")

                isLoading = false
                binding.loading.visibility = View.GONE

                Toast.makeText(this@MainActivity, "Sukses load next page", Toast.LENGTH_SHORT)
                    .show()

            }, delay)


        } catch (e: Exception) {
            Log.e("debug", "exceptions ${e.localizedMessage}")
        }
    }
}


data class TextItem(
    val number: Int, val text: String
)