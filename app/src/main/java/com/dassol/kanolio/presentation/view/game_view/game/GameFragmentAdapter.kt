package com.dassol.kanolio.presentation.view.game_view.game

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dassol.kanolio.R
import com.dassol.kanolio.databinding.ItemFruitBinding

fun interface GameAdapterCallback {
    fun onItemClick(bonus: Int)
}

class GameFragmentAdapter(val callbackListener: GameAdapterCallback) :
    RecyclerView.Adapter<GameFragmentAdapter.ItemViewHolder>() {

    var currentList: MutableList<FruitItem> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemFruitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameFragmentAdapter.ItemViewHolder, position: Int) {
        currentList[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int = currentList.size

    inner class ItemViewHolder(private val binding: ItemFruitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FruitItem) {
            binding.imgItem.setOnClickListener {
                if (item.lucky) {
                    binding.imgItem.setImageResource(R.drawable.element2)
                    callbackListener.onItemClick(100)
                } else {
                    binding.imgItem.setImageResource(R.drawable.element3)
                    callbackListener.onItemClick(0)
                }
            }
        }
    }
}