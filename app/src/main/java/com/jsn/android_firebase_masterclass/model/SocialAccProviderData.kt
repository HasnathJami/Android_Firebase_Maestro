package com.jsn.android_firebase_masterclass.model

data class SocialAccProviderData(
    var providerId: String,
    var uid: String,
    var displayName: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var photoURL: String? = null
)