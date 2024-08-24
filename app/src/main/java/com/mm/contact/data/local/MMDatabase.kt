package com.mm.contact.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mm.contact.data.local.entities.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class MMDatabase : RoomDatabase() {
    abstract fun getContactDao():ContactDao

    companion object {
        private var instance: MMDatabase? = null

        fun getDatabase(context: Context): MMDatabase {
            if (instance == null) {
                synchronized(MMDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
                            MMDatabase::class.java,
                            "mmContact.db"
                        )
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}