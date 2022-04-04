package com.murat.international.scorpcase.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murat.international.scorpcase.data.localdata.ErrorTypes
import com.murat.international.scorpcase.data.localdata.FetchCompletionHandler
import com.murat.international.scorpcase.data.localdata.Person
import com.murat.international.scorpcase.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val localDataUseCase: PeopleUseCase) : ViewModel() {

    var personList: MutableLiveData<List<Person>?> = MutableLiveData()
    var loadingProgressState: MutableLiveData<Boolean> = MutableLiveData()
    var errors: MutableLiveData<ErrorTypes> = MutableLiveData()
    var paginationValue: String? = null

    private var completionHandler: FetchCompletionHandler = { fetchResponse, fetchError ->
        if (fetchResponse != null) {
            paginationValue = fetchResponse.next

            /**
             *  Empty Scope
             */
            if (fetchResponse.people.isEmpty()) {
                errors.postValue(ErrorTypes.NO_DATA)
            }
            /**
             *  First data
             */
            else if (personList.value.isNullOrEmpty()) {
                personList.postValue(fetchResponse.people
                    .groupBy { it.id }.entries
                    .map { it.value.last() }
                    .sortedBy { it.id }
                )
            }
            /**
             * Add new people to list
             */
            else {
                val newList = personList.value?.plus(fetchResponse.people)
                    ?.groupBy { it.id }?.entries
                    ?.map { it.value.last() }
                    ?.sortedBy { it.id }

                /**
                 * no new people to show
                 */
                if (newList?.size ?: 0 != personList.value!!.size) {
                    personList.postValue(newList!!)
                } else {
                    errors.postValue(ErrorTypes.NO_NEW_ITEM)
                }
            }
        }
        if (fetchError != null) {
            when (fetchError.errorTypes) {
                ErrorTypes.INTERNAL_SERVER_ERROR -> errors.postValue(ErrorTypes.INTERNAL_SERVER_ERROR)
                ErrorTypes.PARAMETER_ERROR -> errors.postValue(ErrorTypes.PARAMETER_ERROR)
                else -> errors.postValue(ErrorTypes.NO_DATA)
            }
        }
        loadingProgressState.value = false
    }

    init {
        fetchData()
    }

    private fun fetchData() {
        loadingProgressState.value = true
        localDataUseCase.fetchData(paginationValue, completionHandler)
    }

    fun loadNextPage() {
        fetchData()
    }

    fun refreshPage() {
        paginationValue = null
        personList.value = null
        fetchData()
    }

}

