package hr.ferit.nikoladanilovic.todoapp;

import com.google.firebase.database.Exclude;

public class ToDo {
    private String documentId;
    private String description;

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public ToDo(){
        //treba prazan konstruktor za pravilan rad s firestore-om
    }

    public ToDo(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }


}
