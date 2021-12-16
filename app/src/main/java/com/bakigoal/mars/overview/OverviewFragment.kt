package com.bakigoal.mars.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bakigoal.mars.R
import com.bakigoal.mars.databinding.FragmentOverviewBinding
import com.bakigoal.mars.network.MarsApiFilter

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel by lazy { ViewModelProvider(this)[OverviewViewModel::class.java] }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        observeViewModel()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.navigateToSelectedPropertyComplete()
            }
        })
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.show_all_menu -> viewModel.updateFilter(MarsApiFilter.SHOW_ALL)
            R.id.show_buy_menu -> viewModel.updateFilter(MarsApiFilter.SHOW_BUY)
            R.id.show_rent_menu -> viewModel.updateFilter(MarsApiFilter.SHOW_RENT)
        }

        return super.onOptionsItemSelected(item)
    }
}
