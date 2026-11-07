package com.im.kiai.network

import com.im.kiai.network.message.request.RegisterRequest
import com.im.kiai.network.message.response.*
import io.reactivex.Observable
import retrofit2.http.*


interface ApiInteractor {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email: String, @Field("password") password: String): Observable<LoginResponse>


    @POST("register")
    fun register(@Body data: RegisterRequest): Observable<LoginResponse>

    @GET("kajian")
    fun kajian(): Observable<KajianResponse>

    @GET("kajian/all")
    fun semuaKajian(): Observable<KajianResponse>


    @FormUrlEncoded
    @POST("kajian/nearest")
    fun kajianTerdekat(@Field("latitude") latitude: String, @Field("longitude") longitude: String): Observable<KajianResponse>

    @GET("kajian/asatidz/{id}")
    fun kajianAsatidz(@Path("id") id: Int): Observable<KajianResponse>

    @GET("kajian/location/{id}")
    fun kajianLocation(@Path("id") id: Int): Observable<KajianResponse>

    @GET("asatidz/{id}")
    fun asatidz(@Path("id") id: Int): Observable<AsatidzResponse>

    @FormUrlEncoded
    @POST("asatidz/follow")
    fun follow(@Field("jamaah_id") id: Int, @Field("asatidz_id") asatidzId: Int): Observable<GeneralResponse>

    @FormUrlEncoded
    @POST("asatidz/unfollow")
    fun unfollow(@Field("jamaah_id") id: Int, @Field("asatidz_id") asatidzId: Int): Observable<GeneralResponse>


    @GET("asatidz/following/{id}")
    fun following(@Path("id") id: Int): Observable<AsatidzResponse>

    @FormUrlEncoded
    @POST("location")
    fun kajianLocation(@Field("latitude") latitude: String, @Field("longitude") longitude: String): Observable<LocationResponse>


    @GET("location/all")
    fun allKajianLocation(): Observable<LocationResponse>

    @FormUrlEncoded
    @POST("changepassword")
    fun ubahPassword(@Field("password") oldPassword: String, @Field("passwordnew") newPassword: String, @Field("email") email: String): Observable<GeneralResponse>


    @POST("ubahprofil")
    fun ubahProfil(@Body data: RegisterRequest): Observable<LoginResponse>

}