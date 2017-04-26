package aplicacion.android.danielvm.quicktest_android.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Models.Respuesta;
import aplicacion.android.danielvm.quicktest_android.Models.Test;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Created by Daniel on 23/03/2017.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<Test> tests;
    private int layout;
    private Context context;


    public TestAdapter(List<Test> tests, int layout) {
        this.tests = tests;
        this.layout = layout;
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

    class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener*/ {

        // Elementos que forman parte de la UI del CuestionarioFragment

        private TextView pregunta;
        private LinearLayout ll;

        public ViewHolder(View view) {
            super(view);

            this.pregunta = (TextView) view.findViewById(R.id.textViewStatement);

            ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll = (LinearLayout) view.findViewById(R.id.radioGroup);

            // Añadimos la vista de nuestro menu de contexto
            //itemView.setOnCreateContextMenuListener(activity);
        }

        public void dataBind(final Test test, int position) {

            // Procesamiento de los datos a renderizar

            // Eliminamos los anteriores RadioButtons del CardView
            ll.removeAllViews();

            // Renderizamos el titulo
            this.pregunta.setText((position + 1) + " - " + test.getPregunta());

            // Renderizamos las opciones
            RadioButton radioButton;
            for (Respuesta r : test.getRespuestas()) {
                radioButton = new RadioButton(context);
                radioButton.setText(r.getTitulo());
                ll.addView(radioButton);
            }
        }


        /*
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.resolverCuestionario:
                    // TODO Lógica de resolver un determinado cuestionario
                    return true;
                case R.id.abortarCuestionario:
                    // TODO no hacer nada
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // Obtenemos el cuestionario actual
            Test currentTest = tests.get(this.getAdapterPosition());
            // Modificamos el nombre del cuestionario en función del seleccionado
            contextMenu.setHeaderTitle(currentTest.setHeaderTitle());
            // Inflamos el menu de contexto
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.action_cuestionario, contextMenu);

            // Este bucle se encarga de añadir el listener para cada Item Clicked
            // Es decir, a cada Item de /menu/action_cuestionario -> le asociamos el
            // metodo onMenuItemClick
            for (int i = 0; i < contextMenu.size(); i++)
                contextMenu.getItem(i).setOnMenuItemClickListener(this);
        }
        */

    }
}
