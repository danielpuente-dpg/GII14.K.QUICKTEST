package aplicacion.android.danielvm.quicktest_android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import aplicacion.android.danielvm.quicktest_android.API.APIMoodle;
import aplicacion.android.danielvm.quicktest_android.API.APIServices.MoodleService;
import aplicacion.android.danielvm.quicktest_android.Activities.MainActivity;
import aplicacion.android.danielvm.quicktest_android.Adapters.ExternalToolAdapter;
import aplicacion.android.danielvm.quicktest_android.Models.Android.Cuestionario;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Course;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.ExternalTool;
import aplicacion.android.danielvm.quicktest_android.Models.Moodle.Module;
import aplicacion.android.danielvm.quicktest_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class CuestionarioFragment extends Fragment {

    // Elemento de la UI mediante RecyclerView

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Cuestionario> cuestionarios;

    public boolean flag = true;


    public CuestionarioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        int valor = new MainActivity().modules.size();

        cuestionarios = new ArrayList<>();
        for (int i = 1; i <= new MainActivity().NUM_EXTERNAL_TOOLS; i++) {
            getExternalTool(i);
        }

        // Instanciamos los elementos de la UI

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExternalToolAdapter(this.cuestionarios, getActivity(), R.layout.recycler_view_item);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }

    private void getExternalTool(int counter) {
        Retrofit retrofit = APIMoodle.getApi();
        MoodleService service = retrofit.create(MoodleService.class);

        Call<ExternalTool> call = service.getExternalTools(new MainActivity().TOKEN_WS, APIMoodle.GET_EXTERNAL_TOOL, APIMoodle.FORMAT_JSON, counter);

        call.enqueue(new Callback<ExternalTool>() {
            @Override
            public void onResponse(Call<ExternalTool> call, retrofit2.Response<ExternalTool> response) {
                if (response.isSuccessful()) {
                    if (response.body().getEndpoint() != null) {
                        addExternalTool(response.body(), true);
                    } else {
                        addExternalTool(response.body(), false);
                    }
                } else {
                    Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ExternalTool> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addExternalTool(ExternalTool externalTool, boolean status) {
        ExternalTool et = externalTool;
        Cuestionario cuestionario;

        if (status == true && externalTool.getEndpoint().equals("http://localhost/_QuickTest_TFG/index.php")) {

            // Comprobamos que ese usuario tenga asignado ese cuestionario

            // Obtenemos la descripcion
            String description = externalTool.getParameters().get(10).getValue();
            for(Module module : new MainActivity().modules){
                if(description.equals(module.getName())){
                    // Obtenemos el Id cuestionario
                    String idCuestionario = externalTool.getParameters().get(11).getValue();

                    cuestionario = new Cuestionario(idCuestionario, description, R.mipmap.ic_icon_cuestionario);
                    cuestionarios.add(cuestionario);

                    Log.d("AddExternalTool", description);
                    break;
                }
            }



          /*  // Obtenemos el Id cuestionario
            String idCuestionario = externalTool.getParameters().get(11).getValue();

            cuestionario = new Cuestionario(idCuestionario, description, R.mipmap.ic_icon_cuestionario);
            cuestionarios.add(cuestionario);

            Log.d("AddExternalTool", description);*/
        }/* else {
            flag = false;
            Log.d("AddExternalTool", "Flag: " + flag);
        }*/
    }


}
