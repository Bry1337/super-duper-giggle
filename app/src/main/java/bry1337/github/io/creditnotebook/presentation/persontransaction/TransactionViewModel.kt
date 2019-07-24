package bry1337.github.io.creditnotebook.presentation.persontransaction

import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Finance

/**
 * Created by edwardbryan.abergas on 07/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class TransactionViewModel : BaseViewModel() {

  private val id = MutableLiveData<Int>()

  private val name = MutableLiveData<String>()

  private val credit = MutableLiveData<String>()

  private val debit = MutableLiveData<String>()

  private val date = MutableLiveData<String>()

  fun bind(finance: Finance) {
    id.value = finance.id
    date.value = finance.date
    credit.value = finance.credit.toString()
    debit.value = finance.debit.toString()
  }

  fun getDate(): MutableLiveData<String> {
    return date
  }

  fun getCredit(): MutableLiveData<String> {
    return credit
  }

  fun getDebit(): MutableLiveData<String> {
    return debit
  }
}