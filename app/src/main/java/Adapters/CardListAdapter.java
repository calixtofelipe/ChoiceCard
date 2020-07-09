package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import Interface.RVHackConsultaCard;
import bancoDados.dao.CardDAO;
import objetos.Card;
import save.topdia.R;

/**
 * Created by ADMIN on 17/04/2017.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private RVHackConsultaCard mRVHackConsultaCard;
    private LinkedList<Card> mDataset;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public CardListAdapter(Context c, LinkedList<Card> myDataset) {
        mContext = c;
        mDataset = myDataset;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = mLayoutInflater.inflate(R.layout.itens_adapter_card, parent, false);
        CardViewHolder mvh = new CardViewHolder(v);
        return mvh;
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addListItem(Card c, int position) {
        mDataset.add(c);
        notifyItemInserted(position);
    }

    public void setRVHackConsultaCard(RVHackConsultaCard r) {
        this.mRVHackConsultaCard = r;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //Log.d("AgendaAdapter", "ACAO: " + (mDataset.get(position).getAcao()==null ? "0" : mDataset.get(position).getAcao()));
        holder.tvNumber.setText(mDataset.get(position).getNumero());
        holder.tvApelido.setText(mDataset.get(position).getApelido());
        holder.tvDtCompra.setText(mDataset.get(position).getDiaCompra().toString());


    }


    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvNumber;
        public TextView tvApelido;
        public TextView tvDtCompra;



        public CheckBox ImgOnOff;

        public CardViewHolder(View itemView) {
            super(itemView);

            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            tvApelido = (TextView) itemView.findViewById(R.id.tvApelido);
            tvDtCompra = (TextView) itemView.findViewById(R.id.tvDtCompra);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.i("CardListApadter","LongClick"+getAdapterPosition());
                    mRVHackConsultaCard.onLongPressClickListener(view,getAdapterPosition());
                    CardDAO cardDAO = new CardDAO(mContext);
                    cardDAO.deleteItem(getAdapterPosition());
                    mDataset.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyDataSetChanged();
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {

            if (mRVHackConsultaCard != null) {
                mRVHackConsultaCard.onClickListener(v, getAdapterPosition());
                //Log.i("ConsultPedAdapter","onClick"+getAdapterPosition());
            }
        }


    }
}