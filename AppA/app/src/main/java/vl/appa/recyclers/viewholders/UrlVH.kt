package vl.appa.recyclers.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vl.appa.data.models.ImageUrl
import vl.appa.databinding.VhUrlBinding

class UrlVH private constructor(private val binding: VhUrlBinding): RecyclerView.ViewHolder(binding.root) {

	fun bind(imageUrl: ImageUrl) {
		binding.imageUrl = imageUrl
	}

	companion object {

		fun create(parent: ViewGroup): UrlVH {
			val inflater = LayoutInflater.from(parent.context)
			return UrlVH(VhUrlBinding.inflate(inflater, parent, false))
		}
	}
}