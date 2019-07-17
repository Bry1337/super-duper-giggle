package bry1337.github.io.creditnotebook.presentation.home

import androidx.lifecycle.MutableLiveData
import bry1337.github.io.creditnotebook.R
import bry1337.github.io.creditnotebook.base.BaseViewModel
import bry1337.github.io.creditnotebook.model.Person
import bry1337.github.io.creditnotebook.model.dao.PersonDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomeViewModel(private val personDao: PersonDao) : BaseViewModel() {


  val errorMessage: MutableLiveData<Int> = MutableLiveData()

  private lateinit var subscription: Disposable

  override fun onCleared() {
    super.onCleared()
    subscription.dispose()
  }

  private fun loadPersonList() {
    subscription = Observable.just(personDao.all).subscribeOn(Schedulers.io()).observeOn(
        AndroidSchedulers.mainThread()).subscribe({ result ->
      onRetrievePersonListSuccess(result)
    }, {
      onRetrievePersonListError()
    })
  }

  private fun onRetrievePersonListError() {
    errorMessage.value = R.string.error_loading_list
  }

  private fun onRetrievePersonListSuccess(result: List<Person>?) {

  }


}