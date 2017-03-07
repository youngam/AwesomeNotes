package com.hackspace.alex.awesomenotes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.worklibrary.ui.helper.BaseRecyclerAdapter;
import com.hackspace.alex.worklibrary.ui.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends BaseRecyclerAdapter<Note,NotesAdapter.NoteViewHolder> {
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