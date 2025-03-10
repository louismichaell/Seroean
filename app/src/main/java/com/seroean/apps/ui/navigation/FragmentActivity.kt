package com.seroean.apps.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.seroean.apps.R
import com.seroean.apps.databinding.ActivityFragmentBinding

class FragmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHomeFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = navHomeFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_menu)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)
    }
}