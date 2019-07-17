package bry1337.github.io.creditnotebook.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import bry1337.github.io.creditnotebook.model.Finance

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Dao
interface FinanceDao {

    @get:Query("Select * from finance")
    val all: List<Finance>

    @Query("Select * from finance where personId == :personId")
    fun getFinanceByPerson(personId: Int): Finance

    @Query("Select * from finance WHERE id == :id")
    fun getFinance(id: Int): Finance

    @Insert
    fun insert(finance: Finance)

    @Query("Delete from finance")
    fun deleteAll()

    @Delete
    fun delete(finance: Finance)
}