package weather.android.com.view

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.grabnews.R
import com.app.grabnews.model.NewsArticleModel
import com.generic.httpclient.widget.ILImageView
import kotlinx.android.synthetic.main.itme_news.view.*
import java.text.SimpleDateFormat
import java.util.*


class NewsListAdapter(
    val ctx: Context,
    var list: List<NewsArticleModel>,
    val listiner: OnClickListener
) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.itme_news, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(position, list.get(position), ctx, listiner)
    }

    fun notifyAdapter(newsList: ArrayList<NewsArticleModel>) {
        list = newsList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivNews = view.iv_news
        val tvNewsTitle = view.tv_news_title
        val tvNewsAuthor = view.tv_news_author
        val tvNewsDate = view.tv_news_date
        val tvNewsDescription = view.tv_news_description
        val rootView = view.rootView

        fun bindView(
            position: Int,
            data: NewsArticleModel,
            ctx: Context,
            listiner: OnClickListener
        ) {
            tvNewsDate.text = getDayFromDate(data.publishedAt)
            tvNewsAuthor.text = data.author
            tvNewsTitle.text = data.title
            tvNewsDescription.text = data.description

            loadImage(data.urlToImage, ivNews)

            rootView.setOnClickListener { v -> listiner.onItemClick(position); }
        }

        private fun getDayFromDate(date: String): String {
            val format1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
            format1.timeZone = TimeZone.getTimeZone("UTC")
            val dt1 = format1.parse(date)
            val format2 = SimpleDateFormat("dd-MMM-yyyy")
            format2.timeZone = TimeZone.getDefault()
            return format2.format(dt1)
        }

        private fun loadImage(url: String, ivView: ILImageView) {
            ivView.setScaleType(ImageView.ScaleType.CENTER_CROP)
            ivView.setDefaultImageResId(R.drawable.ic_launcher_background)
            ivView.setErrorImageResId(R.drawable.ic_launcher_background)
            ivView.setImageUrl(url)
        }
    }

    interface OnClickListener {
        fun onItemClick(
            position: Int
        )
    }
}