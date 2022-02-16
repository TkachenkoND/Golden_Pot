package com.dassol.kanolio.presentation.view.game_view.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.dassol.kanolio.R
import com.dassol.kanolio.databinding.FragmentGameBinding
import com.dassol.kanolio.presentation.view.game_view.FragmentMenu
import kotlin.random.Random

class FragmentGame : Fragment(R.layout.fragment_game), GameAdapterCallback {

    private lateinit var binding: FragmentGameBinding
    private val itemAdapter = GameFragmentAdapter(this as GameAdapterCallback)
    private var bonus = 0
    private var list = createItemsList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        itemAdapter.currentList = list

        binding.backButton.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainer, FragmentMenu())
                addToBackStack(null)
            }
        }
    }

    private fun initRecycler() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = itemAdapter
        }
    }

    private fun createItemsList(): ArrayList<FruitItem> {
        var index = 16
        val list = ArrayList<FruitItem>()

        while (index > 0) {
            getRandomBoolean()?.let {
                FruitItem(R.drawable.element1, it)
            }?.let { list.add(it) }

            index--
        }

        return list
    }

    private fun getRandomBoolean(): Boolean? {
        var boolean: Boolean? = null

        when (Random.nextInt(2) + 1) {
            1 -> boolean = true
            2 -> boolean = false
        }

        return boolean
    }

    @SuppressLint("SetTextI18n")
    override fun onItemClick(bonus: Int) {
        this.bonus += bonus
        binding.txtBonus.text = "Now your bonus is ${this.bonus}"
    }
}