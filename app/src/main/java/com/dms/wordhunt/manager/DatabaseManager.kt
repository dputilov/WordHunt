package com.dms.wordhunt.manager

import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.widget.Toast
import com.dms.wordhunt.app.FinanceAssistantApp
import com.dms.wordhunt.classes.BROADCAST_ACTION
import com.dms.wordhunt.classes.BROADCAST_ACTION_TYPE
import com.dms.wordhunt.classes.BROADCAST_SEND_FROM
import com.dms.wordhunt.classes.BroadcastActionType
import com.dms.wordhunt.classes.Credit
import com.dms.wordhunt.classes.Flat
import com.dms.wordhunt.classes.FlatPayment
import com.dms.wordhunt.classes.Payment
import com.dms.wordhunt.classes.Task
import com.dms.wordhunt.database.DB
import com.dms.wordhunt.utils.formatDate

class DatabaseManager {

    var context: Context? = null

    private object Holder { val INSTANCE = DatabaseManager() }

    companion object {
        val instance: DatabaseManager by lazy { Holder.INSTANCE }
    }

    fun initManager(context: Context) {
        this.context = context.applicationContext
    }

    fun sendGroupBroadcastMessage(action: BroadcastActionType) {
        val intent = Intent(BROADCAST_ACTION)
        intent.putExtra(BROADCAST_SEND_FROM, "DatabaseManager")
        intent.putExtra(BROADCAST_ACTION_TYPE, action)
//        intent.putExtra(BROADCAST_ENTITY, Gson().toJson(group))
        context?.sendBroadcast(intent)
    }

    fun autoCreateTask() {
        context?.also { context ->
            val db = DB(context)
            db.open()

            // автоформирование задач
            db.autoTask()
            db.autoFlatTask()

            db.close()
        }
    }

    fun exchangeCreateCredit(credit: Credit) {
        context?.also { context ->

            val db = DB(context)
            db.open()

            if (credit.isNew()) {
                db.creditAdd(credit)
            } else {
                db.creditUpdate(credit)
            }

            db.close()
        }
    }

    fun exchangeCreateCreditPayment(payment: Payment) {
        context?.also { context ->

            val db = DB(context)
            db.open()

            val credit = db.getCreditByUid(payment.getCreditUid())

            if (credit != null) {
                val checkPayment = db.getPaymentByUid(payment.uid)
                if (checkPayment == null) {
                   payment.credit = credit
                   db.payment_Add(payment)

                    /// Auto-close task
                    val t = db.autoCloseTask(payment)
                    if (t.id > 0) {
                       // context.sendBroadcast()
                        //Toast.makeText(context, "Закрыта задача ${t.name}  от " + formatDate(t.date), Toast.LENGTH_SHORT).show()
                    }
                    /// Auto-close task
                }
            }

            db.close()
        }
    }

    fun exchangeCreateCreditGraphic(payment: Payment) {
        context?.also { context ->

            val db = DB(context)
            db.open()

            val credit = db.getCreditByUid(payment.getCreditUid())

            if (credit != null) {
                val checkPayment = db.getGraphicPaymentByUid(payment.uid)
                if (checkPayment == null) {
                    payment.credit = credit
                    db.graphic_Add(payment)
                }
            }

            db.close()
        }
    }

    fun exchangeDeleteGraphic(creditUid: String) {
        context?.also { context ->

            val db = DB(context)
            db.open()

            val credit = db.getCreditByUid(creditUid)
            if (credit != null) {
                db.graphic_DeleteAll(credit.id)
            }

            db.close()
        }
    }

    fun exchangeCreateFlat(flat: Flat) {
        context?.also { context ->

            val db = DB(context)
            db.open()

            if (flat.isNew()) {
                db.flat_Add(flat)
            } else {
                db.flat_Update(flat)
            }


            db.close()
        }
    }

    fun exchangeCreateFlatPayment(flatPayment: FlatPayment) {
        context?.also { context ->

            val db = DB(context)
            db.open()


            val flat = db.getFlatByUid(flatPayment.getFlatUid())

            if (flat != null) {
                val checkPayment = db.getFlatAccountByUid(flatPayment.uid)
                if (checkPayment == null) {
                    flatPayment.flat = flat
                    db.flatAccount_Add(flatPayment)

                    /// Auto-close task
                    val t = db.autoCloseTask(flatPayment)
                    if (t.id > 0) {
                        sendGroupBroadcastMessage(BroadcastActionType.AutoCloseTask)
                        //Toast.makeText(context, "Закрыта задача ${t.name}  от " + formatDate(t.date), Toast.LENGTH_SHORT).show()
                    }
                    /// Auto-close task
                }
            }

            db.close()
        }
    }

    fun getCreditByUid(credit: Credit): Credit? {
        var result : Credit? = null
        context?.also { context ->

            val db = DB(context)
            db.open()

            result = db.getCreditByUid(credit.uid)

            db.close()
        }

        return result
    }

    fun getCreditPaymentByUid(payment: Payment): Payment? {
        var result : Payment? = null
        context?.also { context ->

            val db = DB(context)
            db.open()

            result = db.getPaymentByUid(payment.uid)

            db.close()
        }

        return result
    }

    fun getGraphicPaymentByUid(payment: Payment): Payment? {
        var result : Payment? = null
        context?.also { context ->

            val db = DB(context)
            db.open()

            result = db.getGraphicPaymentByUid(payment.uid)

            db.close()
        }

        return result
    }

    fun getFlatByUid(flat: Flat): Flat? {
        var result : Flat? = null
        context?.also { context ->

            val db = DB(context)
            db.open()

            result = db.getFlatByUid(flat.uid)

            db.close()
        }

        return result
    }

    fun getFlatPaymentByUid(flatPayment: FlatPayment): FlatPayment? {
        var result : FlatPayment? = null
        context?.also { context ->

            val db = DB(context)
            db.open()

            result = db.getFlatAccountByUid(flatPayment.uid)

            db.close()
        }

        return result
    }

    fun deleteCredit(credit: Credit) {
        context?.also { context ->

            val db = DB(context)
            db.open()

            db.payment_DeleteAll(credit.id)

            db.graphic_DeleteAll(credit.id)

            db.credit_Delete(credit.id.toLong())

            db.close()
        }
    }

    fun deleteAllCredits() {
        context?.also { context ->

            val db = DB(context)
            db.open()

            db.credit_DeleteAll()

            db.close()
        }
    }

}