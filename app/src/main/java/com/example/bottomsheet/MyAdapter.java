package com.example.bottomsheet;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



List<CardView> listItemsList;
private Context context;

    public MyAdapter(List<CardView> listItemsList, Context context) {
        this.listItemsList = listItemsList;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyAdapter.ViewHolder viewHolder, int i) {


        CardView listItem=listItemsList.get(i);
        viewHolder.textRs.setText(listItem.getSymbol());
        viewHolder.textCost.setText(listItem.getCost());
        viewHolder.textBookname.setText(listItem.getBookName());
        viewHolder.textSno.setText(listItem.getsNo());

    }

    @Override
    public int getItemCount() {
        return listItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textSno,textBookname,textCost,textRs;

        public ViewHolder( View itemView) {
            super(itemView);

            textSno=(TextView)  itemView.findViewById(R.id.sno);
            textBookname=(TextView) itemView.findViewById(R.id.bookname);
            textCost=(TextView) itemView.findViewById(R.id.cost);
            textRs=(TextView) itemView.findViewById(R.id.rs);

        }
    }

}
