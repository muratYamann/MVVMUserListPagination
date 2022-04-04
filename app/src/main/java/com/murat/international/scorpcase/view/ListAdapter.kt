package com.murat.international.scorpcase.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murat.international.scorpcase.data.localdata.Person
import com.murat.international.scorpcase.databinding.ItemCardBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListAdapterViewHolder>() {

    private val items = ArrayList<Person>()

    fun setItems(items: ArrayList<Person>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterViewHolder {
        val binding: ItemCardBinding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListAdapterViewHolder, position: Int) =
        holder.bind(items[position])

    class ListAdapterViewHolder(
        private val itemBinding: ItemCardBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var person: Person

        fun bind(item: Person) {
            this.person = item
            itemBinding.name.text = item.fullName
            itemBinding.userId.text = "${item.id}"
        }
    }
}
