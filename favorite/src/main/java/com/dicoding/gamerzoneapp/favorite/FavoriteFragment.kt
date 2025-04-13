package com.dicoding.gamerzoneapp.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.ui.GameAdapter
import com.dicoding.gamerzoneapp.favorite.databinding.FragmentFavoriteBinding
import com.dicoding.gamerzoneapp.ui.detail.DetailActivity

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(requireContext())
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val gameAdapter = GameAdapter()

            gameAdapter.onItemClick = { selectedItem ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedItem.id)
                startActivity(intent)
            }

            favoriteViewModel.favoriteGames.observe(viewLifecycleOwner) { games ->
                gameAdapter.submitList(games)
                binding.viewEmpty.root.visibility =
                    if (games.isNotEmpty()) View.GONE else View.VISIBLE
            }

            with(binding.rvGame) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = gameAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
