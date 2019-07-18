package bry1337.github.io.creditnotebook.presentation.home

import android.view.View
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
  val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

  private lateinit var subscription: Disposable

  override fun onCleared() {
    super.onCleared()
    subscription.dispose()
  }

  init {
    loadPersonList()
  }

  private fun loadPersonList() {
    subscription =
        Observable.fromCallable { personDao.all }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePersonListStart() }
            .doOnTerminate { onRetrievePersonListFinish() }
            .subscribe({ result ->
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

  private fun onRetrievePersonListStart() {
    loadingVisibility.value = View.VISIBLE
    errorMessage.value = null
  }

  private fun onRetrievePersonListFinish() {
    loadingVisibility.value = View.GONE
  }


}