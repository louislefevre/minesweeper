package com.minesweeper.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.minesweeper.app.R
import com.minesweeper.app.game.Grid
import com.minesweeper.app.game.Tile

class GridAdapter(
    private var grid: Grid,
    private val onTileClicked: (Tile) -> Unit,
    private val onTileLongClicked: (Tile) -> Unit,
    var itemsClickable: Boolean = true
) : RecyclerView.Adapter<GridAdapter.TileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return TileViewHolder(itemView as ImageView)
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        holder.bind(grid.tileAt(position))
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return grid.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateGrid(grid: Grid) {
        this.grid = grid
        notifyDataSetChanged()
    }

    inner class TileViewHolder(private val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {
        fun bind(tile: Tile) {
            imageView.apply {
                if (tile.isRevealed) {
                    setImageResource(tile.getTileDrawable())
                } else {
                    setImageResource(
                        if (tile.isFlagged) R.drawable.ic_tile_flag
                        else R.drawable.ic_tile_hidden
                    )

                    if (itemsClickable) {
                        setOnClickListener { onTileClicked(tile) }
                        setOnLongClickListener { onTileLongClicked(tile); true }
                    }
                }
            }
        }
    }
}
