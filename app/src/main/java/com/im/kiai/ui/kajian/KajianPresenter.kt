package com.im.kiai.ui.kajian

import android.app.Activity
import android.util.Log
import com.google.gson.JsonParser
import com.im.kiai.network.ApiInteractor
import com.im.kiai.network.ApiService
import com.im.kiai.utils.CustomProgress
import com.im.lapor.ui.headofrt.report.KajianView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class KajianPresenter(private  val activity: Activity, private val view: KajianView) {

    private val apiServices = ApiService.client.create(ApiInteractor::class.java)
    private var subscription: Disposable? = null

    var customProgress: CustomProgress = CustomProgress(activity)



    fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun kajian(){
        try{
            subscription = apiServices.
            semuaKajian()
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
                            view.showKajian(it.data)
                        }
                        else view.showMessage(it.message)
                    },
                    {
                        customProgress.hideLoading()
                        Log.e("LoginPresenter","Error ${it.message}")
                        var message = it.message.toString()

                        view.showMessage(message)
                    }
                )
        } catch (e: Exception) {
            customProgress.hideLoading()
            view.showMessage(e.message.toString())
            Log.e("LoginPresenter","Error ${e.message}")
        }
    }

    fun kajianAsatidz(id: Int){

        try{
            subscription = apiServices.
            kajianAsatidz(id)
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
                            view.showKajian(it.data)
                        }
                        else view.showMessage(it.message)
                    },
                    {
                        customProgress.hideLoading()
                        Log.e("LoginPresenter","Error ${it.message}")
                        var message = it.message.toString()

                        view.showMessage(message)
                    }
                )
        } catch (e: Exception) {
            customProgress.hideLoading()
            view.showMessage(e.message.toString())
            Log.e("LoginPresenter","Error ${e.message}")
        }
    }

    fun kajianLocation(id: Int){

        try{
            subscription = apiServices.
            kajianLocation(id)
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
                            view.showKajian(it.data)
                        }
                        else view.showMessage(it.message)
                    },
                    {
                        customProgress.hideLoading()
                        Log.e("LoginPresenter","Error ${it.message}")
                        var message = it.message.toString()

                        view.showMessage(message)
                    }
                )
        } catch (e: Exception) {
            customProgress.hideLoading()
            view.showMessage(e.message.toString())
            Log.e("LoginPresenter","Error ${e.message}")
        }
    }

    fun kajianNearest(lat: String, lng: String ){
        try{
            subscription = apiServices.
            kajianTerdekat(lat,lng)
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
                            view.showKajian(it.data)
                        }
                        else view.showMessage(it.message)
                    },
                    {
                        customProgress.hideLoading()
                        Log.e("LoginPresenter","Error ${it.message}")
                        var message = it.message.toString()

                        view.showMessage(message)
                    }
                )
        } catch (e: Exception) {
            customProgress.hideLoading()
            view.showMessage(e.message.toString())
            Log.e("LoginPresenter","Error ${e.message}")
        }
    }



}