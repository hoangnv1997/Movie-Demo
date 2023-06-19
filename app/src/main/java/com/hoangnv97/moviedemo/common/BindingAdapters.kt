package com.hoangnv97.moviedemo.common

import android.annotation.SuppressLint
import android.util.Size
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.Util.Companion.appendZeroBeforeNumber
import com.hoangnv97.moviedemo.domain.api.TheMovieConstants
import com.hoangnv97.moviedemo.domain.entity.Genre
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("bind_poster_path")
fun ImageView.bindPosterImageWithPicasso(path: String?) {
    if (path.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_baseline_image_24)
        return
    }
    Picasso.get().load(TheMovieConstants.getPosterUrl(path)).fit()
//    .transform(Rou(4, 1))
        .error(R.drawable.ic_baseline_image_24).into(this)
}

@BindingAdapter("bind_backdrop_path_loading_highlighted_movie", "bind_progress")
fun ImageView.bindBackdropImageWithPicassoLoadingMovie(movie: Movie?, progressBar: ProgressBar) {
    if (movie == null) return
    val backdropPath = movie.backdropPath
    if (backdropPath.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_baseline_image_24)
        return
    }

    progressBar.visibility = View.VISIBLE
    Picasso.get().load(TheMovieConstants.getBackdropUrl(backdropPath)).fit()
        .error(R.drawable.ic_baseline_image_24)
        .into(
            this,
            object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                }
            }
        )
}

@BindingAdapter("bind_backdrop_path_loading", "bind_progress")
fun ImageView.bindBackdropImageWithPicassoLoading(path: String?, progressBar: ProgressBar) {
    if (path.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_baseline_image_24)
        return
    }
    progressBar.visibility = View.VISIBLE
    Picasso.get().load(TheMovieConstants.getBackdropUrl(path)).fit()
        .error(R.drawable.ic_baseline_image_24)
        .into(
            this,
            object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                }
            }
        )
}

@BindingAdapter("bind_video_thumbnail")
fun ImageView.bindVideoThumbnailWithPicasso(youtubeId: String?) {
    if (youtubeId.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_baseline_image_24)
        return
    }
    Picasso.get().load(TheMovieConstants.getYoutubeImageUrl(youtubeId)).fit()
        .error(R.drawable.ic_baseline_image_24).into(this)
}

@BindingAdapter("bind_profile_path")
fun ImageView.bindProfileImageWithPicasso(path: String?) {
    if (path.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_baseline_image_24)
        return
    }
    Picasso.get().load(TheMovieConstants.getProfileUrl(path)).fit()
        .error(R.drawable.ic_baseline_image_24).into(this)
}

@BindingAdapter("bind_profile_path_no_fit")
fun ImageView.bindProfileImageWithPicassoNoFit(path: String?) {
    if (path.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_baseline_image_24)
        return
    }
    Picasso.get().load(TheMovieConstants.getProfileUrl(path))
        .error(R.drawable.ic_baseline_image_24).into(this)
}

@BindingAdapter("bind_rating_bar", "bind_rating_stars")
fun RatingBar.bindRatingBar(movie: Movie?, stars: Int) {
    movie?.let {
        this.rating = (stars * ((it.voteAverage / TheMovieConstants.MAX_RATING))).toFloat()
    }
}

@BindingAdapter("bind_rating_bar_tv_show", "bind_rating_stars")
fun RatingBar.bindRatingBar(voteAverage: Float, stars: Int) {
    this.rating = stars * ((voteAverage / TheMovieConstants.MAX_RATING))
}

@BindingAdapter("bind_genres_text")
fun TextView.bindGenresText(genres: List<Genre>?) {
    if (genres == null) return

    val maxNumOfGenres = 3
    var text = ""
    val appendText = " / "

    val loopCount = if (genres.size <= maxNumOfGenres) genres.size else maxNumOfGenres
    for (i in 0 until loopCount) {
        text = text + genres[i].name + appendText
    }

    this.text = text.dropLast(appendText.length)
}

@BindingAdapter("bind_runtime_text")
fun TextView.bindMovieRuntime(runtimeInMinutes: Int?) {
    runtimeInMinutes?.let {
        val hoursText: String = appendZeroBeforeNumber((it / 60f).toInt())
        val minutesText: String = appendZeroBeforeNumber((it % 60f).toInt())
        val text = "$hoursText:$minutesText / $runtimeInMinutes phút"
        this.text = text
    }
}

@BindingAdapter("bind_language_code_text")
fun TextView.bindMovieLanguage(languageCode: String?) {
    languageCode?.let { this.text = Locale(languageCode).getDisplayLanguage(Locale("vi")) }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("bind_date_text")
fun TextView.bindMovieRuntime(dateString: String?) {
    if (dateString.isNullOrBlank()) return
    val date = SimpleDateFormat(TheMovieConstants.getRuntimeDateFormat()).parse(dateString)
    val pat =
        SimpleDateFormat().toLocalizedPattern().replace("\\W?[HhKkmsSzZXa]+\\W?".toRegex(), "")
    val localFormatter = SimpleDateFormat(pat, Locale.getDefault())
    this.text = localFormatter.format(date)
}

@BindingAdapter("is_visible")
fun View.setIsVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("is_gone")
fun View.setIsGone(isGone: Boolean) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("is_selected")
fun View.setIsSelect(isSelected: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("is_enabled")
fun View.setIsEnabled(isEnabledValue: Boolean) {
    this.isEnabled = isEnabledValue
}

@BindingAdapter("animate_rotation")
fun View.setAnimateRotation(angle: Int) {
    animate().cancel()
    val oldValue = this.rotation.toInt()
    val rotateDiff = angle - oldValue
    val rotateAngle = when {
        rotateDiff > 180 -> rotateDiff - 360
        rotateDiff < -180 -> rotateDiff + 360
        else -> rotateDiff
    }
    animate().rotationBy(rotateAngle.toFloat()).setDuration(200).start()
}

@BindingAdapter("layout_height")
fun View.setLayoutHeight(height: Float) {
    setLayoutHeight(height.toInt())
}

fun View.setLayoutHeight(height: Int) {
    layoutParams = layoutParams?.also {
        it.height = height
    } ?: ViewGroup.LayoutParams(0, height)
}

@BindingAdapter("layout_width")
fun View.setLayoutWidth(width: Float) {
    setLayoutWidth(width.toInt())
}

fun View.setLayoutWidth(width: Int) {
    layoutParams = layoutParams?.also {
        it.width = width
    } ?: ViewGroup.LayoutParams(width, 0)
}

@BindingAdapter("layout_size")
fun View.setLayoutSize(size: Size) {
    layoutParams = layoutParams?.also {
        it.width = size.width
        it.height = size.height
    } ?: ViewGroup.LayoutParams(size.width, size.height)
}
