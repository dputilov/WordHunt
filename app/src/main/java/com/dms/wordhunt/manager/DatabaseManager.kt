package com.dms.wordhunt.manager

import android.content.Context
import android.content.Intent
import com.dms.wordhunt.classes.BROADCAST_ACTION
import com.dms.wordhunt.classes.BROADCAST_ACTION_TYPE
import com.dms.wordhunt.classes.BROADCAST_SEND_FROM
import com.dms.wordhunt.classes.BroadcastActionType

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

}