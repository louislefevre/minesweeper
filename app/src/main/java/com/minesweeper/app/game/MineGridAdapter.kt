package com.minesweeper.app.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minesweeper.app.R


class MineGridAdapter(
    private val tiles: List<Tile>,
    private val onTileClicked: (Tile) -> Unit
) : RecyclerView.Adapter<MineGridAdapter.TileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.board_tile, parent, false)
        return TileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        holder.bind(tiles[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return tiles.size
    }

    inner class TileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tile: Tile) {
            itemView.apply {
                setOnClickListener {
                    onTileClicked(tile)
                }
            }
        }
    }
}
