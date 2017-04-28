package aplicacion.android.danielvm.quicktest_android.Adapters;


import android.content.Context;
import android.support.annotation.BoolRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.APIRest.Respuesta;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Test;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Created by Daniel on 23/03/2017.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<Test> tests;
    private int layout;
    private Context context;

    private HashMap<Integer, Integer> respuestas;
    private HashMap<Integer, Boolean> flags;


    public TestAdapter(List<Test> tests, int layout) {
        this.tests = tests;
        this.layout = layout;
        respuestas = new HashMap();
        flags = new HashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflamos la vista con nuestro Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        // Devolvemos la vista al View Holder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Llamamos la metodo encargado de añadir los datos propios de cada cuestionario
        holder.dataBind(this.tests.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (tests != null)
            return tests.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        // Elementos que forman parte de la UI del CuestionarioFragment

        private TextView pregunta;
        private RadioGroup radioGroup;

        public ViewHolder(View view) {
            super(view);

            this.pregunta = (TextView) view.findViewById(R.id.textViewStatement);
            this.radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

            // Añadimos la vista de nuestro menu de contexto
            //itemView.setOnCreateContextMenuListener(activity);
        }

        public void dataBind(final Test test, final int position) {

            radioGroup.removeAllViews();
            this.pregunta.setText((position + 1) + " - " + test.getPregunta());
            int i = 0;
            for (Respuesta r : test.getRespuestas()) {
                RadioButton nuevoRadio = crearRadioButton(r.getTitulo(), i);
                radioGroup.addView(nuevoRadio);
                i++;
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    respuestas.put(getAdapterPosition(), checkedId);
                    flags.put(getAdapterPosition(), true);
                    Log.d("Listener->Position : " + getAdapterPosition(), "Btn: " + checkedId);
                }
            });

        }

        private RadioButton crearRadioButton(String marca, int i) {

            RadioButton nuevoRadio = new RadioButton(context);
            LinearLayout.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            nuevoRadio.setLayoutParams(params);
            nuevoRadio.setText(marca);
            nuevoRadio.setTag(marca);
            nuevoRadio.setId(i);

            if (flags.containsKey(getAdapterPosition()) == true && respuestas.get(getAdapterPosition()) == i) {
                int id = respuestas.get(getAdapterPosition());
                Log.d("Position : " + i, "Btn: " + id);
                nuevoRadio.setChecked(true);
            }

            return nuevoRadio;
        }
    }
}
