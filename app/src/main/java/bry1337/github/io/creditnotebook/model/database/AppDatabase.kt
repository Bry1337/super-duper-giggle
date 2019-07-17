package bry1337.github.io.creditnotebook.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import bry1337.github.io.creditnotebook.model.Finance
import bry1337.github.io.creditnotebook.model.Person
import bry1337.github.io.creditnotebook.model.dao.FinanceDao
import bry1337.github.io.creditnotebook.model.dao.PersonDao

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Database(entities = [(Person::class), (Finance::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    abstract fun financeDao(): FinanceDao
}