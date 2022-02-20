package com.dassol.kanolio.domain

import android.content.Context
import androidx.core.net.toUri
import com.dassol.kanolio.R
import java.util.*

class Parsing (private val context: Context) {
    fun concatName(
        data: MutableMap<String, Any>?,
        deep: String,
        gadid: String,
        af_id: String,
        link: String,
    ): String {

        return link.toUri().buildUpon().apply {

            appendQueryParameter(context.getString(R.string.secure_get_parametr), "lleBYESoLl")
            appendQueryParameter(context.getString(R.string.dev_tmz_key), TimeZone.getDefault().id)
            appendQueryParameter(context.getString(R.string.gadid_key), gadid)
            appendQueryParameter(context.getString(R.string.deeplink_key), deep)
            appendQueryParameter(context.getString(R.string.source_key), data?.get("media_source").toString())
            appendQueryParameter(context.getString(R.string.af_id_key), af_id)
            appendQueryParameter(context.getString(R.string.adset_id_key), data?.get("adset_id").toString())
            appendQueryParameter(context.getString(R.string.campaign_id_key), data?.get("campaign_id").toString())
            appendQueryParameter(context.getString(R.string.app_campaign_key), data?.get("campaign").toString())
            appendQueryParameter(context.getString(R.string.adset_id_key), data?.get("adset").toString())
            appendQueryParameter(context.getString(R.string.adgroup_key), data?.get("adgroup").toString())
            appendQueryParameter(context.getString(R.string.orig_cost_key), data?.get("orig_cost").toString())
            appendQueryParameter(context.getString(R.string.af_siteid_key), data?.get("af_siteid").toString())

        }.toString()

    }
}