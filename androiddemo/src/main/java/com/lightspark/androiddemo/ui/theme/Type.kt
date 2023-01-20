package com.lightspark.androiddemo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lightspark.androiddemo.R

val Montserrat = FontFamily(
    listOf(
        androidx.compose.ui.text.font.Font(
            resId = R.font.montserrat_medium,
            weight = FontWeight.Normal
        ),
        androidx.compose.ui.text.font.Font(
            resId = R.font.montserrat_medium,
            weight = FontWeight.Medium
        ),
        androidx.compose.ui.text.font.Font(
            resId = R.font.montserrat_semi_bold,
            weight = FontWeight.SemiBold
        ),
        androidx.compose.ui.text.font.Font(
            resId = R.font.montserrat_bold,
            weight = FontWeight.Bold
        ),
        androidx.compose.ui.text.font.Font(
            resId = R.font.montserrat_bold,
            weight = FontWeight.ExtraBold
        ),
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp,
        lineHeight = 28.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 28.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        lineHeight = 22.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = Color(102, 102, 102)
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        color = Color(102, 102, 102)
    )
)