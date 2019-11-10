package base

import io.reactivex.Observable

interface RxUseCasing<Parameters, Mutation> {
    fun execute(parameters: Parameters): Observable<Mutation>
}

abstract class RxUseCase<Parameters, Mutation> : RxUseCasing<Parameters, Mutation>

fun <Mutation> RxUseCase<Unit, Mutation>.execute() =
    execute(Unit)