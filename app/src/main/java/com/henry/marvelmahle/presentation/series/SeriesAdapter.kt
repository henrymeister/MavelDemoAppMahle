package com.henry.marvelmahle.presentation.series

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.henry.marvelmahle.R
import com.henry.marvelmahle.data.model.series.SeriesId
import com.henry.marvelmahle.data.model.series.SeriesResult
import kotlinx.android.synthetic.main.series_item_layout.view.*

class SeriesAdapter(
    private var seriesList: ArrayList<SeriesResult>,
    private var onItemClickListener: (SeriesId) -> Unit
) : RecyclerView.Adapter<SeriesAdapter.DataViewHolder>() {

    //region RECYCLER METHODS ----------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.series_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = seriesList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(seriesList[position], onItemClickListener)
    // endregion

    //region HOLDERS -------------------------------------------------------------------------------

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(serie: SeriesResult, onItemClickListener: (SeriesId) -> Unit) {
            itemView.serie_name.text = serie.title
            itemView.serie_description.text = serie.description

            Glide.with(itemView.serie_image)
                .load(serie.thumbnail.path + "." + serie.thumbnail.extension)
                .error(R.drawable.ic_launcher_background)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.serie_progressbar.isVisible = false
                        Log.e("CHARACTER", "IMAGE KO")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.serie_progressbar.isVisible = false
                        Log.e("CHARACTER", "IMAGE Ok")
                        return false
                    }
                })
                .circleCrop()
                .into(itemView.serie_image)


            itemView.setOnClickListener {
                onItemClickListener.invoke(serie.id)
            }
        }
    }
    // endregion


    // region PUBLIC METHODS -----------------------------------------------------------------------

    fun setData(list: List<SeriesResult>, onItemClickListener: (SeriesId) -> Unit) {
        seriesList = ArrayList()
        seriesList.addAll(list)
        this.onItemClickListener = onItemClickListener
    }
    // endregion
}