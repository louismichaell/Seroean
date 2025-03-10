package com.seroean.apps.ui.beranda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.KulinerViewModelFactory
import com.seroean.apps.NotifikasiViewModelFactory
import com.seroean.apps.ProfileViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.WisataViewModelFactory
import com.seroean.apps.adapter.WisataAdapter
import com.seroean.apps.adapter.KulinerAdapter
import com.seroean.apps.data.response.WisataData
import com.seroean.apps.data.response.KulinerData
import com.seroean.apps.databinding.FragmentHomeBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.budaya.BudayaActivity
import com.seroean.apps.ui.detail.DetailKulinerActivity
import com.seroean.apps.ui.detail.DetailWisataActivity
import com.seroean.apps.ui.kuliner.KulinerActivity
import com.seroean.apps.ui.kuliner.KulinerViewModel
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.loadImage
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.notifikasi.NotifikasiActivity
import com.seroean.apps.ui.notifikasi.NotifikasiViewModel
import com.seroean.apps.ui.notifikasi.NotifikasiWorker
import com.seroean.apps.ui.profile.ProfileViewModel
import com.seroean.apps.ui.sejarah.SejarahActivity
import com.seroean.apps.ui.topkuliner.TopKulinerActivity
import com.seroean.apps.ui.topwisata.TopWisataActivity
import com.seroean.apps.ui.topwisata.WisataViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String
    private lateinit var wisataAdapter: WisataAdapter
    private lateinit var kulinerAdapter: KulinerAdapter

    private val wisataViewModel: WisataViewModel by lazy {
        ViewModelProvider(
            this,
            WisataViewModelFactory(requireContext())
        )[WisataViewModel::class.java]
    }

    private val kulinerViewModel: KulinerViewModel by lazy {
        ViewModelProvider(
            this,
            KulinerViewModelFactory(requireContext())
        )[KulinerViewModel::class.java]
    }

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileViewModelFactory(requireContext())
        )[ProfileViewModel::class.java]
    }

    private val notifikasiViewModel: NotifikasiViewModel by lazy {
        ViewModelProvider(
            this, NotifikasiViewModelFactory(requireContext())
        )[NotifikasiViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lightStatusBar(requireActivity().window)
        preferences = SettingsPreferences.getInstance(requireContext().dataStore)
        wisataAdapter = WisataAdapter()
        kulinerAdapter = KulinerAdapter()

        _binding = FragmentHomeBinding.bind(view)
        setupShimmerEffect(true)
        wisataAdapter.showShimmerPlaceholder()
        clickButoon()
        setupSearch()
        setupRecyclerView()

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(viewLifecycleOwner) { userToken ->
            token = userToken
            profileViewModel.getBiodata(token)
            wisataViewModel.getWisata(token)
            kulinerViewModel.getKuliner(token)
            NotifikasiWorker.scheduleWorker(requireContext(), token)
        }

        notifikasiViewModel.notifikasi.observe(viewLifecycleOwner) { notifikasiList ->
            updateNotificationBadge(notifikasiList.size)
        }

        wisataViewModel.wisata.observe(viewLifecycleOwner) { wisataList ->
            if (wisataList.isNotEmpty()) {
                setupShimmerEffect(false)
                setWisataData(wisataList)
            }
        }

        kulinerViewModel.kuliner.observe(viewLifecycleOwner) { kulinerList ->
            if (kulinerList.isNotEmpty()) {
                setupShimmerEffect(false)
                setKulinerData(kulinerList)
            }
        }

        profileViewModel.biodata.observe(viewLifecycleOwner) { biodata ->
            if (biodata != null) {
                binding.apply {
                    tvHiNama.text = getString(R.string.halo) + " " + biodata.name
                    Profile.loadImage(biodata.profilePicture)
                }
            }
        }

        wisataAdapter.setOnItemClickCallback(object : WisataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: WisataData) {
                val intent = Intent(requireContext(), DetailWisataActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_WISATA_ID, data.wisataId)
                startActivity(intent)
            }
        })

        kulinerAdapter.setOnItemClickCallback(object : KulinerAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KulinerData) {
                val intent = Intent(requireContext(), DetailKulinerActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_KULINER_ID, data.kulinerId)
                startActivity(intent)
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvWisata.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = wisataAdapter
        }

        binding.rvKuliner.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = kulinerAdapter
        }
    }

    private fun setupSearch() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                filterWisataKuliner(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterWisataKuliner(query: String) {
        val filteredWisata = wisataViewModel.wisata.value?.filter {
            it.nama.contains(query, ignoreCase = true) || it.lokasi.contains(query, ignoreCase = true)
        } ?: emptyList()

        val filteredKuliner = kulinerViewModel.kuliner.value?.filter {
            it.nama.contains(query, ignoreCase = true) || it.lokasi.contains(query, ignoreCase = true)
        } ?: emptyList()

        wisataAdapter.setData(filteredWisata)
        kulinerAdapter.setData(filteredKuliner)
    }


    private fun clickButoon(){
        binding.apply {
            lnBudaya.setOnClickListener{
                val intent = Intent(requireContext(), BudayaActivity::class.java)
                startActivity(intent)
            }
            lnSejarah.setOnClickListener{
                val intent = Intent(requireContext(), SejarahActivity::class.java)
                startActivity(intent)
            }
            lnWisata.setOnClickListener{
                val intent = Intent(requireContext(), TopWisataActivity::class.java)
                startActivity(intent)
            }
            lnKuliner.setOnClickListener{
                val intent = Intent(requireContext(), TopKulinerActivity::class.java)
                startActivity(intent)
            }
            notifikasi.setOnClickListener {
                val intent = Intent(requireContext(), NotifikasiActivity::class.java)
                startActivity(intent)
            }
            tvLainnya.setOnClickListener{
                val intent = Intent(requireContext(), KulinerActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun setWisataData(wisataList: List<WisataData>) {
        if (::wisataAdapter.isInitialized) {
            wisataAdapter.setData(wisataList)
            binding.rvWisata.visibility = View.VISIBLE
        }
    }

    private fun setKulinerData(kulinerList: List<KulinerData>) {
        if (::kulinerAdapter.isInitialized) {
            kulinerAdapter.setData(kulinerList)
            binding.rvKuliner.visibility = View.VISIBLE
        }
    }

    private fun setupShimmerEffect(isLoading: Boolean) {
        binding.apply {
            shimmerContainer.apply {
                visibility = if (isLoading) View.VISIBLE else View.GONE
                if (isLoading) startShimmer() else stopShimmer()
            }
            contentContainer.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun updateNotificationBadge(count: Int) {
        if (count > 0) {
            binding.badgeNotification.visibility = View.VISIBLE
        } else {
            binding.badgeNotification.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
