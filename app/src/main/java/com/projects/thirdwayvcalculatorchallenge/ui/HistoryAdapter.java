package com.projects.thirdwayvcalculatorchallenge.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.projects.thirdwayvcalculatorchallenge.R;
import com.projects.thirdwayvcalculatorchallenge.data.OperatorNumber;

import java.util.List;

/**
 * Recycler View that hold List of OperatorNumber for converting List to cells
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static final String TAG = HistoryAdapter.class.getSimpleName();

    /**
     * For set List of OperatorNumber that would changed when getHistoryListMutableLiveData change
     * and notify recyclerview that data changed
     * @param list list of OperatorNumber
     */
    public void setList(List<OperatorNumber> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * List of operations to show as cells
     */
    private List<OperatorNumber> list;

    private OnItemClickListener onItemClickListener;

    public HistoryAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView operationTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            operationTextView = itemView.findViewById(R.id.operation_textView);
        }

        public void bind(final OperatorNumber model, final OnItemClickListener listener) {
            String s = ConcatText(model);
            operationTextView.setText(s);
            itemView.setOnClickListener(v -> listener.onItemClick(model));

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

        OperatorNumber item = list.get(position);

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

    /**
     * Interface to get which item selected
     */
    public interface OnItemClickListener {
        void onItemClick(OperatorNumber operatorNumber);
    }

    /**
     * For adding operation type to operation value
     * @param operatorNumber one operation object has two variables
     * @return one string of operation type added to operation value
     */
    private static String ConcatText(OperatorNumber operatorNumber)
    {
        String s ="";
        switch (operatorNumber.getNumOperator()) {
            case Add:
              s= "+".concat(String.valueOf(operatorNumber.getNumValue()));
                break;
            case Minus:
                s= "-".concat(String.valueOf(operatorNumber.getNumValue()));
                break;
            case Multiply:
                s= "*".concat(String.valueOf(operatorNumber.getNumValue()));
                break;
            case Division:
                s= "/".concat(String.valueOf(operatorNumber.getNumValue()));
                break;
        }
        return s;
    }
}