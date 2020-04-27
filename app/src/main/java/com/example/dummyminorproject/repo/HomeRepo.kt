package com.example.dummyminorproject.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dummyminorproject.home.CatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeRepo {

    var db = FirebaseFirestore.getInstance()
    var mutabledata : MutableLiveData<ArrayList<CatModel>> = MutableLiveData()


    fun loaddata() : MutableLiveData<ArrayList<CatModel>>{


        var arrayList : ArrayList<CatModel> = ArrayList()


        db.collection("Category")

            .addSnapshotListener(EventListener<QuerySnapshot>
            {value , e ->
                if (value != null) {
                    for (document: QueryDocumentSnapshot in value) {
                        var id : String = document.id

                        var imageTitle = document.data.get("ImageTitle").toString()
                        var imageUrl = document.data.get("ImageUrl").toString()
                        arrayList.add(CatModel(imageTitle, imageUrl,id))
                    }
                }
                mutabledata.value = arrayList
            })
        return mutabledata

    }


}