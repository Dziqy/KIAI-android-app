package com.im.kiai.ui.location

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


class KajianLocationPresenter(private  val activity: Activity, private val view: KajianLocationView) {

    private val apiServices = ApiService.client.create(ApiInteractor::class.java)
    private var subscription: Disposable? = null

    var customProgress: CustomProgress = CustomProgress(activity)



    fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun kajianLocation(latitude: String, longitude: String){
        try{
            Log.e("LocationPresenter","cek ${latitude} ${longitude}")
            subscription = apiServices.
            kajianLocation(latitude, longitude)
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
                            view.showLocation(it.data)
                        }
                        else view.showMessage(it.message)
                    },
                    {
                        customProgress.hideLoading()
                        Log.e("LocationPresenter","Error ${it.message}")
                        var message = it.message.toString()

                        view.showMessage(message)
                    }
                )
        } catch (e: Exception) {
            customProgress.hideLoading()
            view.showMessage(e.message.toString())
            Log.e("LocationPresenter","Error ${e.message}")
        }
    }

    fun allKajianLocation(){
        try{
            subscription = apiServices.allKajianLocation()
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
                            view.showLocation(it.data)
                        }
                        else view.showMessage(it.message)
                    },
                    {
                        customProgress.hideLoading()
                        Log.e("LocationPresenter","Error ${it.message}")
                        var message = it.message.toString()

                        view.showMessage(message)
                    }
                )
        } catch (e: Exception) {
            customProgress.hideLoading()
            view.showMessage(e.message.toString())
            Log.e("LocationPresenter","Error ${e.message}")
        }
    }



}