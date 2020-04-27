package com.example.dummyminorproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyminorproject.home.ImageCat.CatImageModel
import com.example.dummyminorproject.repo.subCatRepo

class SubCatViewModel : ViewModel() {

    var mSubRecyclerData : MutableLiveData<ArrayList<CatImageModel>> = MutableLiveData()
    var subCatRepo = subCatRepo()


//    companion object {
//        private const val TAG: String = "CategoryViewModel"
//    }

//    init {
//        Getdata()
//    }

    fun Getdata(args:String): LiveData<ArrayList<CatImageModel>> {
        mSubRecyclerData = subCatRepo.Getdata(args)
        return mSubRecyclerData
    }
}
