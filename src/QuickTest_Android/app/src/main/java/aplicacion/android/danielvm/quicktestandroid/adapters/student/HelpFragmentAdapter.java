package aplicacion.android.danielvm.quicktestandroid.adapters.student;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aplicacion.android.danielvm.quicktestandroid.R;
import aplicacion.android.danielvm.quicktestandroid.models.android.Help;

/**
 * Created by Daniel on 17/06/2017.
 */

public class HelpFragmentAdapter extends RecyclerView.Adapter<HelpFragmentAdapter.ViewHolder> {

    private List<Help> helps;
    private int layout;
    private Activity activity;

    public HelpFragmentAdapter(List<Help> helps, Activity activity, int layout) {
        this.helps = helps;
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
        // Llamamos la metodo encargado de añadir los datos propios de cada cuestionario.
        holder.dataBind(this.helps.get(position));
    }

    @Override
    public int getItemCount() {
        if (helps != null)
            return helps.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        // Elementos de la UI
        private ImageView imageView;
        private TextView textViewType;
        private TextView textViewDescription;

        public ViewHolder(View view) {
            super(view);

            this.imageView = (ImageView) view.findViewById(R.id.imageViewTypeWildcard);
            this.textViewType = (TextView) view.findViewById(R.id.textViewTypeWildcard);
            this.textViewDescription = (TextView) view.findViewById(R.id.textViewHelpFragment);
        }

        public void dataBind(final Help help) {
            this.imageView.setImageResource(help.getImg());
            this.textViewType.setText(help.getType());
            this.textViewDescription.setText(help.getText());
        }
    }
}
