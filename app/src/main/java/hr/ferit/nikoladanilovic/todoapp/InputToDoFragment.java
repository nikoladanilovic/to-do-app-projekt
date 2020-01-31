package hr.ferit.nikoladanilovic.todoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputToDoFragment extends Fragment implements ClickListener, View.OnClickListener{

    private static final String BUNDLE_MESSAGE = "message";

    private EditText etTitle, etDate, etTime, etDesc;
    private RadioButton rbtnToDo, rbtnFuture;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference todoRef = db.collection("todo");
    private CollectionReference oldRef = db.collection("old");
    private CollectionReference futureRef = db.collection("future");
    private Button btnSaveNote;


    public static InputToDoFragment newInstance(String message) {
        InputToDoFragment fragment = new InputToDoFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_to_do, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitle = view.findViewById(R.id.etTitle);
        etDate = view.findViewById(R.id.etDate);
        etTime = view.findViewById(R.id.etTime);
        etDesc = view.findViewById(R.id.etDesc);
        rbtnToDo = view.findViewById(R.id.rbtnToDo);
        rbtnFuture = view.findViewById(R.id.rbtnFuture);
        btnSaveNote = view.findViewById(R.id.btnPlan);
        this.btnSaveNote.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(etTitle.getText().toString().isEmpty() || (!this.rbtnToDo.isChecked() && !this.rbtnFuture.isChecked())){
            Toast.makeText(getActivity(), "Fill in at least title and check type of to-do you want to input!", Toast.LENGTH_SHORT).show();
        }else {
            String description = etTitle.getText().toString() + "\n" + etDate.getText().toString() + "\n" + etTime.getText().toString() + "\n" + etDesc.getText().toString();
            ToDo todo = new ToDo(description);
            etTitle.setText("");
            etDate.setText("");
            etTime.setText("");
            etDesc.setText("");




            if(this.rbtnToDo.isChecked()){
                todoRef.add(todo);
            }
            if(this.rbtnFuture.isChecked()){
                futureRef.add(todo);
            }
        }
    }

    @Override
    public void onClick(int position, View view) {

    }


}
