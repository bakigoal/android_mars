package com.bakigoal.mars.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bakigoal.mars.databinding.FragmentDetailBinding
import com.bakigoal.mars.network.MarsProperty

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val viewModel = detailViewModel(DetailFragmentArgs.fromBundle(requireArguments()).selected)
        binding.detailViewModel = viewModel

        return binding.root
    }

    private fun detailViewModel(selected: MarsProperty): DetailViewModel {
        val viewModelFactory = DetailViewModelFactory(selected, requireActivity().application)
        return ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
    }
}
