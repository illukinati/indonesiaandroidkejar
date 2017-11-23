package id.ignitech.iak.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import id.ignitech.iak.Adapter.FasilitatorAdapter;
import id.ignitech.iak.MainActivity;
import id.ignitech.iak.Model.ModelFasilitator;
import id.ignitech.iak.R;

public class FasilActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference mDatabase;
    RecyclerView rvFasil;

    TextView txtMinggu, txtKelas, txtProfile;

    CharSequence kelas[] = new CharSequence[] {"Beginner", "Intermediate", "Advance"};
    CharSequence minggu[] = new CharSequence[] {"Minggu ke-1", "Minggu ke-2", "Minggu ke-3", "Minggu ke-4"};
    AlertDialog.Builder builderMinggu, builderKelas;

    ArrayList<ModelFasilitator> list;

    int mng = 0;
    String kls = "semua";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasil);

        rvFasil = (RecyclerView) findViewById(R.id.rvFasil);
        txtKelas    = (TextView) findViewById(R.id.txt_kelas);
        txtMinggu   = (TextView) findViewById(R.id.txt_minggu);
        txtProfile  = (TextView) findViewById(R.id.txt_profil);

        txtKelas.setOnClickListener(this);
        txtMinggu.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("fasilitator");

        getDataUser();
        getDataFirebase();

        if(currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        builderMinggu = new AlertDialog.Builder(this);
        builderMinggu.setTitle("Pilih Minggu");
        builderMinggu.setItems(minggu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        txtMinggu.setText("Minggu ke-1");
                        mng = 1;
                        getDataFirebase();
                        break;
                    case 1:
                        txtMinggu.setText("Minggu ke-2");
                        mng = 2;
                        getDataFirebase();
                        break;
                    case 2:
                        txtMinggu.setText("Minggu ke-3");
                        mng = 3;
                        getDataFirebase();
                        break;
                    case 3:
                        txtMinggu.setText("Minggu ke-4");
                        mng = 4;
                        getDataFirebase();
                        break;
                }
            }
        });

        builderKelas = new AlertDialog.Builder(this);
        builderKelas.setTitle("Pilih Tingkatan Kelas");
        builderKelas.setItems(kelas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        txtKelas.setText("Beginner");
                        kls = "Beginner";
                        getDataFirebase();
                        break;
                    case 1:
                        txtKelas.setText("Intermediate");
                        kls = "Intermediate";
                        getDataFirebase();
                        break;
                    case 2:
                        txtKelas.setText("Advance");
                        kls = "Advance";
                        getDataFirebase();
                        break;
                }
            }
        });
    }

    public void getDataUser (){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uid = mAuth.getCurrentUser().getUid();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    if(uid.equals(dataSnapshot1.getKey())){
                        txtProfile.setText(dataSnapshot1.getValue(ModelFasilitator.class).getNama());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void getDataFirebase(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list = new ArrayList<ModelFasilitator>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    if(mng == 1) {
                        ModelFasilitator value = dataSnapshot1.getValue(ModelFasilitator.class);
                        if(value.getTanggal1().equalsIgnoreCase("11 November 2017") ||
                                value.getTanggal2().equalsIgnoreCase("11 November 2017")){
                            switch (kls){
                                case "Beginner":
                                    if(value.getLevel().equalsIgnoreCase("Beginner"))
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                case "Intermediate":
                                    if(value.getLevel().equalsIgnoreCase("Intermediate"))
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                case "Advance":
                                    if(value.getLevel().equalsIgnoreCase("Advance"))
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                default:
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                        value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                            }
                        }
                    } else if(mng == 2){
                        ModelFasilitator value = dataSnapshot1.getValue(ModelFasilitator.class);
                        if(value.getTanggal1().equalsIgnoreCase("18 November 2017") ||
                                value.getTanggal2().equalsIgnoreCase("18 November 2017")){
                            switch (kls){
                                case "Beginner":
                                    if(value.getLevel().equalsIgnoreCase("Beginner"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                case "Intermediate":
                                    if(value.getLevel().equalsIgnoreCase("Intermediate"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                case "Advance":
                                    if(value.getLevel().equalsIgnoreCase("Advance"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                default:
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                            }
                        }
                    } else if(mng == 3){
                        ModelFasilitator value = dataSnapshot1.getValue(ModelFasilitator.class);
                        if(value.getTanggal1().equalsIgnoreCase("25 November 2017") ||
                                value.getTanggal2().equalsIgnoreCase("25 November 2017")){
                            switch (kls){
                                case "Beginner":
                                    if(value.getLevel().equalsIgnoreCase("Beginner"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                case "Intermediate":
                                    if(value.getLevel().equalsIgnoreCase("Intermediate"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                case "Advance":
                                    if(value.getLevel().equalsIgnoreCase("Advance"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                default:
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                            }
                        }
                    } else if (mng == 4){
                        ModelFasilitator value = dataSnapshot1.getValue(ModelFasilitator.class);
                        if(value.getTanggal1().equalsIgnoreCase("2 Desember 2017") ||
                                value.getTanggal2().equalsIgnoreCase("2 Desember 2017")){
                            switch (kls){
                                case "Beginner":
                                    if(value.getLevel().equalsIgnoreCase("Beginner"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                case "Intermediate":
                                    if(value.getLevel().equalsIgnoreCase("Intermediate"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                case "Advance":
                                    if(value.getLevel().equalsIgnoreCase("Advance"))
                                        list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                                value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                                default:
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                    break;
                            }
                        }
                    } else {
                        ModelFasilitator value = dataSnapshot1.getValue(ModelFasilitator.class);
                        switch (kls) {
                            case "Beginner":
                                if (value.getLevel().equalsIgnoreCase("Beginner"))
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                break;
                            case "Intermediate":
                                if (value.getLevel().equalsIgnoreCase("Intermediate"))
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                break;
                            case "Advance":
                                if (value.getLevel().equalsIgnoreCase("Advance"))
                                    list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                            value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                break;
                            default:
                                list.add(new ModelFasilitator(value.getNama(), value.getTanggal1(),
                                        value.getTanggal2(), value.getAlamat(), value.getQuota(), value.getLevel()));
                                break;
                        }
                    }
                }
                FasilitatorAdapter fasilitatorAdapter = new FasilitatorAdapter(list);
                rvFasil.setAdapter(fasilitatorAdapter);
                rvFasil.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FasilActivity.this, LinearLayoutManager.HORIZONTAL, true);
                linearLayoutManager.setReverseLayout(false);
                rvFasil.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.more_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menu_logout){
                    mAuth.signOut();
                    Intent intent = new Intent(FasilActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if (item.getItemId() == R.id.menu_profile){
                    Intent intent = new Intent(FasilActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == txtMinggu){
            builderMinggu.show();
        } else if (view == txtKelas){
            builderKelas.show();
        }
    }
}
