package com.example.dummyminorproject.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyminorproject.home.CatModel
import com.example.dummyminorproject.repo.HomeRepo

class HomeViewModel : ViewModel() {

    var mRecyclerData : MutableLiveData<ArrayList<CatModel>> = MutableLiveData()
    var homeRepo = HomeRepo()


//    companion object {
//        private const val TAG: String = "CategoryViewModel"
//    }

    init {
        loaddata()
    }

    fun loaddata():LiveData<ArrayList<CatModel>>{
        mRecyclerData = homeRepo.loaddata()
        return mRecyclerData
    }
}