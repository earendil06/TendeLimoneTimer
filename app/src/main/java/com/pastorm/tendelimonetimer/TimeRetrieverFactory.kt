package com.pastorm.tendelimonetimer

class TimeRetrieverFactory {
    fun createRetriever(strategy: RetrieveMode) =
        when (strategy) {
            RetrieveMode.LIVE_CAMERA -> LiveCameraRetriever()
            RetrieveMode.STATIC_WEBSITE -> StaticWebsiteRetriever()
        }
}

enum class RetrieveMode {
    LIVE_CAMERA, STATIC_WEBSITE
}