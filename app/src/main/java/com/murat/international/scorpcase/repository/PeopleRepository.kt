package com.murat.international.scorpcase.repository

import com.murat.international.scorpcase.data.localdata.DataSource
import com.murat.international.scorpcase.data.localdata.FetchCompletionHandler
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val localData: DataSource
) {

    fun fetchData(next: String?, fetchCompletionHandler: FetchCompletionHandler) {
        localData.fetch(next, fetchCompletionHandler)
    }

}