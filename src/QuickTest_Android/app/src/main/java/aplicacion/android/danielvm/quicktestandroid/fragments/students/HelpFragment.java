package aplicacion.android.danielvm.quicktestandroid.fragments.students;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aplicacion.android.danielvm.quicktestandroid.R;

/**
 * Clase HelpFragment encargada de mostrar una ayuda sobre el funcionamiento de los comodines
 */
public class HelpFragment extends Fragment {

    public HelpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        return view;
    }

}
