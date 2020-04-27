package com.example.dummyminorproject.home

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager


import com.example.dummyminorproject.R
import com.example.dummyminorproject.utils.ARG_ID

import com.example.dummyminorproject.viewModel.HomeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.fragment_home2.*


/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment(), View.OnClickListener, OnItemClick {

    private lateinit var navController: NavController
    private lateinit var database: FirebaseFirestore
    private lateinit var Storage: FirebaseStorage
    private lateinit var mStorageReference: StorageReference
    lateinit var homeViewModel: HomeViewModel
    private lateinit var catlist: LiveData<ArrayList<CatModel>>
    var CategoryAdapter: CategoryAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmloaddataent
        return inflater.inflate(R.layout.fragment_home2, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<FloatingActionButton>(R.id.addCategory).setOnClickListener(this)
        initUi()


        activity?.menu?.setOnItemSelectedListener {
            when (it) {
                R.id.profile -> navController.navigate(R.id.action_home2_to_userProfile)
                R.id.timeline -> navController.navigate(R.id.action_home2_to_timeline)
            }
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.addCategory -> navController.navigate(R.id.action_home2_to_adddetail)
        }
    }


    private fun initUi() {

        database = FirebaseFirestore.getInstance()
        Storage = FirebaseStorage.getInstance()
        CategoryAdapter = context?.let { CategoryAdapter(it, this) }
        mStorageReference = Storage.reference.child("Category")
        recycler.layoutManager = GridLayoutManager(this.context, 2)
        recycler.setHasFixedSize(true)

        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)


        catlist = homeViewModel.loaddata()

        recycler.adapter = CategoryAdapter


        homeViewModel.mRecyclerData.observe(viewLifecycleOwner, Observer<List<CatModel>> { list ->
            list?.let {
                CategoryAdapter?.setData(it)
            }

        })


    }

    override fun OnClick(catModel: CatModel) {
        val bundle = Bundle()
        bundle.putString(ARG_ID, catModel.id)
        Log.e("click", "Id" + catModel.id)

        navController.navigate(R.id.action_home2_to_subCatImage, bundle)
    }

//    fun loaddata(): ArrayList<CatModel>{
//
//        val item : ArrayList<CatModel> = ArrayList()
//
//        database.collection("Category")
//            .get()
//            .addOnCompleteListener{task ->
//                if(task.isSuccessful){
//                    for (document in task.result!!){
//                        var id : String = document.id
//                        var imageTitle = document.data.get("ImageTitle").toString()
//                        var imageUrl = document.data.get("ImageUrl").toString()
//
//                        val add = item.add(CatModel(imageTitle,imageUrl,id))
//
//                    }
//                    val CategoryAdapter = context?.let { CategoryAdapter(it,catlist!!,this) }
//                    recycler.adapter= CategoryAdapter
//                }
//            }
//
//        return  item
//
//    }

//    override fun OnClick(catModel: CatModel) {
//        Log.e("click","Click on cardView" + catModel.imageTitle)
//
//        var SubCatImage :Fragment = SubCatImage()
//
//        val bundle = Bundle()
//        bundle.putString("id","123")
//        Log.e("click","Id" )
//       // SubCatImage.arguments = bundle
//
//        navController.navigate(R.id.action_home2_to_subCatImage,bundle)
//        //activity!!.supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.CatHome,SubCatImage()).commit()
//    }
}
