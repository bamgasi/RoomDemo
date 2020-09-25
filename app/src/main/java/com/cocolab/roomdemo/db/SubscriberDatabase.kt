package com.cocolab.roomdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase: RoomDatabase() {
    abstract val subscriberDAO: SubscriberDAO

    companion object {
        @Volatile
        private var INSTANCE : SubscriberDatabase? = null

        fun getInstance(context: Context): SubscriberDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        SubscriberDatabase::class.java, "subscriber.db")
                        // DB 버전이 업그레이드 되면 이전 데이터는 모두 삭제된다.
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }

    }
}