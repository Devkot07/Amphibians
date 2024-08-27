package com.example.amphibians.model

import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class Amphibians (

    val name: String,
    val type: String,
    val description: String,
    @SerialName("img_src")
    val imgSrc: String,




    )