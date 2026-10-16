package com.example.spaceconvert.ui.screens.converter

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.example.spaceconvert.R
import com.example.spaceconvert.domain.model.ConverterType
import com.example.spaceconvert.domain.model.MeasurementConverterTypeImpl
import com.example.spaceconvert.ui.theme.SpaceConvertTheme

/**
 * Numeric input field combined with a unit selector.
 *
 * Displays an [OutlinedTextField] for entering a decimal value. A trailing
 * button shows the currently selected unit and opens a scrollable dropdown
 * listing all available [units].
 *
 * Keyboard behaviour:
 * - Tapping the field toggles the software keyboard: the first tap opens it,
 *   the next tap hides it, and so on.
 * - The keyboard is hidden with [androidx.compose.ui.platform.SoftwareKeyboardController.hide]
 *   instead of clearing focus, so the IME keeps the decimal layout and does
 *   not briefly flash a text keyboard while closing.
 * - Opening the unit dropdown hides the keyboard; selecting a unit shows it again.
 *
 * @param value The current text of the input field.
 * @param onValueChange Called whenever the user edits the text.
 * @param selectedUnit The currently selected unit, or `null` if none is selected
 *   (a dash is shown in that case).
 * @param units All units the user can choose from in the dropdown.
 * @param onUnitSelected Called with the unit the user picked from the dropdown.
 * @param isError Whether the field should be displayed in its error state,
 *   e.g. when the input cannot be parsed as a number.
 * @param modifier [Modifier] applied to the text field.
 */
@Composable
fun ConverterInputField(
    value: String,
    onValueChange: (String) -> Unit,
    selectedUnit: ConverterType?,
    units: List<ConverterType>,
    onUnitSelected: (ConverterType) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    // Whether the unit dropdown is currently open.
    var expanded by remember { mutableStateOf(false) }
    // The dropdown may take up at most half of the window height.
    val windowHeightPx = LocalWindowInfo.current.containerSize.height
    val maxMenuHeight = with(LocalDensity.current) { (windowHeightPx * 0.5f).toDp() }

    val keyboardController = LocalSoftwareKeyboardController.current

    // Tracks focus so the keyboard is only re-shown for an already focused field.
    var isFocused by remember { mutableStateOf(false) }

    // Hoisted interaction source used to observe taps on the text field.
    val interactionSource = remember { MutableInteractionSource() }
    val density = LocalDensity.current
    // True while the software keyboard occupies space at the bottom of the window.
    val imeVisible by rememberUpdatedState(WindowInsets.ime.getBottom(density) > 0)

    // Keyboard visibility captured at press time; focus changes during the tap,
    // so checking on release alone would always report the keyboard as open.
    var imeVisibleOnPress by remember { mutableStateOf(false) }

    // Toggles the keyboard on every completed tap on the text field.
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> imeVisibleOnPress = imeVisible
                is PressInteraction.Release -> {
                    if (imeVisibleOnPress) {
                        keyboardController?.hide()
                    } else if (isFocused) {
                        keyboardController?.show()
                    }
                }
            }
        }
    }


    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused },
        label = { Text(stringResource(R.string.converter_input_label)) },
        singleLine = true,
        isError = isError,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        trailingIcon = {
            Box {
                TextButton(
                    onClick = {
                        keyboardController?.hide()
                        expanded = true
                    },
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .widthIn(max = 160.dp)
                ) {
                    Text(
                        text = selectedUnit?.displayName() ?: "–",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = stringResource(R.string.cd_select_unit)
                    )
                }
                if (expanded) {
                    BackHandler { expanded = false }
                    Popup(
                        popupPositionProvider = BelowAnchorPositionProvider,
                        onDismissRequest = { expanded = false },
                        properties = PopupProperties(
                            focusable = false,
                            dismissOnClickOutside = true
                        )
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.extraSmall,
                            color = MaterialTheme.colorScheme.surface,
                            shadowElevation = 3.dp,
                            tonalElevation = 3.dp,
                            modifier = Modifier.heightIn(max = maxMenuHeight)
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(IntrinsicSize.Max)
                                    .verticalScroll(rememberScrollState())
                                    .padding(vertical = 8.dp)
                            ) {
                                units.forEach { unit ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = if (unit.symbol.isNotBlank()) {
                                                    "${unit.displayName()} (${unit.symbol})"
                                                } else {
                                                    unit.displayName()
                                                }
                                            )
                                        },
                                        onClick = {
                                            onUnitSelected(unit)
                                            expanded = false
                                            keyboardController?.show()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

/**
 * Positions a popup directly below its anchor, aligned to the anchor's right edge.
 *
 * Used for the unit dropdown so that it opens underneath the unit button
 * inside the text field's trailing area. The popup is shifted left as far
 * as needed but never past the left edge of the window.
 */
private object BelowAnchorPositionProvider : PopupPositionProvider {
    /**
     * Calculates the top-left position of the popup in window coordinates.
     *
     * @param anchorBounds Bounds of the anchor (the unit button) in window coordinates.
     * @param windowSize Size of the window the popup is shown in.
     * @param layoutDirection Current layout direction (not used, the popup is always right-aligned).
     * @param popupContentSize Measured size of the popup content.
     * @return The offset at which the popup's top-left corner should be placed.
     */
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val x = (anchorBounds.right - popupContentSize.width).coerceAtLeast(0)
        val y = anchorBounds.bottom
        return IntOffset(x, y)
    }
}

/**
 * Preview of [ConverterInputField] with metre selected and a sample value.
 */
@Preview(showBackground = true)
@Composable
private fun ConverterInputFieldPreview() {
    SpaceConvertTheme {
        ConverterInputField(
            value = "1,5",
            onValueChange = {},
            selectedUnit = MeasurementConverterTypeImpl.METER,
            units = MeasurementConverterTypeImpl.entries,
            onUnitSelected = {},
            isError = false
        )
    }
}