package com.bakigoal.mars.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.bakigoal.mars.detail.DetailFragment
import com.bakigoal.mars.network.MarsProperty

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(@Suppress("UNUSED_PARAMETER")marsProperty: MarsProperty, app: Application) : AndroidViewModel(app) {
}
