package com.dms.wordhunt.manager.credit

import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody

interface ApiExerciseService {

    fun loadExercise(): Observable<ResponseBody>

}