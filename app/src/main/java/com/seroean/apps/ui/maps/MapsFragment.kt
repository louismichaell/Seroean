package com.seroean.apps.ui.maps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.MapsViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.data.response.KulinerData
import com.seroean.apps.data.response.WisataData
import com.seroean.apps.databinding.DialogItemsMapsBinding
import com.seroean.apps.databinding.FragmentMapsBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.convertBitmap
import com.seroean.apps.ui.customToast
import com.seroean.apps.ui.detail.DetailKulinerActivity
import com.seroean.apps.ui.detail.DetailWisataActivity
import com.seroean.apps.ui.login.dataStore
import www.sanju.motiontoast.MotionToastStyle
import java.util.Locale

@Suppress("DEPRECATION")
class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.InfoWindowAdapter {
    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var boundsBuilder = LatLngBounds.Builder()
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SettingsPreferences
    private var token: String = ""
    private lateinit var viewModel: MapsViewModel
    private var searchHandler = Handler(Looper.getMainLooper())
    private var lastSearchRunnable: Runnable? = null
    private var currentSearchMarker: Marker? = null

    private val bangkabelitungbonds = LatLngBounds(
        LatLng(-3.3, 105.0),
        LatLng(-1.5, 108.0)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        preferences = SettingsPreferences.getInstance(requireContext().dataStore)
        viewModel = ViewModelProvider(this, MapsViewModelFactory(requireContext()))[MapsViewModel::class.java]

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(viewLifecycleOwner) { userToken ->
            if (!userToken.isNullOrEmpty()) {
                token = userToken
                viewModel.getWisata(token)
                viewModel.getKuliner(token)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        setupSearch()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap?.setInfoWindowAdapter(this)

        mMap?.setLatLngBoundsForCameraTarget(bangkabelitungbonds)
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(bangkabelitungbonds.center, 2f))

        mMap?.uiSettings?.apply {
            isCompassEnabled = true
            isMapToolbarEnabled = true
            isZoomControlsEnabled = true
        }

        if (token.isNotEmpty()) {
            viewModel.getWisata(token)
            viewModel.getKuliner(token)
        }

        viewModel.wisata.observe(viewLifecycleOwner) { wisataList ->
            if (wisataList != null) setMarkerWisata(wisataList)
        }

        viewModel.kuliner.observe(viewLifecycleOwner) { kulinerList ->
            if (kulinerList != null) setMarkerKuliner(kulinerList)
        }

        mMap?.setOnInfoWindowClickListener { marker ->
            when (val data = marker.tag) {
                is WisataData -> {
                    val intent = Intent(requireContext(), DetailWisataActivity::class.java).apply {
                        putExtra(VARIABEL.EXTRA_WISATA_ID, data.wisataId)
                    }
                    startActivity(intent)
                }
                is KulinerData -> {
                    val intent = Intent(requireContext(), DetailKulinerActivity::class.java).apply {
                        putExtra(VARIABEL.EXTRA_KULINER_ID, data.kulinerId)
                    }
                    startActivity(intent)
                }
                else -> {

                }
            }
        }
    }

    private fun setMarkerWisata(data: List<WisataData>) {
        mMap?.let { map ->
            data.forEach { wisata ->
                val latLng = LatLng(wisata.lat ?: 0.0, wisata.lon ?: 0.0)
                map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(wisata.nama)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                )?.tag = wisata
                boundsBuilder.include(latLng)
            }
            adjustCamera()
        }
    }

    private fun setupSearch() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lastSearchRunnable?.let { searchHandler.removeCallbacks(it) }
                lastSearchRunnable = Runnable {
                    val query = s.toString().trim()
                    if (query.isNotEmpty()) {
                        searchLocation(query)
                    }
                }
                searchHandler.postDelayed(lastSearchRunnable!!, 500)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchLocation(location: String) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocationName(location, 1)
            if (!addresses.isNullOrEmpty()) {
                val latLng = LatLng(addresses[0].latitude, addresses[0].longitude)

                currentSearchMarker?.remove()

                currentSearchMarker = mMap?.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(location)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )

                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            } else {
                customToast(getString(R.string.maps), getString(R.string.notfoundmaps), MotionToastStyle.ERROR)
            }
        } catch (e: Exception) {
            customToast(getString(R.string.maps), getString(R.string.notfoundmaps), MotionToastStyle.ERROR)
        }
    }



    private fun setMarkerKuliner(data: List<KulinerData>) {
        mMap?.let { map ->
            data.forEach { kuliner ->
                val latLng = LatLng(kuliner.lat ?: 0.0, kuliner.lon ?: 0.0)
                map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(kuliner.nama)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )?.tag = kuliner
                boundsBuilder.include(latLng)
            }
            adjustCamera()
        }
    }

    private fun adjustCamera() {
        mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bangkabelitungbonds, 10))
    }

    @SuppressLint("SetTextI18n")
    override fun getInfoWindow(marker: Marker): View {
        val toolTips = DialogItemsMapsBinding.inflate(LayoutInflater.from(requireContext()))

        when (val data = marker.tag) {
            is WisataData -> {
                toolTips.tvNama.text = getAddressFromLatLng(requireContext(), marker.position.latitude, marker.position.longitude)
                toolTips.tvNama.text = data.nama
                toolTips.ivLocationImage.setImageBitmap(convertBitmap(requireContext(), data.foto))
                toolTips.tvAlamat.text = data.lokasi
                toolTips.tvRatingCount.text = data.rating
                toolTips.tvSubProvinsitxt.text = data.provinsi
            }
            is KulinerData -> {
                toolTips.tvNama.text = getAddressFromLatLng(requireContext(), marker.position.latitude, marker.position.longitude)
                toolTips.tvNama.text = data.nama
                toolTips.ivLocationImage.setImageBitmap(convertBitmap(requireContext(), data.foto))
                toolTips.tvAlamat.text = data.lokasi
                toolTips.tvRatingCount.text= data.rating.toString()
                toolTips.tvSubProvinsitxt.text = data.provinsi
            }
            else -> {
                toolTips.tvNama.text = "Tidak Tersedia"
                toolTips.tvAlamat.text = "Tidak Tersedia"
                toolTips.tvRatingCount.text= "Tidak Tersedia"
                toolTips.tvSubProvinsitxt.text = "Tidak Tersedia"
            }
        }

        return toolTips.root
    }

    private fun getAddressFromLatLng(context: Context, lat: Double, lon: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, lon, 1)

            if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0)
            } else {
                "Lokasi tidak ditemukan"
            }
        } catch (e: Exception) {
            "Gagal mendapatkan alamat"
        }
    }

    override fun getInfoContents(marker: Marker): View {
        return View(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
