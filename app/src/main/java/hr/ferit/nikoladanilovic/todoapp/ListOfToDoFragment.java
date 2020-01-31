package hr.ferit.nikoladanilovic.todoapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfToDoFragment extends Fragment implements ClickListener, View.OnClickListener {

    private static final String BUNDLE_MESSAGE = "message";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference todoRef = db.collection("todo");
    private CollectionReference oldRef = db.collection("old");
    private CollectionReference futureRef = db.collection("future");
    public ListenerRegistration newListenerTodo;


    private RecyclerView recyclerList;
    private NameAdapterListToDo adapterList;

    private List<String> dataTodo = new ArrayList<>();
    private List<String> dataOld = new ArrayList<>();
    private List<String> dataFuture = new ArrayList<>();
    private List<String> idTodo = new ArrayList<>();
    private List<String> idOld = new ArrayList<>();
    private List<String> idFuture = new ArrayList<>();

    public static ListOfToDoFragment newInstance(String message) {
        ListOfToDoFragment fragment = new ListOfToDoFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_list_to_do, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerList = view.findViewById(R.id.recyclerListToDo);
        adapterList = new NameAdapterListToDo(this);
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerList.setAdapter(adapterList);
    }

    @Override
    public void onStart(){
        super.onStart();

        String newstr = "default";
        if(getArguments() != null) {
            String args_new_str = getArguments().getString(BUNDLE_MESSAGE);
            if (args_new_str != null && !args_new_str.isEmpty()){
                newstr = args_new_str;
            }
        }
        if (newstr.equals("todo")) {
            if(getActivity() != null) {

                todoRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        dataTodo.clear();
                        idTodo.clear();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            ToDo todo = documentSnapshot.toObject(ToDo.class);
                            todo.setDocumentId(documentSnapshot.getId());

                            String documentId = todo.getDocumentId();
                            String description = todo.getDescription();


                            idTodo.add(documentId);
                            dataTodo.add(description);
                            adapterList.setDataForList(dataTodo);
                        }
                    }
                });

            }
        }
        if (newstr.equals("old")) {
            if(getActivity() != null) {

                oldRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        dataOld.clear();
                        idOld.clear();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            ToDo todo = documentSnapshot.toObject(ToDo.class);
                            todo.setDocumentId(documentSnapshot.getId());

                            String documentId = todo.getDocumentId();
                            String description = todo.getDescription();


                            idOld.add(documentId);
                            dataOld.add(description);
                            adapterList.setDataForList(dataOld);
                        }
                    }
                });

            }
        }

        if (newstr.equals("future")) {
            if(getActivity() != null) {
                futureRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        dataFuture.clear();
                        idFuture.clear();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            ToDo todo = documentSnapshot.toObject(ToDo.class);
                            todo.setDocumentId(documentSnapshot.getId());

                            String documentId = todo.getDocumentId();
                            String description = todo.getDescription();

                            idFuture.add(documentId);
                            dataFuture.add(description);
                            adapterList.setDataForList(dataFuture);
                        }
                    }
                });
            }
        }

    }




    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClick(final int position, View view) {

        if(view.getId() == R.id.ivSignxCell){
            String newstr = "default";
            if(getArguments() != null) {
                String args_new_str = getArguments().getString(BUNDLE_MESSAGE);
                if (args_new_str != null && !args_new_str.isEmpty()){
                    newstr = args_new_str;
                }
            }
            if (newstr.equals("todo")) {
                todoRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){

                            ToDo todo = documentSnapshot.toObject(ToDo.class);
                            todo.setDocumentId(documentSnapshot.getId());

                            if(idTodo.get(position).equals(todo.getDocumentId())) {
                                String documentIdToRemove = idTodo.get(position);
                                todoRef.document(documentIdToRemove).delete();
                                idTodo.remove(position);
                                dataTodo.remove(position);
                                adapterList.notifyItemRemoved(dataTodo.size());
                                adapterList.setDataForList(dataTodo);
                                oldRef.add(todo);
                                break;
                            }
                        }
                    }
                });
            }
            if (newstr.equals("old")) {
                oldRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            ToDo todo = documentSnapshot.toObject(ToDo.class);
                            todo.setDocumentId(documentSnapshot.getId());

                            if(idOld.get(position).equals(todo.getDocumentId())) {
                                String documentIdToRemove = idOld.get(position);
                                oldRef.document(documentIdToRemove).delete();
                                idOld.remove(position);
                                dataOld.remove(position);
                                adapterList.notifyItemRemoved(dataOld.size());
                                adapterList.setDataForList(dataOld);
                                break;
                            }
                        }
                    }
                });
            }
            if (newstr.equals("future")) {
                futureRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            ToDo todo = documentSnapshot.toObject(ToDo.class);
                            todo.setDocumentId(documentSnapshot.getId());

                            if(idFuture.get(position).equals(todo.getDocumentId())) {
                                String documentIdToRemove = idFuture.get(position);
                                futureRef.document(documentIdToRemove).delete();
                                idFuture.remove(position);
                                dataFuture.remove(position);
                                adapterList.notifyItemRemoved(dataFuture.size());
                                adapterList.setDataForList(dataFuture);
                                oldRef.add(todo);
                                break;
                            }
                        }
                    }
                });
            }
        }
    }
}