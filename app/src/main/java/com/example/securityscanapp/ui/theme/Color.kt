package com.example.securityscanapp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Red = Color(0xFF9F023B)
val Green = Color(0xFF018601)
val Blue = Color(0xFF3A3AF3)
val Yellow = Color(0xFFEFE379)

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

val Titlebar =  Color(0xFF874EEC)

val LightYellow = Color(0xFFF5E8C1)
val LightGreen = Color(0xFFCBF3CD)
val LightRed = Color(0xFFF39DBA)
val LightGray = Color(0xFF737171)

val DarkGreen = Color(0xFF005D05)
val DarkGray = Color(0xFF494949)

val LightColors = lightColorScheme(
    primary = Green,
    onPrimary = Color.White,
    background = Color.White,
    surface = LightGray
)

val DarkColors = darkColorScheme(
    primary = DarkGreen,
    onPrimary = Color.Black,
    background = DarkGray,
    surface = Black
)
