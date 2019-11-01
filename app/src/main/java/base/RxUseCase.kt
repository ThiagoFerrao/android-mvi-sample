package base

abstract class RxUseCase<Parameters, Mutation>: RxUseCasing<Parameters, Mutation>

fun <Mutation> RxUseCase<Unit, Mutation>.execute() = execute(parameters = Unit)