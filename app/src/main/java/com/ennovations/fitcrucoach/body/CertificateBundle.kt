package com.ennovations.fitcrucoach.body

import java.io.Serializable

data class CertificateBundle(
    val change: Int,
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val image_url: String,
) : Serializable
