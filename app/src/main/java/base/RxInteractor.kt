package base

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject

abstract class RxInteractor<Command, Mutation, State>(override val initialState: State) :
    RxInteracting<Command, Mutation, State> {

    override fun process(input: Observable<Command>): Observable<State> {
        val lastState: ReplaySubject<State> = ReplaySubject.createWithSize(1)

        return input
            .observeOn(Schedulers.io())
            .withLatestFrom(lastState,
                BiFunction<Command, State, Pair<Command, State>>
                { command, state -> Pair(command, state) }
            )
            .flatMap { (command, state) -> mutation(command, state) }
            .scan(initialState) { state, mutation -> reduce(mutation, state) }
            .doOnNext { lastState.onNext(it) }
    }
}