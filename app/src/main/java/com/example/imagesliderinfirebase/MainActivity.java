package com.example.imagesliderinfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ImageSlider slider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//removing the status bar
        slider=findViewById(R.id.image_slider);
        List<SlideModel> slideModelList=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("ImageSlider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    //everything relating to the imageSlider is to be done here
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d:snapshot.getChildren())
                            slideModelList.add(new SlideModel(d.child("url").getValue().toString(),d.child("title").getValue().toString(), ScaleTypes.FIT));
                    slider.setImageList(slideModelList);

                        slider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                //gets the object at 'i' index from the slideModelList
                                Toast.makeText(getApplicationContext(),"title: "+slideModelList.get(i).getTitle(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}