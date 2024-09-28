package com.amzi.codebase.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.amzi.codebase.R


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val kanit = FontFamily(
    Font(R.font.kanitregular,
        FontWeight.SemiBold)
)

val kanitText = TextStyle(
    fontFamily = kanit,
    fontSize = 20.sp,
    color = Color.White
)

val kanitmedium = FontFamily(
    Font(R.font.kanitmedium,
        FontWeight.ExtraBold)

)

val kanitMedText = TextStyle(
    fontFamily = kanitmedium,
    fontSize = 22.sp,
    color = Color.White
)

val outfitBold = FontFamily(
    Font(R.font.outfitbold,
        FontWeight.ExtraBold)

)

val outfitBoldText = TextStyle(
    fontFamily = outfitBold,
    fontSize = 36.sp,
    color = Color.Black
)

val outfitBold2 = FontFamily(
    Font(R.font.outfitbold)

)

val outfitMedium = FontFamily(
    Font(R.font.outfitmedium)

)

val outfitBoldTextSmall = TextStyle(
    fontFamily = outfitBold2,
)

val outfitBoldMixed = FontFamily(
    Font(R.font.outfitbold)

)

val outfitBoldTextMixed = TextStyle(
    fontFamily = outfitBoldMixed,
)

val outfitRegular = FontFamily(
    Font(R.font.outfitregular)

)

val outfitRegularText = TextStyle(
    fontFamily = outfitRegular,
)

val CompactTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = outfitBold,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
    ),

    headlineMedium = TextStyle(
        fontFamily = outfitBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
    ),

    headlineSmall = TextStyle(
        fontFamily = outfitBold,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = outfitBold,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    ),

    titleMedium = TextStyle(
        fontFamily = outfitRegular,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
    ),

    titleSmall = TextStyle(
        fontFamily = outfitRegular,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = outfitBold,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = outfitMedium,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = outfitRegular,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = outfitRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),

    labelMedium = TextStyle(
        fontFamily = outfitRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = outfitRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
    ),


)