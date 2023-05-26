package com.dms.wordhunt.exchange

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dms.wordhunt.base.BaseAndroidViewModel
import com.dms.wordhunt.classes.BroadcastActionType
import com.dms.wordhunt.classes.ExchangeFilterItem
import com.dms.wordhunt.classes.ExchangeItem
import com.dms.wordhunt.useCase.services.LoadExerciseUseCase
import com.dms.wordhunt.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Exchange
 */

class ExchangeViewModel(application: Application): BaseAndroidViewModel(application) {

    @Inject
    lateinit var LOadExerciseUseCase: dagger.Lazy<LoadExerciseUseCase>

    private var exchangeApplySubscription: Disposable? = null
    private var exchangeCreditSubscription: Disposable? = null
    private var exchangeCreditPaymentsSubscription: Disposable? = null
    private var exchangeCreditPGraphicsSubscription: Disposable? = null
    private var exchangeFlatSubscription: Disposable? = null
    private var exchangeFlatPaymentsSubscription: Disposable? = null
    private var exchangeAllSubscription: Disposable? = null

    var exchangeFilterItemList = MutableLiveData<List<ExchangeFilterItem>>()

    var exchangeItemList = MutableLiveData<List<ExchangeItem>>()
    var onlyUpdated = MutableLiveData<Boolean>()

    val showCloseTaskMessageEvent = SingleLiveEvent<Void>()

    // Data binding
    val resultText = MutableLiveData<String>()
    val applyLayoutVisibility = MutableLiveData(false)
    val obtainingDataInProgress = MutableLiveData(false)
    val applyProgressBarVisibility = MutableLiveData(false)
    val maxApplyProgress = MutableLiveData<Int>(0)
    val currentApplyProgress = MutableLiveData<Int>(0)
    val currentApplySecondaryProgress = MutableLiveData<Int>(0)
    val exchangeApplyProgressBarText = MutableLiveData("")
    val exchangeApplyProgressBarPercentText = MutableLiveData("")

    init {
        onlyUpdated.value = true
        registerGroupBroadcastReceiver()

    }

    override fun onCleared() {
        super.onCleared()

        unregisterGroupBroadcastReceiver()

        exchangeApplySubscription?.dispose()

        exchangeCreditSubscription?.dispose()
        exchangeCreditPaymentsSubscription?.dispose()
        exchangeCreditPGraphicsSubscription?.dispose()
        exchangeFlatSubscription?.dispose()
        exchangeFlatPaymentsSubscription?.dispose()
        exchangeAllSubscription?.dispose()

    }


    private fun getOnlyUpdated() : Boolean = onlyUpdated.value ?: false

    override fun onGroupBroadcastReceive(action: BroadcastActionType) {
        showCloseTaskMessageEvent.call()
    }

    private fun getCurrentApplyProgress() : Int {
        return currentApplyProgress.value ?: 0
    }

    fun onStartClick() {
        exchangeAll()
    }
    private fun getProgressPercent(progress: Int): String {
        val percent = maxApplyProgress.value?.let { max ->
            if (max != 0) {
                (progress * 100) / max
            } else {
                0
            }
        } ?: 0
        return "${percent}%"
    }

    private fun exchangeAll() {
        exchangeAllSubscription?.dispose()

        exchangeAllSubscription = LOadExerciseUseCase.get().loadExercise()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ onStartExchangeProcess() }
            .doOnComplete{ onStopExchangeProcess() }
            .subscribe(
                { data -> onExchangeSuccess(data) },
                { error: Throwable -> onExchangeFail(error) }
            )
    }

    private fun onStartExchangeProcess() {
        obtainingDataInProgress.value = true
    }

    private fun onStopExchangeProcess() {
        obtainingDataInProgress.value = false
    }

    private fun onExchangeSuccess(data: List<ExchangeItem>) {
        val newList = mutableListOf<ExchangeItem>()

        exchangeItemList.value?.also {
            newList.addAll(it)
        }

        data.map { it.isSelected = it.isChanged() }

        if (getOnlyUpdated()) {
            newList.addAll(data.filter { it.isChanged() })
        } else {
            newList.addAll(data)
        }

        exchangeItemList.value = newList

        checkExchangeList()
    }

    private fun checkExchangeList() {
        if (exchangeItemList.value?.isEmpty() == true) {
            resultText.value = "No new data received"
            applyLayoutVisibility.value = false
        } else {
            applyLayoutVisibility.value = true
        }
    }

    private fun onExchangeFail(error: Throwable) {
        exchangeItemList.value = listOf()
        onStopExchangeProcess()
        resultText.value = "http error = $error";
        Log.d("DMS_NETWORK", "http error = $error")
    }
}
