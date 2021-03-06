package aplicacion.android.danielvm.quicktestandroid.adapters.student;

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

import aplicacion.android.danielvm.quicktestandroid.activities.student.GradeActivity;
import aplicacion.android.danielvm.quicktestandroid.models.android.Questionnaire;
import aplicacion.android.danielvm.quicktestandroid.R;

/**
 * Clase ResolvedExternalToolAdapter encargada de tratar la logica del adaptor de los
 * cuestionarios resueltos.
 *
 * @author Daniel Puente Gabarri.
 */
public class ResolvedExternalToolAdapter extends RecyclerView.Adapter<ResolvedExternalToolAdapter.ViewHolder> {

    private List<Questionnaire> questionnaires;
    private int layout;
    private Activity activity;

    /**
     * Constructor de la clase.
     * @param questionnaires, questionnaires.
     * @param activity, activity.
     * @param layout, layout.
     */
    public ResolvedExternalToolAdapter(List<Questionnaire> questionnaires, Activity activity, int layout) {
        this.questionnaires = questionnaires;
        this.layout = layout;
        this.activity = activity;
    }

    /**
     * Metodo encargado de inflar la vista.
     *
     * @param parent,   parent.
     * @param viewType, viewType.
     * @return ViewHolder, view.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflamos la vista con nuestro Layout
        View view = LayoutInflater.from(activity).inflate(layout, parent, false);
        // Devolvemos la vista al View Holder
        return new ViewHolder(view);
    }

    /**
     * Metodo encargado de enlazar el contenido de cada vista.
     *
     * @param holder,   holder.
     * @param position, position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Llamamos la metodo encargado de añadir los datos propios de cada cuestionario
        holder.dataBind(this.questionnaires.get(position));
    }

    /**
     * Metodo encargado de proporcionar el numero de cuestionarios.
     *
     * @return int, tamaño.
     */
    @Override
    public int getItemCount() {
        if (questionnaires != null)
            return questionnaires.size();
        return 0;
    }

    /**
     * Clase interna ViewHolder encarga de instanciar y de actualizar la
     * informacion de los elementos que forman la UI.
     *
     * @author Daniel Puente Gabarri.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        // Elementos de la UI.
        private ImageView imgCuestionario;
        private TextView textViewName;
        private TextView textViewDescription;

        /**
         * Constructor de la clase.
         * @param view, view.
         */
        public ViewHolder(View view) {
            super(view);
            this.imgCuestionario = (ImageView) view.findViewById(R.id.imageViewCuestionario);
            this.textViewName = (TextView) view.findViewById(R.id.textViewName);
            this.textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);

            // Añadimos la vista de nuestro menu de contexto
            itemView.setOnCreateContextMenuListener(this);
        }

        /**
         * Metodo encargado de instanciar los elementos de para cada vista.
         *
         * @param questionnaire
         */
        public void dataBind(final Questionnaire questionnaire) {
            this.textViewName.setText(questionnaire.getDescription());
            this.textViewDescription.setText(questionnaire.getCourseName());
            this.imgCuestionario.setImageResource(questionnaire.getImgIcon());

        }

        /**
         * Metodo encarcago de la logica a seguir en el menu de contexto.
         *
         * @param item, item.
         * @return boolean, boolean.
         */
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.checkQuestionnaire:
                    goToGradeActivity(getAdapterPosition());
                    return true;
                case R.id.doNotCheckQuestionnaire:
                    // no hacer nada.
                    return true;
                default:
                    return false;
            }
        }

        /**
         * Metodo encargado de inflar el menu de contexto.
         *
         * @param contextMenu, contextMenu
         * @param v,           view.
         * @param menuInfo,    menuInfo.
         */
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // Obtenemos el cuestionario actual
            Questionnaire currentQuestionnaire = questionnaires.get(this.getAdapterPosition());
            // Modificamos el nombre del cuestionario en función del seleccionado
            String msg = "¿Desea revisar el " + currentQuestionnaire.getDescription() + "?";
            contextMenu.setHeaderTitle(msg);
            // Inflamos el menu de contexto
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.action_resolved_questionnaire, contextMenu);

            // Este bucle se encarga de añadir el listener para cada Item Clicked
            // Es decir, a cada Item de /menu/action_un_resolved_questionnaire -> le asociamos el
            // metodo onMenuItemClick
            for (int i = 0; i < contextMenu.size(); i++)
                contextMenu.getItem(i).setOnMenuItemClickListener(this);
        }

    }

    /**
     * Metodo encargado de direccionar a GradeActivity para resolver el cuestionario.
     *
     * @param position, position.
     */
    private void goToGradeActivity(int position) {
        // Obtenemos el questionnaire ha resolver
        Questionnaire questionnaire = questionnaires.get(position);

        // Obtenemos el Id del questionnaire a resolver
        int idQuestionnaire = questionnaire.getIdQuestionnaire();
        // Obtenemos la clave del ciente;
        String idStudent = questionnaire.getClientKey();

        // Vamos al activity encargado de resolver el Test
        Intent intent = new Intent(activity, GradeActivity.class);

        intent.putExtra("idQuestionnaire", idQuestionnaire);
        intent.putExtra("idStudent", idStudent);
        intent.putExtra("nameQuestionnaire", questionnaire.getDescription());

        activity.startActivity(intent);


    }

}
