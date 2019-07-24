package bry1337.github.io.creditnotebook.presentation.persontransaction

import android.view.View
import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Finance
import bry1337.github.io.creditnotebook.model.dao.FinanceDao
import bry1337.github.io.creditnotebook.model.dao.PersonDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by edwardbryan.abergas on 07/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class PersonTransactionViewModel(private val personDao: PersonDao,
    private val financeDao: FinanceDao) : BaseViewModel() {

  val transactionListAdapter: TransactionListAdapter = TransactionListAdapter()
  val personName: MutableLiveData<String> = MutableLiveData()
  val errorMessage: MutableLiveData<Int> = MutableLiveData()
  val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
  val totalCredits: MutableLiveData<Int> = MutableLiveData()
  private var personNameTemp: String? = null
  private lateinit var subscription: Disposable

  override fun onCleared() {
    super.onCleared()
    subscription.dispose()
  }

  fun loadTransactionHistory(personId: Int) {
    subscription = Observable.fromCallable { personDao.getPerson(personId) }.concatMap { person ->
      Observable.fromCallable {
        personNameTemp = person.name
        financeDao.getTransactionhistoryOfPerson(person.id)
      }
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { onRetrieveTransactionHistoryStart() }
        .doOnTerminate { onRetrieveTransactionFinish() }
        .subscribe({ result ->
          onRetrieveTransactionHistorySuccess(result)
          getTotalCredits(personId)
        }, { cause ->
          onRetrieveError(cause.localizedMessage)
        })
  }

  private fun getTotalCredits(personId: Int) {
    subscription = Observable.fromCallable { financeDao.getTotalCreditsOfPerson(personId) }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loadingVisibility.value = View.VISIBLE }
        .doOnTerminate { loadingVisibility.value = View.GONE }
        .subscribe({ result ->
          totalCredits.value = result
        }, { cause ->
          onRetrieveError(cause.localizedMessage)
        })
  }

  private fun onRetrieveTransactionHistoryStart() {
    loadingVisibility.value = View.VISIBLE
    errorMessage.value = null
    personName.value = null
    personNameTemp = null
  }

  private fun onRetrieveTransactionFinish() {
    loadingVisibility.value = View.GONE
    personName.value = personNameTemp
  }

  private fun onRetrieveTransactionHistorySuccess(financeList: List<Finance>) {
    transactionListAdapter.updateFinanceList(financeList)
  }

  private fun onRetrieveError(cause: String) {
    errorMessage.value = R.string.something_went_wrong
  }
}