package com.dassol.kanolio.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.dassol.kanolio.R
import com.dassol.kanolio.databinding.MainActivityBinding
import com.dassol.kanolio.domain.CheckNetwork
import com.dassol.kanolio.domain.MyOneSignal
import com.dassol.kanolio.domain.Parsing
import com.dassol.kanolio.presentation.view.game_view.FragmentMenu
import com.dassol.kanolio.presentation.view.game_view.FragmentProgressBar
import com.dassol.kanolio.presentation.view.game_view.game.FragmentGame
import com.dassol.kanolio.presentation.view_model.MainActivityViewModel
import com.facebook.applinks.AppLinkData
import com.facebook.internal.Utility
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainActivityViewModel>()

    lateinit var binding: MainActivityBinding

    val parsing = Parsing()
    var fullLink: String? = null

    private val checkInet = CheckNetwork(this)
    private val myOneSignal = MyOneSignal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.startData()

        if (checkInet.checkForInternet()) {
            initObserver()
        } else
            startGameView()
    }

    private fun workWithApps() {
        viewModel.isLoading.observe(this) { vm ->
            if (vm) {
                val conversionDataListener = object : AppsFlyerConversionListener {
                    override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                        Log.d("AppLog", data.toString())
                        AppLinkData.fetchDeferredAppLinkData(this@MainActivity) {
                            Log.d("AppLog", it?.targetUri.toString())
                            fullLink = parsing.concatName(
                                data = data,
                                deep = it?.targetUri.toString(),
                                gadid = getAppId(),
                                af_id = AppsFlyerLib.getInstance()
                                    .getAppsFlyerUID(this@MainActivity),
                                application_id = this@MainActivity.packageName,
                                link = viewModel.appsDevKeyAndLink.value!!.link
                            )
                            Log.d("AppLog", fullLink!!)

                            viewModel.saveFullLinkInDataBase(fullLink!!)

                            startWebView(fullLink!!)
                            myOneSignal.workWithOneSignal(data, it?.targetUri.toString())
                        }
                    }

                    override fun onConversionDataFail(error: String?) {
                        Log.e(Utility.LOG_TAG, "error onAttributionFailure :  $error")
                    }

                    override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                        data?.map {
                            Log.d(Utility.LOG_TAG, "onAppOpen_attribute: ${it.key} = ${it.value}")
                        }
                    }

                    override fun onAttributionFailure(error: String?) {
                        Log.e(Utility.LOG_TAG, "error onAttributionFailure :  $error")
                    }
                }
                AppsFlyerLib.getInstance().init(
                    viewModel.appsDevKeyAndLink.value!!.appsDevKey,
                    conversionDataListener,
                    applicationContext
                )
                AppsFlyerLib.getInstance().start(this)
            } else
                Toast.makeText(this, "ключ і лінка пусті !!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun getAppId(): String {
        var advertisingId = ""

        GlobalScope.launch {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(this@MainActivity)
            advertisingId = adInfo.id.toString()
            OneSignal.setExternalUserId(advertisingId)
        }
        return advertisingId
    }

    private fun initObserver() {
        viewModel.fetchFullLinkFromDataBase()

        lifecycleScope.launch {
            viewModel.fullLink.observe(this@MainActivity) {
                if (it == "null") {
                    workWithApps()
                } else {
                    startWebView(it)
                }
            }
        }
    }

    private fun startGameView() {
        OneSignal.sendTag("key1", "bot")
        binding.pgBar.visibility = ProgressBar.GONE

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, FragmentProgressBar())
        }
    }

    private fun startWebView(url: String) {
        if (url.isNotEmpty()) {
            startActivity(Intent(this, ViewWebka::class.java).putExtra("url", url))
            finish()
        }
    }
}