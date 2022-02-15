package com.dassol.kanolio.domain

import android.net.Uri
import android.util.Log

class Parsing {
    fun concatName(
        data: MutableMap<String, Any>?,
        deep: String,
        gadid: String,
        af_id: String,
        application_id: String,
        link: String,
    ): String {

        val deepContains = !deep.contains("sub")
        val doubleDeepChecker = deep != "null" && deep.contains("sub")
        val doubleCampaignChecker =
            data?.get("campaign") != "" && data?.get("campaign") != "null" && data?.get("campaign")
                .toString().contains("sub") && data?.get("campaign") != null
        val doubleCChecker = data?.get("c").toString().contains("sub") && data?.get("c") != "null"

        var deepWithoutMyApp = deep.replace("myapp://", "")
        deepWithoutMyApp = deepWithoutMyApp.replace("||", "doubleline").replace("_", "underline")
        deepWithoutMyApp = encodeCharacters(deepWithoutMyApp)


        var campaignReplacedSymbols =
            data?.get("campaign").toString().replace("||", "doubling").replace("_", "underline")
        campaignReplacedSymbols = encodeCharacters(campaignReplacedSymbols)

        var cFieldreplacedsymbols =
            data?.get("c").toString().replace("||", "doubleline").replace("_", "underline")
        cFieldreplacedsymbols = encodeCharacters(cFieldreplacedsymbols)


        Log.d("library", " launchConcat")
        return link + "?" +
                "&gadid=$gadid" +
                "&af_id=$af_id" +
                if (doubleDeepChecker) {
                    replaceSymbols(deepWithoutMyApp)
                } else {
                    ""
                } +

                if (doubleCampaignChecker && deepContains) {
                    replaceSymbols(campaignReplacedSymbols)
                } else {
                    ""
                } +

                if (doubleCChecker) {
                    replaceSymbols(cFieldreplacedsymbols)
                } else {
                    ""
                } +
                if (doubleDeepChecker) {

                    "&app_campaign=${encodeCharacters(deep)}"
                } else {
                    ""
                } +
                if (doubleCChecker) {
                    "&app_campaign=${encodeCharacters(data?.get("c").toString())}"
                } else {
                    ""
                } +
                if (doubleCampaignChecker && deepContains) {
                    "&app_campaign=${encodeCharacters(data?.get("campaign").toString())}"
                } else {
                    ""
                } +
                "&orig_cost=${data?.get("orig_cost").toString()}" +
                "&adset_id=${data?.get("adset_id").toString()}" +
                "&campaign_id=${data?.get("campaign_id").toString()}" +
                "&source=${data?.get("media_source").toString()}" +
                "&bundle=$application_id" +
                "&af_siteid=${data?.get("af_siteid").toString()}" +
                "&currency=${data?.get("currency").toString()}" +
                "&adset=${encodeCharacters(data?.get("adset").toString())}" +
                "&adgroup=${encodeCharacters(data?.get("adgroup").toString())}"

    }

    private fun encodeCharacters(parameter: String): String {
        return Uri.encode(parameter)
    }

    private fun replaceSymbols(campaign: String): String {
        return campaign.replace("doubleline", "&").replace("underline", "=")
    }

}