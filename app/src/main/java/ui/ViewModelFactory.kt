package com.asdec.hauling.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asdec.hauling.data.HaulingRepository

class ViewModelFactory(private val repository: HaulingRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // Handle MainViewModel
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                MainViewModel(repository) as T
            }

            // Handle AdminViewModel (The missing piece causing the crash)
            modelClass.isAssignableFrom(AdminViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                AdminViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}