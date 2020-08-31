package com.example.takenotes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener {
    LayoutInflater inflater;
    List<Note> notes;
    NoteDatabase db;
    Note note;

    Adapter(Context context, List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, final int position) {
        String title = notes.get(position).getTitle();
        String date = notes.get(position).getDate();
        String time = notes.get(position).getTime();
        holder.nTitle.setText(title);
        holder.nDate.setText(date);
        holder.nTime.setText(time);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(view.getContext(),Details.class);
                in.putExtra("ID",notes.get(position).getID());
                view.getContext().startActivity(in);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(),"",Toast.LENGTH_SHORT).show();
                showMenu(view);
                return false;
            }
        });

    }

    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.long_pressed_menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.edit:
            Log.d("Edit","Edit");
            return true;
            case R.id.delete:
                db.deleteNote(note.getID());
                return true;
            case R.id.share:
                Log.d("share","share");
                return true;
            case R.id.view:
                Log.d("view","view");
                return true;
            default:
                return false;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle, nDetails, nDate, nTime;
        View view;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nTitle = itemView.findViewById(R.id.noteTitle);
            nDetails = itemView.findViewById(R.id.noteDetails);
            nDate = itemView.findViewById(R.id.noteDate);
            nTime = itemView.findViewById(R.id.noteTime);
            view = itemView;
            linearLayout = itemView.findViewById(R.id.linearLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(view.getContext(),Details.class);

                    in.putExtra("ID", notes.get(getBindingAdapterPosition()).getID());
                   view.getContext().startActivity(in);
                    Toast.makeText(view.getContext(),"item clicked" ,Toast.LENGTH_SHORT).show();
                }
            });



        }
    }
}
