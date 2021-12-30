package com.minesweeper.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minesweeper.app.R
import com.minesweeper.app.game.Grid
import com.minesweeper.app.game.Tile

class GridAdapter(
    private var grid: Grid,
    private val onTileClicked: (Tile) -> Unit
) : RecyclerView.Adapter<GridAdapter.TileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return TileViewHolder(itemView)
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

    inner class TileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tile: Tile) {
            itemView.apply {
                if (tile.isRevealed) {
                    setBackgroundResource(tile.getTileDrawable())
                }
                else {
                    setBackgroundResource(R.drawable.ic_tile_hidden)
                    setOnClickListener {
                        onTileClicked(tile)
                    }
                }
            }
        }
    }
}
