package com.jsn.android_firebase_masterclass.utils

object  Utility {
    fun isNullOrEmpty(str: String?): Boolean {
        if (!str.isNullOrEmpty()) return false
        return true
    }
}