package com.amzi.codebase.utility

import android.util.Log
import androidx.fragment.app.Fragment
import com.amzi.codebase.MainActivity
import com.tillagewireless.ss.data.network.Resource

fun handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
//  Log.d(MainActivity.TAG,"Response FAILED $failure")
    when {
        failure.isNetworkError -> {

        }
        failure.errorCode == 401 -> {

        }
        else -> {
            val error = failure.errorBody?.string().toString()
        }
    }
}

