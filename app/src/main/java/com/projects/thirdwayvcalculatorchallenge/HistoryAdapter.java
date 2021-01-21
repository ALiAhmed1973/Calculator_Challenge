package com.projects.thirdwayvcalculatorchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static final String TAG = HistoryAdapter.class.getSimpleName();

    private Context context;

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<String> list;

    private OnItemClickListener onItemClickListener;

    public HistoryAdapter(Context context) {

        this.context = context;

        //this.onItemClickListener = onItemClickListener;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView operationTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            operationTextView = itemView.findViewById(R.id.operation_textView);
        }

        public void bind(final String model, final OnItemClickListener listener) {
            operationTextView.setText(model);
            itemView.setOnClickListener(v -> listener.onItemClick(getLayoutPosition()));

        }

    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.history_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {

        String item = list.get(position);

        holder.bind(item, onItemClickListener);

    }

    @Override

    public int getItemCount() {
        if(list==null)
        {
            return 0;
        }
        return list.size();

    }

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

}