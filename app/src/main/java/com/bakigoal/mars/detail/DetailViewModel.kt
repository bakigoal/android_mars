package com.bakigoal.mars.detail

import android.app.Application
import androidx.lifecycle.*
import com.bakigoal.mars.R
import com.bakigoal.mars.detail.DetailFragment
import com.bakigoal.mars.network.MarsProperty

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(marsProperty: MarsProperty, val app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData(marsProperty)
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty
    val displayPropertyPrice: LiveData<String>
        get() = Transformations.map(selectedProperty) {
            app.applicationContext.getString(
                when (it.isRental) {
                    true -> R.string.display_price_monthly_rental
                    false -> R.string.display_price
                },
                it.price
            )
        }
    val displayPropertyType: LiveData<String>
        get() = Transformations.map(selectedProperty) {
            app.applicationContext.getString(
                R.string.display_type,
                if (it.isRental) "RENT" else "SALE"
            )
        }

}
