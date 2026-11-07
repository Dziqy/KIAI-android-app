package com.im.kiai.ui.astidz

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


class AsatidzPresenter(private  val activity: Activity, private val view: AsatidzView) {

    private val apiServices = ApiService.client.create(ApiInteractor::class.java)
    private var subscription: Disposable? = null

    var customProgress: CustomProgress = CustomProgress(activity)



    fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun asatidz(id: Int){
        try{
            subscription = apiServices.
            asatidz(id = id)
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

    fun follow(id: Int, asatidzId: Int){
        try{
            subscription = apiServices.
            follow(id = id, asatidzId = asatidzId)
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
                            asatidz(id)
                        }
                        view.showMessage(it.message)
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

    fun unfollow(id: Int, asatidzId: Int){
        try{
            subscription = apiServices.
            unfollow(id = id, asatidzId = asatidzId)
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
                            asatidz(id)
                        }
                        view.showMessage(it.message)
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