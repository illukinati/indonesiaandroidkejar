package id.ignitech.iak.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import id.ignitech.iak.Model.ModelFasilitator;
import id.ignitech.iak.R;

/**
 * Created by Asus on 22/11/2017.
 */

public class FasilitatorAdapter extends RecyclerView.Adapter<FasilitatorAdapter.ViewHolder> {
    private ArrayList<ModelFasilitator> modelFasilitators;
    Context context;
    Typeface avenir;

    public FasilitatorAdapter(ArrayList<ModelFasilitator> modelFasilitators) {
        this.modelFasilitators = modelFasilitators;
    }

    @Override
    public FasilitatorAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fasilitator, viewGroup, false);
        return new FasilitatorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FasilitatorAdapter.ViewHolder viewHolder, final int i) {

        Log.w("ayolah", modelFasilitators.get(i).getNama());
        viewHolder.txtNama.setText(modelFasilitators.get(i).getNama());
        viewHolder.txtAlamat.setText(modelFasilitators.get(i).getAlamat());
        viewHolder.txtTanggal1.setText(modelFasilitators.get(i).getTanggal1());
        viewHolder.txtTanggal2.setText(modelFasilitators.get(i).getTanggal2());
        viewHolder.txtQuota.setText(modelFasilitators.get(i).getQuota() + " Orang");
        viewHolder.txtLevel.setText(modelFasilitators.get(i).getLevel());
        viewHolder.cvFasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return  modelFasilitators == null ? 0 : modelFasilitators.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtAlamat, txtTanggal1, txtTanggal2, txtQuota, txtLevel;
        private CardView cvFasil;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();

            txtNama = (TextView) view.findViewById(R.id.txt_nama_fasil);
            txtAlamat = (TextView) view.findViewById(R.id.txt_alamat_fasil);
            txtTanggal1 = (TextView) view.findViewById(R.id.txt_tanggal_fasil);
            txtTanggal2 = (TextView) view.findViewById(R.id.txt_tanggal_fasil2);
            txtQuota  = (TextView) view.findViewById(R.id.txt_kuota_fasil);
            txtLevel  = (TextView) view.findViewById(R.id.txt_level_fasil);
            cvFasil = (CardView) view.findViewById(R.id.cv_fasil);
        }
    }
}
