package com.example.spaceconvert.ui.screens.converter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spaceconvert.domain.model.Calculation
import com.example.spaceconvert.domain.model.ConverterType
import com.example.spaceconvert.domain.model.MeasurementConverterTypeImpl
import com.example.spaceconvert.ui.theme.SpaceConvertTheme

/** Connects the ViewModel to the layout. */
@Composable
fun ConverterScreen(viewModel: ConverterViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ConverterContent(
        state = state,
        onInputChange = viewModel::onInputChange,
        onUnitSelected = viewModel::onUnitSelected
    )
}

/** The actual layout. Has no knowledge of a ViewModel*/
@Composable
fun ConverterContent(
    state: ConverterUiState,
    onInputChange: (String) -> Unit,
    onUnitSelected: (ConverterType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp)
    ) {
        ConverterInputField(
            value = state.inputText,
            onValueChange = onInputChange,
            selectedUnit = state.selectedUnit,
            units = state.units,
            onUnitSelected = onUnitSelected,
            isError = state.isInputError
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = state.results,
                key = { it.unit.id }
            ) { result ->
                ResultRow(result)
            }
        }
    }
}

@Composable
private fun ResultRow(
    result: ConversionResult,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (result.unit.symbol.isNotBlank()) {
                    "${result.unit.displayName()} (${result.unit.symbol})"
                } else {
                    result.unit.displayName()
                },
                modifier = Modifier.weight(1f)
            )
            Text(
                text = result.value?.formatResult() ?: "–",
                style = MaterialTheme.typography.bodyLarge.copy(fontFeatureSettings = "tnum")
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConverterContentPreview() {
    val units = MeasurementConverterTypeImpl.entries
    val base = MeasurementConverterTypeImpl.METER
    SpaceConvertTheme {
        ConverterContent(
            state = ConverterUiState(
                inputText = "1",
                selectedUnit = base,
                units = units,
                results = units.map {
                    ConversionResult(it, Calculation(base, it, 1.0).outputValue)
                }
            ),
            onInputChange = {},
            onUnitSelected = {}
        )
    }
}