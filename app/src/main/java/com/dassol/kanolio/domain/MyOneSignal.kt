package com.dassol.kanolio.domain

import com.onesignal.OneSignal

class MyOneSignal {
    fun workWithOneSignal(data: MutableMap<String, Any>?, deepLink: String?) {

        if (data?.get("campaign") == null && deepLink == null) {
            OneSignal.sendTag("key2", "organic")
        } else if (data?.get("campaign") != null) {
            OneSignal.sendTag("key2", extractSub(data.get("campaign").toString()))
        } else if (deepLink != "null" && deepLink != null) {

            OneSignal.sendTag("key2", extractSub(deepLink))
        }
    }

    private fun extractSub(string: String) =
        string.substringAfter("sub1_").substringBefore("||sub2")
}