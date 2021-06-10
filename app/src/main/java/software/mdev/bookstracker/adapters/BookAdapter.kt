package software.mdev.bookstracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import software.mdev.bookstracker.R
import software.mdev.bookstracker.data.db.entities.Book
import kotlinx.android.synthetic.main.item_book.view.*
import software.mdev.bookstracker.other.Constants.BOOK_STATUS_IN_PROGRESS
import software.mdev.bookstracker.other.Constants.BOOK_STATUS_READ
import software.mdev.bookstracker.other.Constants.BOOK_STATUS_TO_READ

class BookAdapter(
    var context: Context,
    val whichFragment: String

) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent,false))
    }

    private var onBookClickListener: ((Book) -> Unit)? = null

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val curBook = differ.currentList[position]
        holder.itemView.apply {
            tvBookTitle.text = curBook.bookTitle
            tvBookAuthor.text = curBook.bookAuthor

            when (whichFragment ){
                BOOK_STATUS_READ -> rbRatingIndicator.rating = curBook.bookRating
            }
            when (curBook.bookStatus ){
                BOOK_STATUS_READ -> {
                    ivInProgressIndicator.visibility = View.GONE
                    ivToReadIndicator.visibility = View.GONE
                    rbRatingIndicator.visibility = View.VISIBLE
                    rbRatingIndicator.rating = curBook.bookRating
                }
                BOOK_STATUS_IN_PROGRESS -> {
                    rbRatingIndicator.visibility = View.GONE
                    ivToReadIndicator.visibility = View.GONE
                    ivInProgressIndicator.visibility = View.VISIBLE
                }
                BOOK_STATUS_TO_READ -> {
                    rbRatingIndicator.visibility = View.GONE
                    ivInProgressIndicator.visibility = View.GONE
                    ivToReadIndicator.visibility = View.VISIBLE
                }
            }
        }

        holder.itemView.apply {
            setOnClickListener {
                onBookClickListener?.let { it(curBook) }
            }
        }
    }

    fun setOnBookClickListener(listener: (Book) -> Unit) {
        onBookClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}