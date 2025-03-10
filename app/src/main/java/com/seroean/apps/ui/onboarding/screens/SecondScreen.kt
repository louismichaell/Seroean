package com.seroean.apps.ui.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.seroean.apps.R
import com.seroean.apps.databinding.FragmentSecondScreenBinding
import com.seroean.apps.ui.FINISHED
import com.seroean.apps.ui.ONBOARDING
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.welcome.WelcomeActivity

class SecondScreen : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        lightStatusBar(requireActivity().window)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        binding.ivNextArrow2.setOnClickListener {
            viewPager?.currentItem = 2
        }
        binding.skipButton.setOnClickListener {
            onBoardingIsFinished()
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }

    private fun onBoardingIsFinished() {
        val sharedPreferences = requireActivity().getSharedPreferences(ONBOARDING, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(FINISHED, true).apply()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}