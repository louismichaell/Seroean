package com.seroean.apps.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.ProfileViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.databinding.FragmentProfileBinding
import com.seroean.apps.ui.about.AboutActivity
import com.seroean.apps.ui.favorite.FavoriteActivity
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.loadImage
import com.seroean.apps.ui.login.LoginActivity
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.questions.PertanyaanActivity

@Suppress("UNUSED_EXPRESSION")
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileViewModelFactory(requireContext())
        )[ProfileViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lightStatusBar(requireActivity().window)
        preferences = SettingsPreferences.getInstance(requireContext().dataStore)
        _binding = FragmentProfileBinding.bind(view)
        setupShimmerEffect(true)
        clickButton()

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(viewLifecycleOwner) {
            token = it
            viewModel.getBiodata(token)
        }

        viewModel.biodata.observe(viewLifecycleOwner) { biodata ->
            if (biodata != null) {
                binding.apply {
                    tvUserName.text = biodata.name
                    tvUserEmail.text = biodata.email
                    ivProfilePicture.loadImage(biodata.profilePicture)
                }
                setupShimmerEffect(false)
            } else {
                setupShimmerEffect(true)
            }
        }

        viewModel.profileStatus.observe(viewLifecycleOwner) { profileStatus ->
            if (profileStatus.isNullOrEmpty()) return@observe
            val isError = viewModel.isErrorProfile
            if (isError && profileStatus == getString(R.string.UNAUTHORIZED)) {
                getString(R.string.ERROR_UNAUTHORIZED)
            } else {
                profileStatus
            }
        }
    }

    private fun clickButton() {
        val authViewModel = ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]
        binding.apply {
            lnProfile.setOnClickListener {
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                startActivity(intent)
            }
            lnDisukai.setOnClickListener {
                val intent = Intent(requireContext(), FavoriteActivity::class.java)
                startActivity(intent)
            }
            lnQuestion.setOnClickListener {
                val intent = Intent(requireContext(), PertanyaanActivity::class.java)
                startActivity(intent)
            }
            lnAbout.setOnClickListener {
                val intent = Intent(requireContext(), AboutActivity::class.java)
                startActivity(intent)
            }
            lnLogout.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                val alert = builder.create()
                builder
                    .setTitle(getString(R.string.LOGOUT_CONFIRMATION_TITLE))
                    .setMessage(getString(R.string.LOGOUT_CONFIRMATION_MESSAGE))
                    .setPositiveButton(getString(R.string.LOGOUT_CONFIRMATION_YES)) { _, _ ->
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        authViewModel.deleteUserLoginSession()
                    }
                    .setNegativeButton(getString(R.string.LOGOUT_CONFIRMATION_CANCEL)) { _, _ ->
                        alert.cancel()
                    }
                    .show()
            }
            }
        }

    private fun setupShimmerEffect(isLoading: Boolean) {
        binding.apply {
            shimmerContainer.apply {
                visibility = if (isLoading) View.VISIBLE else View.GONE
                if (isLoading) {
                    startShimmer()
                } else {
                    stopShimmer()
                }
            }
            contentContainer.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
