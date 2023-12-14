package com.example.quoteoftheday.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quoteoftheday.R
import com.example.quoteoftheday.data.local.QuoteEntity
import com.example.quoteoftheday.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var quoteList: MutableList<QuoteEntity>
    private lateinit var randomQuote: QuoteEntity
    private var isOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        quoteList = ArrayList()
        viewModel.getQuote()
        viewModel.getQuoteFromDatabase().observe(viewLifecycleOwner) {
            it?.let { list ->
                if (!isOpen) {
                    quoteList.addAll(list)
                    val shuffledList = quoteList.shuffled()
                    randomQuote = shuffledList.last()
                    binding.quote = randomQuote
                    updateFavoriteIcon(randomQuote)
                    isOpen = true
                    binding.progressBar.visibility = View.GONE
                    binding.constraintLayout.visibility = View.VISIBLE
                }
                randomQuote = list.firstOrNull { quote ->
                    quote.id == randomQuote.id
                }!!

            }
        }

        binding.ibRefresh.setOnClickListener {
            val shuffledList = quoteList.shuffled()
            randomQuote = shuffledList.last()
            binding.quote = randomQuote
            updateFavoriteIcon(randomQuote)
        }

        binding.ibFavorite.setOnClickListener {
            updateToFavorite(randomQuote)
        }
        binding.ibShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Quote")
            intent.putExtra(Intent.EXTRA_TEXT, randomQuote.content)
            val chooser = Intent.createChooser(intent, "Share using.....")
            startActivity(chooser)
        }


        return binding.root
    }

    private fun updateToFavorite(quoteEntity: QuoteEntity) {
        if (quoteEntity.quoteType == "REGULAR") {
            viewModel.updateQuoteType(quoteEntity.id, "FAVORITE")
            binding.ibFavorite.setImageResource(R.drawable.favorite_filled)
        } else {
            viewModel.updateQuoteType(quoteEntity.id, "REGULAR")
            binding.ibFavorite.setImageResource(R.drawable.favorite_outline)
        }
    }

    private fun updateFavoriteIcon(quoteEntity: QuoteEntity) {
        when (quoteEntity.quoteType) {
            "FAVORITE" -> {
                binding.ibFavorite.setImageResource(R.drawable.favorite_filled)
            }
            "REGULAR" -> {
                binding.ibFavorite.setImageResource(R.drawable.favorite_outline)
            }
        }
    }
}