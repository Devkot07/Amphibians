package com.example.amphibians.ui.screens

import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication


import com.example.amphibians.model.Amphibians
import com.example.amphibians.data.AmphibiansRepository
import retrofit2.HttpException
import java.io.IOException

sealed interface AmphibiansUiState {

    data class Success(val amphibians: List<Amphibians>) : AmphibiansUiState
    object Error : AmphibiansUiState
    object Loading : AmphibiansUiState
}

class AmphibiansViewModel(private val amphibiansRepository: AmphibiansRepository): ViewModel() {
    var amphibiansUiState : AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    init {
        getAmphibiansInfo()
    }

    fun getAmphibiansInfo() {

        viewModelScope.launch{
            amphibiansUiState = AmphibiansUiState.Loading
            amphibiansUiState = try {
                val result = amphibiansRepository.getAmphibiansInfo()
                AmphibiansUiState.Success(result)
            }
            catch (e: IOException) {
                AmphibiansUiState.Error
            } catch (e: HttpException) {
                AmphibiansUiState.Error
            }
        }

         //amphibiansRepository.getAmphibiansInfo()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }

}