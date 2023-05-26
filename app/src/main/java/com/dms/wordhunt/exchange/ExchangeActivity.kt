package com.dms.wordhunt.exchange

import android.view.*
import com.dms.wordhunt.base.ViewBindingActivity
import com.dms.wordhunt.databinding.ExchangeActivityBinding

class ExchangeActivity : ViewBindingActivity<ExchangeActivityBinding>() {

    override val bindingInflater: (LayoutInflater) -> ExchangeActivityBinding
        = ExchangeActivityBinding::inflate

    override fun setup() {
        //ToolbarUtils.initToolbar(this, true, R.string.toolbar_exchange, R.color.ExchangeToolbar, R.color.ExchangeWindowsBar)
        //ToolbarUtils.initToolbar(this, true, R.string.toolbar_exchange, R.color.grayLight, R.color.ExchangeWindowsBar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
