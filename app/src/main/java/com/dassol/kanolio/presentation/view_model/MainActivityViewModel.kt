package com.dassol.kanolio.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dassol.kanolio.data.database.dao.DataBaseDao
import com.dassol.kanolio.data.database.dao.FullLinkDataBaseDao
import com.dassol.kanolio.data.database.entity.Data
import com.dassol.kanolio.data.database.entity.FullLink
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val dataBaseDao: DataBaseDao,
    private val fullLinkDataBaseDao: FullLinkDataBaseDao
) : ViewModel() {

    fun startData() {
        viewModelScope.launch {
            if (dataBaseDao.checkDataBase() == null) {
                saveAppsDevKeyAndLinkInDb()
                _appsDevKeyAndLink.postValue(dataBaseDao.getDataFromDataBase())
                _isLoading.postValue(true)
            } else {
                _appsDevKeyAndLink.postValue(dataBaseDao.getDataFromDataBase())
                _isLoading.postValue(true)
            }
        }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _appsDevKeyAndLink = MutableLiveData<Data>()
    val appsDevKeyAndLink: LiveData<Data> = _appsDevKeyAndLink

    private val _fullLink = MutableLiveData<String>()
    val fullLink: LiveData<String> = _fullLink

    private suspend fun saveAppsDevKeyAndLinkInDb() {
        val data = Data("3Vz9Bwt74gpEY7xac94ZRA", "https://trident.website/Jfk9YBsc")
        dataBaseDao.saveAppsDevKeyAndLinkInDb(data)
    }

    //Full Link
    fun saveFullLinkInDataBase(fullLink: String) {
        val fullLink = FullLink(fullLink)
        viewModelScope.launch {
            fullLinkDataBaseDao.saveFullLinkInDb(fullLink)
        }
    }

    fun fetchFullLinkFromDataBase() {
        GlobalScope.launch {
            if (fullLinkDataBaseDao.checkDataBase() != null)
                _fullLink.postValue(fullLinkDataBaseDao.getFullLinkFromDataBase().fullLink)
            else
                _fullLink.postValue("null")
        }
    }

}