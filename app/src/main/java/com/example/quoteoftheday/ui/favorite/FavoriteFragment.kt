package com.example.quoteoftheday.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteoftheday.data.local.QuoteEntity
import com.example.quoteoftheday.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var quotesList: MutableList<QuoteEntity>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        quotesList = ArrayList()
        setUpAdapter()
        viewModel.getQuotesFromDatabase().observe(viewLifecycleOwner) {
            it?.let { list ->
                quotesList.clear()
                quotesList.addAll(list.filter { quote ->
                    quote.quoteType == "FAVORITE"
                })
                val newList = ArrayList<QuoteEntity>()
                newList.addAll(quotesList)
                favoriteAdapter.submitList(newList)
            }
        }
        return binding.root
    }

    private fun setUpAdapter() {
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.rvFavorites.adapter = favoriteAdapter

        favoriteAdapter.setOnButtonClickListener(object : FavoriteAdapter.OnButtonClickListener {
            override fun onButtonClick(action: String, quoteEntity: QuoteEntity) {
                when (action) {
                    "Favorite" -> {
                        updateToFavorite(quoteEntity)
                    }
                    "Share" -> {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Quote")
                        intent.putExtra(Intent.EXTRA_TEXT, quoteEntity.content)
                        val chooser = Intent.createChooser(intent, "Share using.....")
                        startActivity(chooser)
                    }
                }
            }

        })
    }

    private fun updateToFavorite(quoteEntity: QuoteEntity) {
        if (quoteEntity.quoteType == "REGULAR") {
            viewModel.updateQuoteType(quoteEntity.id, "FAVORITE")
        } else {
            viewModel.updateQuoteType(quoteEntity.id, "REGULAR")
        }
    }
}