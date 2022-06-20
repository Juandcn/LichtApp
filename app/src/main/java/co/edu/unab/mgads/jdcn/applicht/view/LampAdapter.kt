package co.edu.unab.mgads.jdcn.applicht.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.unab.mgads.jdcn.applicht.R
import co.edu.unab.mgads.jdcn.applicht.databinding.LampItemBinding
import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp

class LampAdapter (private var lamps:ArrayList<Lamp>): RecyclerView.Adapter<LampAdapter.ProductViewHolder>(){

    var onItemClickListener:((Lamp)->Unit)?=null
    var onItemLongClickListener:((Lamp)->Unit)?=null

    fun refresh(myLamp: ArrayList<Lamp>){
        lamps=myLamp
        notifyDataSetChanged()
    }

    class ProductViewHolder(private val binding: LampItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(myLamp: Lamp,
                 onItemClickListener: ((Lamp) -> Unit)?,
                 onItemLongClickListener: ((Lamp) -> Unit)?){
            binding.lamp=myLamp
            binding.root.setOnClickListener{
                onItemClickListener?.let {
                    it(myLamp)
                }
            }
            binding.root.setOnLongClickListener{
                onItemLongClickListener?.let {
                    it(myLamp)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflate= LayoutInflater.from(parent.context)
        val binding:LampItemBinding = DataBindingUtil.inflate(inflate,
            R.layout.lamp_item,parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(lamps[position], onItemClickListener, onItemLongClickListener)
    }

    override fun getItemCount(): Int= lamps.size
}