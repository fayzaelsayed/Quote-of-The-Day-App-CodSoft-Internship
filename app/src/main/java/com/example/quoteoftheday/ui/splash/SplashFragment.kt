package com.example.quoteoftheday.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quoteoftheday.R
import com.example.quoteoftheday.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var textAnimation: Animation
    private lateinit var lottieAnimation: Animation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        lottieAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.top_to_bottom)
        textAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_to_top)

        CoroutineScope(Dispatchers.Main).launch {
            delay(300)
            binding.tvQuoteOfTheDay.visibility = View.VISIBLE
            binding.tvQuoteOfTheDay.startAnimation(textAnimation)
            delay(200)
            binding.lottieAnimationView.visibility = View.VISIBLE
            binding.lottieAnimationView.startAnimation(lottieAnimation)
            delay(6000)
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }
        return binding.root
    }


}