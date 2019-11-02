package network

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface MainSchedulerFactory {
    val scheduler: Scheduler

    class Impl: MainSchedulerFactory {
        override val scheduler: Scheduler = AndroidSchedulers.mainThread()
    }
}

interface IOSchedulerFactory {
    val scheduler: Scheduler

    class Impl: IOSchedulerFactory {
        override val scheduler: Scheduler = Schedulers.io()
    }
}