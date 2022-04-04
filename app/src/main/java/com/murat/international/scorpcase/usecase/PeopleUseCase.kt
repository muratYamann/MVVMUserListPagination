package com.murat.international.scorpcase.usecase

import com.murat.international.scorpcase.data.localdata.FetchCompletionHandler
import com.murat.international.scorpcase.repository.PeopleRepository
import javax.inject.Inject

class PeopleUseCase @Inject constructor(
    private var repository: PeopleRepository
) {

    fun fetchData(next: String?, fetchCompletionHandler: FetchCompletionHandler) {
        repository.fetchData(next, fetchCompletionHandler)
    }

}