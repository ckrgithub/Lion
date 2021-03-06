package com.ruzhan.movie.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.ActivityCompat.finishAfterTransition
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.ruzhan.lion.glide.ImageLoader
import com.ruzhan.lion.listener.OnItemClickListener
import com.ruzhan.lion.model.Movie
import com.ruzhan.lion.model.Video
import com.ruzhan.lion.ui.widget.ElasticDragDismissFrameLayout
import com.ruzhan.lion.util.ViewUtils
import com.ruzhan.movie.ImageListModel
import com.ruzhan.movie.R
import com.ruzhan.movie.video.WebVideoActivity
import kotlinx.android.synthetic.main.frag_movie_detail.*

/**
 * Created by ruzhan123 on 2018/7/5.
 */
class MovieDetailFragment : Fragment() {

    companion object {

        private const val MOVIE: String = "MOVIE"
        private const val TRANSITION_TIME: Long = 450

        @JvmStatic
        fun newInstance(movie: Movie): MovieDetailFragment {
            val args = Bundle()
            args.putParcelable(MOVIE, movie)
            val frag = MovieDetailFragment()
            frag.arguments = args
            return frag
        }
    }

    private lateinit var movie: Movie
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var movieDetailAdapter: MovieDetailAdapter
    private lateinit var chromeFader: ElasticDragDismissFrameLayout.SystemChromeFader

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        movie = arguments!!.getParcelable(MOVIE)
        ImageLoader.get().loadNoCrossFade(shot, movie.image,
                ViewUtils.getPlaceholder(activity, 0))

        movieDetailAdapter = MovieDetailAdapter(object : OnItemClickListener<Video> {

            override fun onItemClick(position: Int, bean: Video, itemView: View) {
                WebVideoActivity.launch(activity!!, bean.playWebUrl)
            }
        },
                object : OnItemClickListener<ImageListModel> {
                    override fun onItemClick(position: Int, bean: ImageListModel, itemView: View) {
                        ImageDetailActivity.launch(activity!!, bean)
                    }
                })
        recycler_view.adapter = movieDetailAdapter

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val topViewHolder = movieDetailAdapter.getMovieDetailHeaderHolder()
                topViewHolder?.let {
                    val scrollY = topViewHolder.itemView.top
                    shot.offset = scrollY
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // as we animate the main image's elevation change when it 'pins' at it's min height
                // a fling can cause the title to go over the image before the animation has a chance to
                // run. In this case we short circuit the animation and just jump to state.
                shot.isImmediatePin = newState == RecyclerView.SCROLL_STATE_SETTLING
            }
        })

        recycler_view.onFlingListener = object : RecyclerView.OnFlingListener() {
            override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                shot.isImmediatePin = true
                return false
            }
        }

        back.setOnClickListener {
            activity?.let { act ->
                closeFragmentUpdateUi()
                finishAfterTransition(act)
            }
        }
        chromeFader = object : ElasticDragDismissFrameLayout.SystemChromeFader(activity) {

            override fun onDragDismissed() {
                activity?.let {
                    closeFragmentUpdateUi()
                    finishAfterTransition(it)
                }
            }
        }

        postponeEnterTransition()

        shot.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                shot.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return true
            }
        })

        movieDetailViewModel.movieDetailLiveData.observe(this@MovieDetailFragment,
                Observer { movieDetail ->
                    movieDetail?.let { movieDetailAdapter.setData(it) }
                })

        shot.postDelayed({
            movieDetailViewModel.loadMovieDetailEntity(movie.id)
        }, TRANSITION_TIME)

        movieDetailViewModel.getMovieDetail(movie.id)
    }

    override fun onResume() {
        super.onResume()
        elastic_drag_dismiss.addListener(chromeFader)
    }

    override fun onPause() {
        super.onPause()
        elastic_drag_dismiss.removeListener(chromeFader)
    }

    fun closeFragmentUpdateUi() {
        recycler_view?.let { recycler_view.visibility = View.INVISIBLE }
    }
}