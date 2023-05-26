package com.dms.wordhunt.manager.credit

import com.dms.wordhunt.internet.api.Api
import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Credit service remote implementation
 */

class ApiExerciseServiceRemote constructor(private val api: Api) : ApiExerciseService {

    override fun loadExercise(): Observable<ResponseBody> {
       return api.getExercise()
    }
}