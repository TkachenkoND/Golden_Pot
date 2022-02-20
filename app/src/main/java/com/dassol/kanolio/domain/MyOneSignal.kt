package com.dassol.kanolio.domain

import com.onesignal.OneSignal

class MyOneSignal {
    fun workWithOneSignal(data: MutableMap<String, Any>?, deepLink: String?) {

        if (data?.get("campaign").toString() == "null" && deepLink == "null") {
            OneSignal.sendTag("key2", "organic")
        } else if (deepLink != "null" && deepLink != null) {

            OneSignal.sendTag("key2", deepLink.replace("myapp://", "").substringBefore("/"))

        } else if (data?.get("campaign").toString() != "null") {

            OneSignal.sendTag("key2", data?.get("campaign").toString().substringBefore("_"))
        }
    }

}