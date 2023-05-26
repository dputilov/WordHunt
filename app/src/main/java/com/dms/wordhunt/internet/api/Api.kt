package com.dms.wordhunt.internet.api

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * API class representing all the public EYE API methods
 */

interface Api {

    @GET("edu/ex/2")
    fun getExercise(): Observable<ResponseBody>

    @POST("api/Flats/{FlatUid}/Photo")
    fun updateFlatPicture(
        @Header("Filename") filename: String,
        @Header("CreateDate") createAt: String,
        @Header("FileSize") fileSize: Long,
        @Path("FlatUid") creditUid: String,
        @Body image: RequestBody): Completable

//    @DELETE("api/Posts/{postId}")
//    fun deletePost(@Path("postId") postId: String): Completable

//    @PUT("api/Posts/{id}")
//    fun updatePost(@Path("id") id: String,
//                   @Body body: UpdatePostBody): Observable<Post>

}
