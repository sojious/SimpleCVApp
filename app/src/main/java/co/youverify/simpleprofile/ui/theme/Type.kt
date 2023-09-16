package co.youverify.simpleprofile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import co.youverify.simpleprofile.R


val sPProTextFont= FontFamily(Font(R.font.sf_pro_text_regular, weight = FontWeight.Normal),)


val sPProRoundedFont= FontFamily(Font(R.font.sf_pro_rounded_regular, weight = FontWeight.Normal),)
val sofiaFont= FontFamily(Font(R.font.sofia_pro_regular, weight = FontWeight.Normal),)
val nevradaFont= FontFamily(Font(R.font.nevrada_regular, weight = FontWeight.Normal),)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = sPProRoundedFont,
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