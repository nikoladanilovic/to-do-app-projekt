package hr.ferit.nikoladanilovic.todoapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NameViewHolderListToDo extends RecyclerView.ViewHolder {

    private TextView tvNameCell;
    ImageView ivSignxCell;


    public NameViewHolderListToDo(@NonNull View itemView, final ClickListener listener) {
        super(itemView);

        tvNameCell = itemView.findViewById(R.id.tvNameCell);
        ivSignxCell = (ImageView) itemView.findViewById(R.id.ivSignxCell);

        ivSignxCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClick(getAdapterPosition(), v);

            }
        });
    }

    public void setText(String name){
        tvNameCell.setText(name);
    }
}
