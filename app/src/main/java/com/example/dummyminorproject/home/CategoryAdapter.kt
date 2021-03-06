package com.example.dummyminorproject.home


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dummyminorproject.R
import com.example.dummyminorproject.databinding.HomeItemBinding
import com.example.dummyminorproject.home.CatModel
import com.example.dummyminorproject.home.OnItemClick
import com.squareup.picasso.Picasso


class CategoryAdapter(private var context : Context, private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

     private var catlist: List<CatModel>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layoutInflater : LayoutInflater =
            LayoutInflater.from(parent.context)
        val binding : HomeItemBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.home_item ,parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.binding.category = catlist?.get(position)


        val imageTitle : CatModel? = catlist?.get(position)
        Picasso.get().load(imageTitle?.imageUrl).resize(250,250).centerCrop().into(holder.binding.thumbnail)
        holder.binding.cardView.setOnClickListener{
            catlist?.get(position)?.let { it1 -> onItemClick.OnClick(it1) }
        }
        holder.binding.executePendingBindings()

    }

    override fun getItemCount(): Int {
        return catlist?.size?: 0
    }

    fun setData(catlist: List<CatModel>) {
        this.catlist = catlist
        notifyDataSetChanged()

    }


    inner class MyViewHolder(var binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root) {



    }


}


