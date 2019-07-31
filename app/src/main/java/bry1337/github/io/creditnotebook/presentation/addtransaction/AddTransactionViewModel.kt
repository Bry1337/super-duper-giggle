package bry1337.github.io.creditnotebook.presentation.addtransaction

import android.view.View
import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Finance
import bry1337.github.io.creditnotebook.model.Person
import bry1337.github.io.creditnotebook.model.dao.FinanceDao
import bry1337.github.io.creditnotebook.model.dao.PersonDao
import bry1337.github.io.creditnotebook.network.CreditApi
import bry1337.github.io.creditnotebook.util.Constants
import bry1337.github.io.creditnotebook.util.extension.getCurrentDateTime
import bry1337.github.io.creditnotebook.util.extension.toString
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by edwardbryan.abergas on 07/18/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class AddTransactionViewModel(private val personDao: PersonDao, private val financeDao: FinanceDao) : BaseViewModel() {

  @Inject
  lateinit var creditApi: CreditApi

  val errorMessage: MutableLiveData<Int> = MutableLiveData()
  val successMessage: MutableLiveData<Int> = MutableLiveData()
  val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
  val personObject: MutableLiveData<Person> = MutableLiveData()

  private lateinit var subscription: Disposable

  override fun onCleared() {
    super.onCleared()
    if(::subscription.isInitialized) {
      subscription.dispose()
    }
  }

  fun fieldsNotEmptyForCredit(personName: String, phoneNumber: String, creditAmount: String): Boolean {
    if (personName.isBlank()) {
      errorMessage.value = R.string.person_name_cannot_be_blank
      return false
    }
    if (phoneNumber.isBlank()) {
      errorMessage.value = R.string.phone_number_cannot_be_blank
      return false
    }
    if (creditAmount.isBlank()) {
      errorMessage.value = R.string.phone_number_cannot_be_blank
      return false
    }
    return true
  }

  fun fieldsNotEmptyForDebit(personName: String, debitAmount: String): Boolean {
    if (personName.isBlank()) {
      errorMessage.value = R.string.person_name_cannot_be_blank
      return false
    }
    if (debitAmount.isBlank()) {
      errorMessage.value = R.string.debit_value_cannot_be_blank
      return false
    }
    return true
  }

  fun loadPersonInformation(personId: Int) {
    subscription = Observable.fromCallable { personDao.getPerson(personId) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { onLoadPersonInformationStart() }
        .doOnTerminate { onLoadPersonInformationFinish() }
        .subscribe({ result ->
          personObject.value = result
        }, {
          onLoadPersonInformationError()
        })
  }

  fun createPersonObject(personName: String, phoneNumber: String, creditAmount: Double, debitAmount: Double) {
    val person = Person(personName)
    if (personObject.value != null) {
      if (creditAmount > 0) {
        addPersonTransactionCredit(personObject.value!!.id, creditAmount)
      } else {
        addPersonTransactionDebit(personObject.value!!.id, debitAmount)
      }
    } else {
      if (creditAmount > 0) {
        person.phoneNumber = phoneNumber
        person.credit = creditAmount
        insertNewEntryForCredit(person)
      } else {
        person.debit = debitAmount
        insertNewEntryForDebit(person)
      }
    }
  }

  private fun addPersonTransactionDebit(personId: Int, debitAmount: Double) {
    subscription = Observable.fromCallable {
      val date = getCurrentDateTime()
      val dateInString = date.toString(Constants.DATE_FORMAT)
      val finance = Finance(personId)
      finance.debit = debitAmount
      finance.date = dateInString
      financeDao.insert(finance)
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { onInsertProcessStart() }
        .doOnTerminate { onInsertProcessFinish() }
        .subscribe({ _ ->
          onSuccessfulInsert()
        }, {
          onInsertError()
        })
  }

  private fun addPersonTransactionCredit(personId: Int, creditAmount: Double) {
    subscription = Observable.fromCallable {
      val date = getCurrentDateTime()
      val dateInString = date.toString(Constants.DATE_FORMAT)
      val finance = Finance(personId)
      finance.credit = creditAmount
      finance.date = dateInString
      financeDao.insert(finance)
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { onInsertProcessStart() }
        .doOnTerminate { onInsertProcessFinish() }
        .subscribe({ _ ->
          onSuccessfulInsert()
        }, {
          onInsertError()
        })
  }

  private fun insertNewEntryForCredit(person: Person) {
    subscription = Observable.fromCallable { personDao.insert(person) }
        .concatMap { personId ->
          val date = getCurrentDateTime()
          val dateInString = date.toString(Constants.DATE_FORMAT)
          val finance = Finance(personId.toInt())
          finance.credit = person.credit
          finance.date = dateInString
          Observable.fromCallable { financeDao.insert(finance) }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { onInsertProcessStart() }
        .doOnTerminate { onInsertProcessFinish() }
        .subscribe({ _ ->
          onSuccessfulInsert()
        }, {
          onInsertError()
        })
  }

  private fun insertNewEntryForDebit(person: Person) {
    subscription = Observable.fromCallable { personDao.insert(person) }
        .concatMap { personId ->
          val date = getCurrentDateTime()
          val dateInString = date.toString(Constants.DATE_FORMAT)
          val finance = Finance(personId.toInt())
          finance.credit = person.credit
          finance.date = dateInString
          Observable.fromCallable { financeDao.insert(finance) }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { onInsertProcessStart() }
        .doOnTerminate { onInsertProcessFinish() }
        .subscribe({ _ ->
          onSuccessfulInsert()
        }, {
          onInsertError()
        })
  }

  private fun onSuccessfulInsert() {
    successMessage.value = R.string.transaction_has_been_added
  }

  private fun onInsertError() {
    errorMessage.value = R.string.error_adding_transaction
  }

  private fun onInsertProcessStart() {
    loadingVisibility.value = View.VISIBLE
    errorMessage.value = null
  }

  private fun onInsertProcessFinish() {
    loadingVisibility.value = View.GONE
  }

  private fun onLoadPersonInformationStart() {
    loadingVisibility.value = View.VISIBLE
    personObject.value = null
  }

  private fun onLoadPersonInformationFinish() {
    loadingVisibility.value = View.VISIBLE
  }

  private fun onLoadPersonInformationError() {
    errorMessage.value = R.string.error_loading_person_information
  }
}