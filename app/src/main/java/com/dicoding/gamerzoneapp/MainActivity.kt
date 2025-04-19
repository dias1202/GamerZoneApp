package com.dicoding.gamerzoneapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.dicoding.gamerzoneapp.databinding.ActivityMainBinding
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }

                R.id.navigation_favorite -> {
                    loadFavoriteFeature()
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFavoriteFeature() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)

        if (splitInstallManager.installedModules.contains("favorite")) {
            navigateToFavorite()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule("favorite")
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    binding.root.post {
                        navigateToFavorite()
                    }
                }
                .addOnFailureListener {
                    showToast("Failed to install favorite feature: ${it.message}")
                }
        }
    }

    private fun navigateToFavorite() {
        try {
            val className = "com.dicoding.gamerzoneapp.favorite.FavoriteFragment"
            val navigator =
                navController.navigatorProvider.getNavigator(FragmentNavigator::class.java)

            val destinationId = R.id.navigation_favorite_dynamic

            if (navController.graph.findNode(destinationId) == null) {
                val destination = FragmentNavigator.Destination(navigator).apply {
                    id = destinationId
                    setClassName(className)
                    label = "Favorite"
                }
                navController.graph.addDestination(destination)
            }

            navController.navigate(destinationId)

        } catch (e: ClassNotFoundException) {
            showToast("Class not found: ${e.message}")
        } catch (e: Exception) {
            showToast("Failed to open favorite: ${e.message}")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
