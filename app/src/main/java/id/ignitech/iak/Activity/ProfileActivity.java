package id.ignitech.iak.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import id.ignitech.iak.Item.Configuration;
import id.ignitech.iak.Item.Message;
import id.ignitech.iak.Model.ModelFasilitator;
import id.ignitech.iak.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference mDatabase;
    Toolbar toolbar;

    EditText edtNama, edtAlamat, edtQuota;
    TextView txtTanggal, txtTanggal2;
    Button btnSimpan;
    Spinner spinner;

    String level;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date1, date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference(Configuration.Database.DATABASE_NAME);

        edtNama     = (EditText) findViewById(R.id.edt_nama);
        edtAlamat   = (EditText) findViewById(R.id.edt_alamat);
        edtQuota    = (EditText) findViewById(R.id.edt_quota);
        txtTanggal  = (TextView) findViewById(R.id.edt_tanggal);
        txtTanggal2 = (TextView) findViewById(R.id.edt_tanggal2);
        btnSimpan   = (Button) findViewById(R.id.btn_simpan);

        date1 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(txtTanggal);
            }
        };

        date2 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(txtTanggal2);
            }
        };
        spinner = (Spinner) findViewById(R.id.spn_level);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.level, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        txtTanggal.setOnClickListener(this);
        txtTanggal2.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        getDataUser();
    }

    private void updateLabel(TextView edtDate) {
        String myFormat = Configuration.DatePicker.DATE_FORMAT; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void getDataUser (){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uid = mAuth.getCurrentUser().getUid();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    if(uid.equals(dataSnapshot1.getKey())){
                        edtNama.setText(dataSnapshot1.getValue(ModelFasilitator.class).getNama());
                        edtAlamat.setText(dataSnapshot1.getValue(ModelFasilitator.class).getAlamat());
                        edtQuota.setText(dataSnapshot1.getValue(ModelFasilitator.class).getQuota());
                        txtTanggal.setText(dataSnapshot1.getValue(ModelFasilitator.class).getTanggal1());
                        txtTanggal2.setText(dataSnapshot1.getValue(ModelFasilitator.class).getTanggal2());
                        spinner.setSelection(getIndex(spinner, dataSnapshot1.getValue(ModelFasilitator.class).getLevel()));
                        level = dataSnapshot1.getValue(ModelFasilitator.class).getLevel();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    @Override
    public void onClick(View view) {
        if(view == btnSimpan){
            updateData();
        } else if(view == txtTanggal){
            DatePickerDialog dp = new DatePickerDialog(this, date1, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dp.show();

        } else if(view == txtTanggal2){
            new DatePickerDialog(this, date2, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        level = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void updateData(){
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("nama").setValue(edtNama.getText().toString());
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("alamat").setValue(edtAlamat.getText().toString());
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("level").setValue(level);
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("quota").setValue(edtQuota.getText().toString());
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("tanggal1").setValue(txtTanggal.getText().toString());
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("tanggal2").setValue(txtTanggal2.getText().toString());

        Toast.makeText(this, Message.Profile.UPDATED, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
