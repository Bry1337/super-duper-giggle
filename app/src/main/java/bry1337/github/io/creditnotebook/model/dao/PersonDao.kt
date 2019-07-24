package bry1337.github.io.creditnotebook.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import bry1337.github.io.creditnotebook.model.Person

/**
 * Created by edwardbryan.abergas on 07/17/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Dao
interface PersonDao {

    @get:Query("Select * from person")
    val all: List<Person>

    @Query("Select * from person WHERE id = :id")
    fun getPerson(id: Int): Person

    @Insert
    fun insert(person: Person): Long

    @Query("Delete from person")
    fun deleteAll()

    @Delete
    fun delete(person: Person)
}