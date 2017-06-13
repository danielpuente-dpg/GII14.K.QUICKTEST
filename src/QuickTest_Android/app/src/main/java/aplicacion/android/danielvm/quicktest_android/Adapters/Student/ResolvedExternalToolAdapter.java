package aplicacion.android.danielvm.quicktest_android.Adapters.Student;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aplicacion.android.danielvm.quicktest_android.Activities.Student.GradeActivity;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Questionnaire;
import aplicacion.android.danielvm.quicktest_android.R;

/**
 * Created by Daniel on 23/03/2017.
 */

public class ResolvedExternalToolAdapter extends RecyclerView.Adapter<ResolvedExternalToolAdapter.ViewHolder> {

    private List<Questionnaire> questionaries;
    private int layout;
    private Activity activity;

    public ResolvedExternalToolAdapter(List<Questionnaire> questionaries, Activity activity, int layout) {
        this.questionaries = questionaries;
        this.layout = layout;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflamos la vista con nuestro Layout
        View view = LayoutInflater.from(activity).inflate(layout, parent, false);
        // Devolvemos la vista al View Holder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Llamamos la metodo encargado de añadir los datos propios de cada cuestionario
        holder.dataBind(this.questionaries.get(position));
    }

    @Override
    public int getItemCount() {
        if (questionaries != null)
            return questionaries.size();
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        // Elementos que forman parte de la UI del CuestionarioFragment

        private ImageView imgCuestionario;
        private TextView textViewName;
        private TextView textViewDescription;

        public ViewHolder(View view) {
            super(view);
            this.imgCuestionario = (ImageView) view.findViewById(R.id.imageViewCuestionario);
            this.textViewName = (TextView) view.findViewById(R.id.textViewName);
            this.textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);

            // Añadimos la vista de nuestro menu de contexto
            itemView.setOnCreateContextMenuListener(this);
        }

        public void dataBind(final Questionnaire questionnaire) {
            this.textViewName.setText(questionnaire.getDescripcion());
            this.textViewDescription.setText(questionnaire.getCurso());
            this.imgCuestionario.setImageResource(questionnaire.getImgIcon());

        }


        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.revisarCuestionario:
                    goToInfoQuestionaryActivity(getAdapterPosition());
                    return true;
                case R.id.noRevisarCuestionario:
                    // TODO no hacer nada
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // Obtenemos el cuestionario actual
            Questionnaire currentQuestionnaire = questionaries.get(this.getAdapterPosition());
            // Modificamos el nombre del cuestionario en función del seleccionado
            contextMenu.setHeaderTitle(currentQuestionnaire.getDescripcion());
            // Inflamos el menu de contexto
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.action_cuestionario_resolved, contextMenu);

            // Este bucle se encarga de añadir el listener para cada Item Clicked
            // Es decir, a cada Item de /menu/action_cuestionario -> le asociamos el
            // metodo onMenuItemClick
            for (int i = 0; i < contextMenu.size(); i++)
                contextMenu.getItem(i).setOnMenuItemClickListener(this);
        }

    }


    private void goToInfoQuestionaryActivity(int position) {
        // Obtenemos el questionnaire ha resolver
        Questionnaire questionnaire = questionaries.get(position);

        // Obtenemos el Id del questionnaire a resolver
        int idCuestionario = questionnaire.getIdCuestionario();
        // Obtenemos la clave del ciente;
        String clave = questionnaire.getClaveCliente();

        // Vamos al activity encargado de resolver el Test
        Intent intent = new Intent(activity, GradeActivity.class);

        intent.putExtra("idCuestionario", idCuestionario);
        intent.putExtra("idAlumno", clave);
        intent.putExtra("nombre", questionnaire.getDescripcion());

        activity.startActivity(intent);


    }

}
