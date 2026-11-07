package com.im.kiai.ui.following

import android.app.Activity
import android.util.Log
import com.google.gson.JsonParser
import com.im.kiai.network.ApiInteractor
import com.im.kiai.network.ApiService
import com.im.kiai.utils.CustomProgress
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class FollowingPresenter(private  val activity: Activity, private val view: FollowingView) {

    private val apiServices = ApiService.client.create(ApiInteractor::class.java)
    private var subscription: Disposable? = null

    var customProgress: CustomProgress = CustomProgress(activity)



    fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun asatidz(id: Int){
        try{
            subscription = apiServices.
            following(id = id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    customProgress.showLoading()
                }
                .subscribe(
                    {
                        customProgress.hideLoading()
                        if(it.success)
                        {
                            view.showAsatidz(it.data)
                        }
                        else view.showMessage(it.message)
                    },
                    {
                        customProgress.hideLoading()
                        Log.e("AsatidzPresenter","Error ${it.message}")
                        var message = it.message.toString()

                        view.showMessage(message)
                    }
                )
        } catch (e: Exception) {
            customProgress.hideLoading()
            view.showMessage(e.message.toString())
            Log.e("AsatidzPresenter","Error ${e.message}")
        }
    }

}