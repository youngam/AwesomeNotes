package com.hackspace.alex.awesomenotes.adapter;

import java.util.Collections;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.ui.ItemTouchHelperListener;
import com.hackspace.alex.worklibrary.ui.helper.BaseRecyclerAdapter;
import com.hackspace.alex.worklibrary.ui.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends BaseRecyclerAdapter<Note,NotesAdapter.NoteViewHolder>
        implements ItemTouchHelperListener {
    private OnItemClickListener<Note> mOnNoteClickListener;

    public NotesAdapter(OnItemClickListener<Note> onNoteClickListener) {
        mOnNoteClickListener = onNoteClickListener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflate(R.layout.note_item_view, parent);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.setContent(mItems.get(position));
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void addItem(Note note, int position) {
        mItems.add(position, note);
        notifyItemInserted(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.note_title_text_view)
        TextView mNoteTitleTextView;

        NoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        void setContent(Note note) {
            mNoteTitleTextView.setText(note.getTitle());
        }

        @Override
        public void onClick(View v) {
            if(mOnNoteClickListener != null){
                mOnNoteClickListener.onEntityClick(mItems.get(getLayoutPosition()));
            }
        }
    }
}