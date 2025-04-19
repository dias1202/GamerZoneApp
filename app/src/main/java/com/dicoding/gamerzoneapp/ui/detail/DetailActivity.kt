package com.dicoding.gamerzoneapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.GameDetail
import com.dicoding.core.utils.formatDate
import com.dicoding.gamerzoneapp.R
import com.dicoding.gamerzoneapp.databinding.ActivityDetailBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()

    private var favoriteMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        detailViewModel.gameDetail.observe(this) {
            updateFavoriteIcon()
        }

        val extras = intent.extras
        if (extras != null) {
            val gameId = extras.getInt(EXTRA_DATA)
            if (gameId != 0) {
                val gameDetailData = detailViewModel.getGameDetail(gameId)
                gameDetailData.observe(this) { game ->
                    when (game) {
                        is Resource.Error -> {
                            binding.contentGameDetail.progressCircular.visibility =
                                View.GONE
                            showToast(getString(R.string.something_wrong))
                        }

                        is Resource.Loading -> {
                            binding.contentGameDetail.progressCircular.visibility =
                                View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.contentGameDetail.progressCircular.visibility = View.GONE
                            game.data?.let {
                                detailViewModel.setGame(it)
                                showDetailGame(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showDetailGame(gameDetail: GameDetail) {

        val formattedDate = formatDate(gameDetail.released)

        with(binding) {
            contentGameDetail.gameName.text = gameDetail.name
            contentGameDetail.gameDescription.text = HtmlCompat.fromHtml(gameDetail.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            contentGameDetail.gameReleased.text = getString(R.string.released, formattedDate)
            contentGameDetail.gameRatingStar.rating = gameDetail.rating.toFloat()
        }

        showPlatforms(gameDetail.platforms)

        Glide.with(this)
            .load(gameDetail.backgroundImage)
            .into(binding.contentGameDetail.gameImage)
    }

    private fun showPlatforms(platforms: List<String?>) {
        val chipGroup = binding.contentGameDetail.gamePlatform
        chipGroup.removeAllViews()

        for (platform in platforms) {
            val chip = Chip(this).apply {
                text = platform
                isCheckable = false
                isClickable = false
            }
            chipGroup.addView(chip)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        favoriteMenuItem = menu.findItem(R.id.favorite_toggle)
        updateFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_toggle -> {
                val currentFavorite = detailViewModel.gameDetail.value?.isFavorite == true
                detailViewModel.setFavoriteGame()
                val message = if (currentFavorite) {
                    getString(R.string.removed_from_favorite)
                } else {
                    getString(R.string.added_to_favorite)
                }
                showToast(message)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateFavoriteIcon() {
        val isFavorite = detailViewModel.gameDetail.value?.isFavorite == true
        favoriteMenuItem?.setIcon(
            if (isFavorite) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}