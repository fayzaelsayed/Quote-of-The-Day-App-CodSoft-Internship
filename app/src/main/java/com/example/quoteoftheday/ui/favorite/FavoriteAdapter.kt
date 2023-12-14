package com.example.quoteoftheday.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteoftheday.R
import com.example.quoteoftheday.data.local.QuoteEntity
import com.example.quoteoftheday.databinding.FragmentHomeBinding

class FavoriteAdapter :
    ListAdapter<QuoteEntity, FavoriteAdapter.QuoteViewHolder>(DiffCallback()) {
    private lateinit var buttonClickListener: OnButtonClickListener
    fun setOnButtonClickListener(listener: OnButtonClickListener) {
        buttonClickListener = listener
    }

    interface OnButtonClickListener {
        fun onButtonClick(action: String, quoteEntity: QuoteEntity)
    }

    inner class QuoteViewHolder(private val binding: FragmentHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quoteEntity: QuoteEntity) {
            binding.apply {
                quote = quoteEntity
                ibRefresh.visibility = View.GONE
                progressBar.visibility = View.GONE
                constraintLayout.visibility = View.VISIBLE
                when (quoteEntity.quoteType) {
                    "FAVORITE" -> {
                        binding.ibFavorite.setImageResource(R.drawable.favorite_filled)
                    }
                    "REGULAR" -> {
                        binding.ibFavorite.setImageResource(R.drawable.favorite_outline)
                    }
                }
                ibFavorite.setOnClickListener {
                    buttonClickListener.onButtonClick("Favorite", quoteEntity)
                }
                ibShare.setOnClickListener {
                    buttonClickListener.onButtonClick("Share", quoteEntity)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<QuoteEntity>() {
        override fun areItemsTheSame(oldItem: QuoteEntity, newItem: QuoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: QuoteEntity,
            newItem: QuoteEntity
        ): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding =
            FragmentHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(currentItem)
        }
    }


}