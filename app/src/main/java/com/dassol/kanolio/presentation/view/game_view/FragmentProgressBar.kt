package com.dassol.kanolio.presentation.view.game_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.dassol.kanolio.R
import com.dassol.kanolio.databinding.FragmentProgressBinding
import com.dassol.kanolio.presentation.view.game_view.game.FragmentGame
import java.util.*

class FragmentProgressBar : Fragment(R.layout.fragment_progress) {

    private lateinit var binding: FragmentProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainer, FragmentGame())
                        addToBackStack(null)
                    }
                }
            }, 1980
        )
    }
}