package com.seroean.apps.ui.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seroean.apps.databinding.FragmentThirdScreenBinding
import com.seroean.apps.ui.FINISHED
import com.seroean.apps.ui.ONBOARDING
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.welcome.WelcomeActivity

class ThirdScreen : Fragment() {
    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lightStatusBar(requireActivity().window)
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)

        binding.ivNextFinish.setOnClickListener {
            onBoardingIsFinished()
            val intent = Intent(requireActivity(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
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
