package com.example.databasecrud;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDB;
    EditText editTextID, editName , editEmail , editCC;
    Button buttonAdd , buttonGetData , buttonUpdate , buttonDelete , buttonViewAll , buttonDeleteAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DataBaseHelper(this);

        editTextID = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
        editEmail = findViewById(R.id.editText_email);
        editCC = findViewById(R.id.editText_CC);

        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);
        buttonUpdate = findViewById(R.id.button_update);
        buttonGetData = findViewById(R.id.button_view);
        buttonViewAll = findViewById(R.id.button_viewAll);
        buttonDeleteAll = findViewById(R.id.button_deleteAll);

       // showMessage("test","testing done");

        AddData();
        getData();
        viewAll();
        updateData();
        deleteData();
        deleteAll();


    }

    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(editName.getText().toString(),editEmail.getText().toString(),editCC.getText().toString());
                if(isInserted==true){
                    Toast.makeText(MainActivity.this,"data inserted",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this,"something went wrong",Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(MainActivity.this,"test",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void getData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextID.getText().toString();

                if(id.equals(String.valueOf(""))){
                    editTextID.setError("Enter ID");
                    return;
                }

                Cursor cursor = myDB.getData(id);
                String data = null;

                if(cursor.moveToNext()){
                    data = "ID: "+ cursor.getString(0) +"\n"+
                            "Name: "+ cursor.getString(1) +"\n"+
                            "Email: "+ cursor.getString(2) +"\n"+
                            "Subjects Count: "+ cursor.getString(3) +"\n";
                }
                showMessage("Data: ", data);
            }
        });
    }
    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDB.getallData();

                if(cursor.getCount()==0){
                    showMessage("Error","Add some data in database");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while(cursor.moveToNext()){
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Email: "+cursor.getString(2)+"\n");
                    buffer.append("SC: "+cursor.getString(3)+"\n\n");
                }
                showMessage("All Data", buffer.toString());
            }
        });
    }
    public void updateData(){

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDB.updateData(editTextID.getText().toString(),
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editCC.getText().toString());

                if(isUpdate==true){
                    Toast.makeText(MainActivity.this,"Data Modify Success!!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"OOPS!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextID.getText().toString();

                if(id.equals(String.valueOf(""))){
                    editTextID.setError("Enter ID");
                    return;
                }

                Integer deletedRow = myDB.deleteData(editTextID.getText().toString());

                if(deletedRow>0){
                    Toast.makeText(MainActivity.this,"Data Gone", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteAll(){
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteallData();
            }
        });

    }






    private void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



}



