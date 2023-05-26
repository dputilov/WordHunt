package com.dms.wordhunt.exchange

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dms.wordhunt.R
import com.dms.wordhunt.base.BaseFragment
import com.dms.wordhunt.classes.ExchangeFilterItem
import com.dms.wordhunt.classes.ExchangeItem
import com.dms.wordhunt.classes.ExchangeItemType
import com.dms.wordhunt.databinding.ExchangeFragmentBinding

class ExchangeFragment : BaseFragment<ExchangeFragmentBinding>(), ExchangeAdapterDelegate {

    private val viewModel: ExchangeViewModel by activityViewModels()

    private var exchangeAdapter : ExchangeListAdapter? = null

    private var exchangeItemInfoFragment: ExchangeItemInfoFragment? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ExchangeFragmentBinding
        = ExchangeFragmentBinding::inflate

    override fun setupDataBinding() {

        // Bind layout with ViewModel
        binding.viewModel = viewModel

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//       // setToolBar()
//    }

    override fun setup() {
        setupExchangeListAdapter()

        bindViewModel()

        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        exchangeAdapter = null
    }

    private fun bindViewModel() {
        viewModel.exchangeItemList.observe(viewLifecycleOwner, Observer { exchangeItemList ->
            exchangeItemList?.also {
                updateAdapter(it)
            }
        })

        viewModel.showCloseTaskMessageEvent.observe(viewLifecycleOwner, Observer {
            showCloseTaskMessage()
        })
    }

    private fun setupExchangeListAdapter(){
        exchangeAdapter = ExchangeListAdapter(requireActivity(), this)
        binding.exchangeRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.exchangeRecyclerView.adapter = exchangeAdapter
    }

    private fun updateAdapter(exchangeItemList: List<ExchangeItem>){
        exchangeAdapter?.listData = exchangeItemList
        exchangeAdapter?.notifyDataSetChanged()
    }

    fun setListeners() = with(binding) {
        exchangeApplyButton.setOnClickListener {
        }

        exchangeApplyCancel.setOnClickListener {
        }

        exchangeRecyclerView.setOnScrollChangeListener { view, newState, i2, i3, i4 ->
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                && exchangeRecyclerView.computeVerticalScrollOffset() == 0
            ) {
                if (!exchangeFloatingActionButton.isExtended) {
                    exchangeFloatingActionButton.extend()
                }
            } else {
                if (exchangeFloatingActionButton.isExtended) {
                    exchangeFloatingActionButton.shrink()
                }
            }

        }
    }

    private fun showCloseTaskMessage() {
        //Toast.makeText(activity, "Закрыта задача ${t.name}  от " + formatDate(t.date), Toast.LENGTH_SHORT).show()
        Toast.makeText(activity, "Закрыта задача ", Toast.LENGTH_SHORT).show()
    }

    private fun showSlideBottomFragment(item: ExchangeItem, onCloseClick: (item: ExchangeItem) -> Unit) {
        activity?.let {
            if (exchangeItemInfoFragment == null) {
                exchangeItemInfoFragment = ExchangeItemInfoFragment()
            }

            exchangeItemInfoFragment?.item = item
            exchangeItemInfoFragment?.onCloseClick = onCloseClick
            exchangeItemInfoFragment?.show(it.supportFragmentManager, ExchangeItemInfoFragment.TAG)
        }
    }


    override fun onExchangeItemClick(item: ExchangeItem) {
        showSlideBottomFragment(item) {
            showInfo(item)
        }
    }

    private fun showInfo(item: ExchangeItem) {
        Toast.makeText(context, "ЗАКРЫТ ${item.objectName}", Toast.LENGTH_SHORT).show()
    }

}
