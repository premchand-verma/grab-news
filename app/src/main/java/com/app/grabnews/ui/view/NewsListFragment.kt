package weather.android.com.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.grabnews.R
import com.app.grabnews.model.NewsArticleData
import com.app.grabnews.model.NewsArticleModel
import com.app.grabnews.model.NewsListResponse
import com.app.grabnews.ui.view.DetailActivity
import com.app.grabnews.ui.viewmodel.NewsListViewModel
import com.app.grabnews.util.Utils.isNetworkAvailable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.fragment_news_listing.*
import weather.android.com.util.Constants

class NewsListFragment : Fragment(), NewsListAdapter.OnClickListener {
    private lateinit var realm: Realm
    private val TAG = NewsListFragment::class.java.simpleName

    private lateinit var newsAdapter: NewsListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var newsList: ArrayList<NewsArticleModel> = arrayListOf()
    private lateinit var newsListViewModel: NewsListViewModel

    private var totalItemData = 0
    private var isLoading: Boolean = false
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var pageNumber: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsListViewModel = ViewModelProviders.of(this).get(NewsListViewModel::class.java)
        loadingObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addRecylerView()

        realm = Realm.getDefaultInstance()

        if (isNetworkAvailable(context)) {
            progress_bar.visibility = View.VISIBLE
            newsListViewModel.getNewsList(pageNumber)
        }else{
            progress_bar.visibility = View.GONE
            showNewsFromDatabase()
        }
    }

    private fun loadingObserver() {
        newsListViewModel.successResponseData.removeObservers(this)
        newsListViewModel.errorResponseData.removeObservers(this)
        newsListViewModel.successResponseData.observe(
            this,
            Observer { model -> showSuccessView(model) })
        newsListViewModel.errorResponseData.observe(this, Observer { str -> showErrorView() })
    }

    private fun showSuccessView(model: NewsListResponse) {
        progress_bar.visibility = View.GONE
        totalItemData = model.totalResults
        newsList.addAll(model.articles)
        newsAdapter.notifyAdapter(newsList)
        addToDatabase(newsList)
    }

    private fun addRecylerView() {
        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = linearLayoutManager
        newsAdapter = NewsListAdapter(context!!, arrayListOf(), this)
        recycler_view.adapter = newsAdapter

        isLoading = false
        loadMoreData()
    }

    fun showErrorView() {
        progress_bar.visibility = View.GONE
    }

    private fun loadMoreData() {
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.childCount
                    totalItemCount = linearLayoutManager.itemCount
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                    if (!isLoading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            if (pageNumber * Constants.PAGE_SIZE < totalItemData) {
                                isLoading = true
                                pageNumber++
                                newsListViewModel.getNewsList(pageNumber)
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onItemClick(position: Int) {
        if (!isNetworkAvailable(activity)) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Constants.NEWS_URL, newsList.get(position).url)
            startActivity(intent)
        }
    }

    private fun addToDatabase(data: List<NewsArticleModel>) {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()

        for (newsData in data) {
            addRecords(newsData)
        }
        if (!isNetworkAvailable(activity)) showNewsFromDatabase()
    }

    private fun addRecords(data: NewsArticleModel) {
        try {
            realm.executeTransaction({ realm: Realm ->
                try {

                    var id: Number? = realm.where(NewsArticleData::class.java).max("id")
                    var nextId: Int
                    if (id == null) {
                        nextId = 1
                    } else {
                        nextId = id.toInt()+1
                    }
                    val news = realm.createObject<NewsArticleData>(nextId)

//                    news.id = nextId
                    news.author = data.author
                    news.content = data.content
                    news.description = data.description
                    news.publishedAt = data.publishedAt
                    news.title = data.title
                    news.url = data.url
                    news.urlToImage = data.urlToImage

                    realm.copyToRealmOrUpdate(news)
                } catch (e: RealmPrimaryKeyConstraintException) {
                    Toast.makeText(
                        context,
                        "Primary Key exists, Press Update instead",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } catch (e:Exception){
            Log.e(TAG, e.toString())
        }
    }

    private fun showNewsFromDatabase() {
        try {
            val newsData: RealmResults<NewsArticleData> =
                realm!!.where(NewsArticleData::class.java).findAll()

            for (data in newsData) {
                var newsModel = NewsArticleModel()
                newsModel.author = data.author
                newsModel.content = data.content
                newsModel.description = data.description
                newsModel.publishedAt = data.publishedAt
                newsModel.title = data.title
                newsModel.url = data.url
                newsModel.urlToImage = data.urlToImage
                newsList.add(newsModel)
            }

            newsAdapter.notifyAdapter(newsList)
        } finally {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}
