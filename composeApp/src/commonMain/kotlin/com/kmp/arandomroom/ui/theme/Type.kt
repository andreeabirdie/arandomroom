package com.kmp.arandomroom.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import arandomroom.composeapp.generated.resources.Res
import arandomroom.composeapp.generated.resources.redhatdisplay_black
import arandomroom.composeapp.generated.resources.redhatdisplay_blackitalic
import arandomroom.composeapp.generated.resources.redhatdisplay_bold
import arandomroom.composeapp.generated.resources.redhatdisplay_bolditalic
import arandomroom.composeapp.generated.resources.redhatdisplay_extrabold
import arandomroom.composeapp.generated.resources.redhatdisplay_extrabolditalic
import arandomroom.composeapp.generated.resources.redhatdisplay_italic
import arandomroom.composeapp.generated.resources.redhatdisplay_light
import arandomroom.composeapp.generated.resources.redhatdisplay_lightitalic
import arandomroom.composeapp.generated.resources.redhatdisplay_medium
import arandomroom.composeapp.generated.resources.redhatdisplay_mediumitalic
import arandomroom.composeapp.generated.resources.redhatdisplay_regular
import arandomroom.composeapp.generated.resources.redhatdisplay_semibold
import arandomroom.composeapp.generated.resources.redhatdisplay_semibolditalic
import org.jetbrains.compose.resources.Font

val baseline = Typography()

@Composable
fun RedHatFontFamily() = FontFamily(
    Font(Res.font.redhatdisplay_regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.redhatdisplay_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.redhatdisplay_light, FontWeight.Light, FontStyle.Normal),
    Font(Res.font.redhatdisplay_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.redhatdisplay_medium, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.redhatdisplay_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.redhatdisplay_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.redhatdisplay_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.redhatdisplay_bold, FontWeight.Bold, FontStyle.Normal),
    Font(Res.font.redhatdisplay_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.redhatdisplay_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(Res.font.redhatdisplay_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.redhatdisplay_black, FontWeight.Black, FontStyle.Normal),
    Font(Res.font.redhatdisplay_blackitalic, FontWeight.Black, FontStyle.Italic)
)

@Composable
fun AppTypography() = Typography().run {
    val fontFamily = RedHatFontFamily()
    copy(
        displayLarge = baseline.displayLarge,
        displayMedium = baseline.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = fontFamily)
    )
}


