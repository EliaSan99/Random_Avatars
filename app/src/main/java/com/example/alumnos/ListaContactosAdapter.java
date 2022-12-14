package com.example.alumnos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alumnos.EditarActivity;
import com.example.alumnos.R;
import com.example.alumnos.VerActivity;
import com.example.alumnos.Contactos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    private Context context;
    ArrayList<Contactos> listaContactos;
    ArrayList<Contactos> listaOriginal;


    public ListaContactosAdapter(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
        this.context = context;

    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, null, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
       // holder.cvEfecto.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));

        holder.viewNombre.setText(listaContactos.get(position).getNombre());
        holder.viewMatricula.setText(listaContactos.get(position).getMatricula());
        holder.viewApellidos.setText(listaContactos.get(position).getApellidos());
        holder.viewApellidoM.setText(listaContactos.get(position).getApellidoM());
        holder.viewSexo.setText(listaContactos.get(position).getSexo());
        holder.viewFecha_nacimiento.setText(listaContactos.get(position).getFecha_nacimiento());
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Contactos> collecion = listaContactos.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaContactos.clear();
                listaContactos.addAll(collecion);
            } else {
                for (Contactos c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaContactos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewMatricula, viewApellidos, viewApellidoM,viewSexo,viewFecha_nacimiento;
        CardView cvEfecto;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            cvEfecto = itemView.findViewById(R.id.card);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewMatricula = itemView.findViewById(R.id.viewMatricula);
            viewApellidos = itemView.findViewById(R.id.viewApellidos);
            viewApellidoM = itemView.findViewById(R.id.viewApellidoM);
            viewSexo = itemView.findViewById(R.id.viewSexo);
            viewFecha_nacimiento = itemView.findViewById(R.id.viewFecha_nacimiento);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EditarActivity.class);
                    intent.putExtra("ID", listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
