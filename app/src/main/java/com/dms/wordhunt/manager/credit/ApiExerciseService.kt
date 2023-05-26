package com.dms.wordhunt.manager.credit

import com.dms.wordhunt.classes.Credit
import com.dms.wordhunt.classes.Payment
import com.dms.wordhunt.classes.ServerResponse
import io.reactivex.Completable
import io.reactivex.Observable

interface ApiCreditService {

    fun loadCredits(): Observable<List<Credit>>

    fun loadAllCreditPayments(): Observable<List<Payment>>

    fun loadAllCreditGraphic(): Observable<List<Payment>>

    fun loadCreditGraphic(creditUid: String): Observable<List<Payment>>

    fun loadCreditPayments(creditUid: String): Observable<List<Payment>>

    // TODO JUST FOR TEST
    fun loadCreditsAndFlats(): Observable<List<ServerResponse>>

}