package com.dms.wordhunt.manager.credit

import com.dms.wordhunt.classes.Credit
import com.dms.wordhunt.classes.Payment
import com.dms.wordhunt.classes.ServerResponse
import com.dms.wordhunt.internet.api.Api
import io.reactivex.Observable

/**
 * Credit service remote implementation
 */

class ApiCreditServiceRemote constructor(private val api: Api) : ApiCreditService {

    override fun loadCredits(): Observable<List<Credit>> {
        return api.loadCredits()
    }

    override fun loadAllCreditPayments(): Observable<List<Payment>> {
        return api.loadAllCreditPayments()
    }

    override fun loadCreditPayments(creditUid: String): Observable<List<Payment>> {
        return api.loadCreditPayments(creditUid)
    }

    override fun loadAllCreditGraphic(): Observable<List<Payment>> {
        return api.loadAllCreditGraphic()
    }

    override fun loadCreditGraphic(creditUid: String): Observable<List<Payment>> {
        return api.loadCreditGraphic(creditUid)
    }

    // TODO FOR TEST
    override fun loadCreditsAndFlats(): Observable<List<ServerResponse>> {
        return api.loadCreditsAndFlats()
    }
}