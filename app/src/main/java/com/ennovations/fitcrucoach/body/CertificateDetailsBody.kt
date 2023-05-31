package com.ennovations.fitcrucoach.body

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class CertificateDetailsBody(
    val certificate_name: RequestBody,
    val description: RequestBody,
    val image: MultipartBody.Part?
)
