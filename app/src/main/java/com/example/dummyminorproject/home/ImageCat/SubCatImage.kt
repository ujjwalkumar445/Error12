package com.example.dummyminorproject.home.ImageCat

import android.os.Bundle
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
import com.example.dummyminorproject.home.CatModel
import com.example.dummyminorproject.home.CategoryAdapter
import com.example.dummyminorproject.utils.ARG_ID
import com.example.dummyminorproject.viewModel.SubCatViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_sub_cat_image.*

/**
 * A simple [Fragment] subclass.
 */
class SubCatImage : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var database: FirebaseFirestore
    private lateinit var Storage: FirebaseStorage
    private lateinit var mStorageReference: StorageReference
    private var catImagelist: ArrayList<CatImageModel>? = null
    lateinit var subCatViewModel: SubCatViewModel
    private lateinit var catSublist: LiveData<ArrayList<CatImageModel>>
    var imageAdapter: ImageAdapter? = null

    var subCatId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subCatId = arguments?.getString(ARG_ID,"")?:""

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_cat_image, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<FloatingActionButton>(R.id.addCategoryImage).setOnClickListener(this)
        basic()

        activity?.menu?.setOnItemSelectedListener {
            when (it) {
                R.id.profile -> navController.navigate(R.id.action_subCatImage_to_userProfile)
                R.id.timeline -> navController.navigate(R.id.action_subCatImage_to_timeline)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.addCategoryImage -> navController.navigate(
                R.id.action_subCatImage_to_addImage,
                arguments
            )
        }
    }


    private fun basic() {

        database = FirebaseFirestore.getInstance()
//        Storage  = FirebaseStorage.getInstance()
//        mStorageReference = Storage.reference.child("CategoryImage")
       imageAdapter = context?.let { ImageAdapter(it) }


        recyclerImage.layoutManager = GridLayoutManager(this.context, 3)
        recyclerImage.setHasFixedSize(true)

        subCatViewModel = ViewModelProvider(requireActivity()).get(SubCatViewModel::class.java)


        catSublist = subCatViewModel.Getdata(subCatId)

        recyclerImage.adapter = imageAdapter


        subCatViewModel.mSubRecyclerData.observe(viewLifecycleOwner, Observer<List<CatImageModel>> { list ->
            list?.let {
                imageAdapter?.setData(it)
            }

        })


    }

//        catImagelist = ArrayList()
//        catImagelist = GetImage()

    }

//    fun GetImage(): ArrayList<CatImageModel> {
//
//        val list: ArrayList<CatImageModel> = ArrayList()
//
////        val args: String? = arguments?.getString(ARG_ID)
//
//        database.collection("CategoryImage").document(subCatId).collection("Image")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    for (document in task.result!!) {
//                        var id: String = document.id
//                        var imageUrl = document.data.get("ImageUrl").toString()
//
//                        list.add(CatImageModel(imageUrl, id, subCatId))
//
//                    }
//                    val ImageAdapter = context?.let { ImageAdapter(catImagelist!!) }
//                    recyclerImage.adapter = ImageAdapter
//                }
//            }
//
//        return list
//
//    }


