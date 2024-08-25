package com.mm.contact.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mm.contact.data.local.entities.ContactEntity
import com.mm.contact.util.Constant
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM ContactEntity ORDER BY name  ")
    fun getContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM ContactEntity ORDER BY name LIMIT :limit offset :offset ")
    fun getContacts(offset: Int, limit: Int = Constant.LIMIT): Flow<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertContact(contactEntity: ContactEntity)
}