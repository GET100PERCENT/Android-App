/*
 * Copyright (c) 2021 Windscribe Limited.
 */
package com.windscribe.tv.rate

import android.content.pm.PackageManager
import com.google.android.gms.common.GooglePlayServicesUtil
import com.windscribe.vpn.ActivityInteractor
import com.windscribe.vpn.Windscribe.Companion.appContext
import com.windscribe.vpn.constants.RateDialogConstants

class RateMyAppPresenterImp(
    private val rateView: RateView,
    private val interactor: ActivityInteractor
) : RateMyAppPresenter {
    override fun onAskMeLaterClick() {
        interactor.setRateDialogUpdateTime()
        interactor.saveRateAppPreference(RateDialogConstants.STATUS_ASK_LATER)
        rateView.onGoWindScribeActivity()
    }

    override fun onNeverAskClick() {
        interactor.setRateDialogUpdateTime()
        interactor.saveRateAppPreference(RateDialogConstants.STATUS_NEVER_ASK)
        rateView.onGoWindScribeActivity()
    }

    override fun onRateNowClick() {
        interactor.setRateDialogUpdateTime()
        interactor.saveRateAppPreference(RateDialogConstants.STATUS_ALREADY_ASKED)
        if (isPlayStoreInstalled) {
            rateView.openPlayStoreWithLink()
        } else {
            rateView.showToast("No Play store installed.")
            rateView.onGoWindScribeActivity()
        }
    }

    private val isPlayStoreInstalled: Boolean
        get() = try {
            appContext.packageManager
                .getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
}
