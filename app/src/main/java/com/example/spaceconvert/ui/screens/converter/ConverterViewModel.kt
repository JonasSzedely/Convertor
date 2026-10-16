package com.example.spaceconvert.ui.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spaceconvert.domain.model.Calculation
import com.example.spaceconvert.domain.model.ConverterType
import com.example.spaceconvert.domain.repository.CalculatorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConversionResult(
    val unit: ConverterType,
    val value: Double?
)

data class ConverterUiState(
    val inputText: String = "1",
    val selectedUnit: ConverterType? = null,
    val units: List<ConverterType> = emptyList(),
    val results: List<ConversionResult> = emptyList(),
    val isInputError: Boolean = false
)

class ConverterViewModel(
    private val repository: CalculatorRepository,
    private val defaultUnitId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConverterUiState())
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeUnits().collect { units ->
                _uiState.update { state ->
                    state.copy(
                        units = units,
                        selectedUnit = state.selectedUnit
                            ?: units.firstOrNull { it.id == defaultUnitId }
                            ?: units.firstOrNull()
                    ).recalculate()
                }
            }
        }
    }

    fun onInputChange(text: String) {
        _uiState.update { it.copy(inputText = text).recalculate() }
    }

    fun onUnitSelected(unit: ConverterType) {
        _uiState.update { it.copy(selectedUnit = unit).recalculate() }
    }

    companion object {
        fun factory(
            repository: CalculatorRepository,
            defaultUnitId: String
        ) = viewModelFactory {
            initializer { ConverterViewModel(repository, defaultUnitId) }
        }

    }

    private fun ConverterUiState.recalculate(): ConverterUiState {
        val value = inputText.replace(',', '.').toDoubleOrNull()
        val base = selectedUnit

        return copy(
            isInputError = inputText.isNotBlank() && value == null,
            results = units.map { target ->
                ConversionResult(
                    unit = target,
                    value = if (value != null && base != null) {
                        Calculation(base, target, value).outputValue
                    } else null
                )
            }
        )
    }
}