package bry1337.github.io.creditnotebook.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import bry1337.github.io.creditnotebook.model.database.AppDatabase
import bry1337.github.io.creditnotebook.presentation.home.HomeViewModel
import bry1337.github.io.creditnotebook.util.Constants

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
      val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java,
          Constants.PERSON_DATABSE).build()
      return HomeViewModel(db.personDao()) as T
    }
    throw IllegalArgumentException("Unknown ViewModel Class")
  }
}