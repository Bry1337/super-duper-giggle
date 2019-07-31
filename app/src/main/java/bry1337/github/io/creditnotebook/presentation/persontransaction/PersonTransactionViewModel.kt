package bry1337.github.io.creditnotebook.presentation.persontransaction

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Finance
import bry1337.github.io.creditnotebook.model.dao.FinanceDao
import bry1337.github.io.creditnotebook.model.dao.PersonDao
import bry1337.github.io.creditnotebook.util.OnRemoveItemListener
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
    private val financeDao: FinanceDao) : BaseViewModel(), OnRemoveItemListener {

  val transactionListAdapter: TransactionListAdapter = TransactionListAdapter()
  val personName: MutableLiveData<String> = MutableLiveData()
  val errorMessage: MutableLiveData<Int> = MutableLiveData()
  val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
  val totalCredits: MutableLiveData<Int> = MutableLiveData()
  val position: MutableLiveData<Int> = MutableLiveData()

  private val personId: MutableLiveData<Int> = MutableLiveData()

  private lateinit var removeItemDialog: AlertDialog
  private lateinit var subscription: Disposable
  private lateinit var financeList: ArrayList<Finance>

  private var finance: Finance? = null
  private var personNameTemp: String? = null

  override fun onCleared() {
    super.onCleared()
    if(::subscription.isInitialized) {
      subscription.dispose()
    }
    this.personId.value = null
  }

  override fun onRemoveItem(position: Int) {
    this.position.value = position
    finance = financeList[position]
    financeList.removeAt(position)
    transactionListAdapter.updateFinanceList(financeList)
    removeItemDialog.show()
  }

  fun createRemoveItemDialog(context: Context) {
    removeItemDialog = AlertDialog.Builder(context)
        .setTitle(R.string.delete)
        .setMessage(R.string.are_you_sure)
        .setPositiveButton(R.string.ok) { _, _ ->
          deleteFromDb()
          dismissAndSetDefaultValue()
        }
        .setNegativeButton(R.string.undo) { _, _ ->
          undoRemoveItem()
          dismissAndSetDefaultValue()
        }
        .create()
  }

  fun loadTransactionHistory(personId: Int) {
    this.personId.value = personId
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

  private fun deleteFromDb(){
    subscription = Observable.fromCallable { financeDao.delete(finance!!) }
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          getTotalCredits(this.personId.value!!)
        }, {
          onDeleteError()
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
    this.financeList = ArrayList(financeList)
    transactionListAdapter.updateFinanceList(financeList)
  }

  private fun onRetrieveError(cause: String) {
    errorMessage.value = R.string.something_went_wrong
  }

  private fun undoRemoveItem() {
    financeList.add(this.position.value!!, finance!!)
    transactionListAdapter.updateFinanceList(financeList)
  }

  private fun dismissAndSetDefaultValue() {
    removeItemDialog.dismiss()
    this.position.value = null
    finance = null
  }

  private fun onDeleteError() {
    errorMessage.value = R.string.error_deleting
  }

}