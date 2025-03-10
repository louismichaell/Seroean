package com.seroean.apps.ui.budaya

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.BudayaViewModelFactory
import com.seroean.apps.SejarahViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.adapter.BudayaAdapter
import com.seroean.apps.adapter.SejarahAdapter
import com.seroean.apps.adapter.ImageSliderAdapter
import com.seroean.apps.data.response.BudayaData
import com.seroean.apps.data.response.SejarahData
import com.seroean.apps.data.response.ImageDataResponse
import com.seroean.apps.databinding.FragmentBudayaMainBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.detail.DetailBudayaActivity
import com.seroean.apps.ui.detail.DetailSejarahActivity
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.sejarah.SejarahViewModel
import com.seroean.apps.ui.slideInterval

@Suppress("DEPRECATION")
class BudayaFragment : Fragment(R.layout.fragment_budaya_main) {

    private var _binding: FragmentBudayaMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var budayaAdapter: BudayaAdapter
    private lateinit var sejarahAdapter: SejarahAdapter
    private lateinit var adapter: ImageSliderAdapter
    private val list = ArrayList<ImageDataResponse>()
    private lateinit var dots: ArrayList<TextView>
    private var currentPage = 0
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String

    private val budayaViewModel: BudayaViewModel by lazy {
        ViewModelProvider(
            this,
            BudayaViewModelFactory(requireContext())
        )[BudayaViewModel::class.java]
    }

    private val sejarahViewModel: SejarahViewModel by lazy {
        ViewModelProvider(
            this,
            SejarahViewModelFactory(requireContext())
        )[SejarahViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lightStatusBar(requireActivity().window)

        _binding = FragmentBudayaMainBinding.bind(view)
        setupShimmerEffect(true)

        preferences = SettingsPreferences.getInstance(requireContext().dataStore)
        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(viewLifecycleOwner) { userToken ->
            token = userToken
            budayaViewModel.getBudaya(token)
            sejarahViewModel.getSejarah(token)
        }

        budayaAdapter = BudayaAdapter()
        sejarahAdapter = SejarahAdapter()

        setupRecyclerView()
        setupViewPager()

        budayaAdapter.showShimmerPlaceholder()
        sejarahAdapter.showShimmerPlaceholder()

        budayaViewModel.budaya.observe(viewLifecycleOwner) { budayaList ->
            if (budayaList.isNotEmpty()) {
                setBudayaData(budayaList)
                setupShimmerEffect(false)
            }
        }

        sejarahViewModel.sejarah.observe(viewLifecycleOwner) { sejarahList ->
            if (sejarahList.isNotEmpty()) {
                setSejarahData(sejarahList)
                setupShimmerEffect(false)
            }
        }

        budayaAdapter.setOnItemClickCallback(object : BudayaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BudayaData) {
                val intent = Intent(requireContext(), DetailBudayaActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_BUDAYA_ID, data.budayaId)
                startActivity(intent)
            }
        })

        sejarahAdapter.setOnItemClickCallback(object : SejarahAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SejarahData) {
                val intent = Intent(requireContext(), DetailSejarahActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_SEJARAH_ID, data.sejarahId)
                startActivity(intent)
            }
        })
        binding.tvLainnya.setOnClickListener{
            val intent = Intent(requireContext(), BudayaActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupRecyclerView() {
        binding.rvBudaya.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = budayaAdapter
        }

        binding.rvSejarah.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = sejarahAdapter
        }
    }

    private fun setBudayaData(budayaList: List<BudayaData>) {
        if (::budayaAdapter.isInitialized) {
            budayaAdapter.setData(budayaList)
            binding.rvBudaya.visibility = View.VISIBLE
        }
    }

    private fun setSejarahData(sejarahList: List<SejarahData>) {
        if (::sejarahAdapter.isInitialized) {
            sejarahAdapter.setData(sejarahList)
            binding.rvSejarah.visibility = View.VISIBLE
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

    private fun setupViewPager() {
        list.clear()

        list.add(ImageDataResponse("https://storage.googleapis.com/seroean-buckets/banner/Banner-1.png"))
        list.add(ImageDataResponse("https://storage.googleapis.com/seroean-buckets/banner/Banner-2.png"))
        list.add(ImageDataResponse("https://storage.googleapis.com/seroean-buckets/banner/Banner-3.jpg"))
        list.add(ImageDataResponse("https://storage.googleapis.com/seroean-buckets/banner/Banner4.jpg"))

        adapter = ImageSliderAdapter(list)
        binding.viewPager.adapter = adapter
        dots = ArrayList()
        setIndicator()

        runnable = object : Runnable {
            override fun run() {
                if (currentPage >= list.size) {
                    currentPage = 0
                }
                binding.viewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, slideInterval)
            }
        }

        handler.postDelayed(runnable, slideInterval)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                currentPage = position
                super.onPageSelected(position)
            }
        })
    }

    private fun selectedDot(position: Int) {
        val selectedColor = ContextCompat.getColor(requireActivity(), R.color.deepbluedark)
        val unselectedColor = ContextCompat.getColor(requireActivity(), R.color.white_transparent)
        for (i in dots.indices) {
            dots[i].setTextColor(if (i == position) selectedColor else unselectedColor)
        }
    }

    private fun setIndicator() {
        binding.dotsIndicator.removeAllViews()
        for (i in list.indices) {
            val dot = TextView(requireActivity())
            dot.text = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY)
            dot.textSize = 30f
            dot.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white_transparent))

            dots.add(dot)
            binding.dotsIndicator.addView(dot)
        }
        selectedDot(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
        _binding = null
    }
}
