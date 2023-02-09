package com.phone.keeptask

import android.app.Application
import com.phone.keeptask.di.taskModule
import com.phone.keeptask.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EntryPoint : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@EntryPoint)
            modules(taskModule, roomModule)
        }
    }
}