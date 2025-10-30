package com.youapps.onlybeans.contracts

 interface UseCaseContract<I,O> {

    suspend  fun execute(input: I) : O
}

 interface UseCaseContractWriteOnly<I> {

    suspend  fun execute(input: I)
}

interface UseCaseContractReadOnly<O> {

    suspend  fun execute() : O
}

