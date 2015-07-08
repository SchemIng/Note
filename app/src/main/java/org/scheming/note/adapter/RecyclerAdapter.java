package org.scheming.note.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.scheming.greendao.bean.Note;
import org.scheming.note.R;

import java.util.List;

/**
 * Created by Scheming on 2015/5/29.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Note> datas = null;
    private ItemClickListener clickListener = null;
    private ItemLongClickListener longClickListener = null;

    public RecyclerAdapter(List<Note> datas) {
        this.datas = datas;
    }

    public void setDatas(List<Note> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, clickListener, longClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(datas.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface ItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    public interface ItemLongClickListener {
        void OnItemLongClickListener(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private AppCompatTextView content = null;
        private ItemClickListener clickListener = null;
        private ItemLongClickListener longClickListener = null;

        public ViewHolder(View itemView, ItemClickListener clickListener, ItemLongClickListener longClickListener) {
            super(itemView);
            content = (AppCompatTextView) itemView.findViewById(R.id.item_content);
            this.clickListener = clickListener;
            this.longClickListener = longClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.OnItemClickListener(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (longClickListener != null)
                longClickListener.OnItemLongClickListener(v, getAdapterPosition());
            return false;
        }
    }
}
