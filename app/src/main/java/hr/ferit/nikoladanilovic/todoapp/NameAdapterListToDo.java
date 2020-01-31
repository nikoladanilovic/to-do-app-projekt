package hr.ferit.nikoladanilovic.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NameAdapterListToDo extends RecyclerView.Adapter<NameViewHolderListToDo> {

    private List<String> dataList = new ArrayList<>();
    private ClickListener listener;

    public NameAdapterListToDo(ClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NameViewHolderListToDo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_for_recycle_view, parent, false);
        return new NameViewHolderListToDo(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolderListToDo holder, int position) {
        holder.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataForList(List<String> data) {
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
        notifyItemInserted(data.size());
    }
}
