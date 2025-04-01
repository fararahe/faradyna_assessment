package com.faradyna.assessment.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.faradyna.assessment.R

// Define the font families
val interRegular = FontFamily(
    Font(R.font.inter_regular)
)

val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = interRegular),
    displayMedium = baseline.displayMedium.copy(fontFamily = interRegular),
    displaySmall = baseline.displaySmall.copy(fontFamily = interRegular),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = interRegular),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = interRegular),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = interRegular),
    titleLarge = baseline.titleLarge.copy(fontFamily = interRegular),
    titleMedium = baseline.titleMedium.copy(fontFamily = interRegular),
    titleSmall = baseline.titleSmall.copy(fontFamily = interRegular),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = interRegular),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = interRegular),
    bodySmall = baseline.bodySmall.copy(fontFamily = interRegular),
    labelLarge = baseline.labelLarge.copy(fontFamily = interRegular),
    labelMedium = baseline.labelMedium.copy(fontFamily = interRegular),
    labelSmall = baseline.labelSmall.copy(fontFamily = interRegular),
)