package com.android.androidannotactions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.androidannotactions.Util.Alertas;
import com.android.androidannotactions.Util.DataSerial;
import com.android.androidannotactions.adapter.GoogleAdapter;
import com.android.androidannotactions.model.AddressComponent;
import com.android.androidannotactions.model.DataRequestCadastro;
import com.android.androidannotactions.model.DataResponseCadastro;
import com.android.androidannotactions.model.ResponseGoogle;
import com.android.androidannotactions.rest.RestV;
import com.google.android.gms.drive.realtime.internal.event.ObjectChangedDetails;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@Fullscreen
@EActivity(R.layout.activity_main)

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> listEnderecoResult;
    public static ResponseGoogle responseEnderecoGoogle;
    @ViewById
    public AutoCompleteTextView rua;
    @ViewById
    public EditText nomeLocal;
    @ViewById
    public EditText numero;
    @ViewById
    public EditText bairro;
    @ViewById
    public EditText cidade;
    @ViewById
    public EditText uf;
    @ViewById
    public EditText pais;
    private View focus;
    @ViewById
    public ProgressBar progressBar;
    @ViewById
    public Spinner spinner;
    @ViewById
    public LinearLayout linearForm;
    private double latitude, longitude = 0;

    private int bebida;
    public PlaceAutocompleteFragment autocompleteFragment;

    @AfterViews
    public void mudaActionBar() {
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @AfterViews
    public void montandoSpiner() {

        bebida = -1;
        List<String> bebidas = new ArrayList<String>();
        bebidas.add(getString(R.string.selecione));
        bebidas.add(getString(R.string.cafe));
        bebidas.add(getString(R.string.cerveja));
        bebidas.add(getString(R.string.ambos));


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bebidas);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }


    public void pegandoConteudoDoSpinner() {
        if (getString(R.string.cafe).equals(spinner.getSelectedItem().toString())) {
            bebida = 1;
        } else if (getString(R.string.cerveja).equals(spinner.getSelectedItem().toString())) {
            bebida = 2;
        } else if (getString(R.string.ambos).equals(spinner.getSelectedItem().toString())) {
            bebida = 3;
        } else {
            bebida = -1;
        }

    }
    @UiThread
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Click(R.id.cadastrar)
    public void carregandoDadosParaServidor() {
        pegandoConteudoDoSpinner();
        if (rotinaDeErro()) {
            DataRequestCadastro dataRequestCadastro = new DataRequestCadastro();
            dataRequestCadastro.setAddress(rua.getText().toString() + ", " + numero.getText().toString() + " - " + bairro.getText().toString() + ", " + cidade.getText().toString());
            dataRequestCadastro.setBeverage(bebida);
            dataRequestCadastro.setLatitude(latitude);
            dataRequestCadastro.setLongitude(longitude);
            dataRequestCadastro.setName(nomeLocal.getText().toString());
            String params = DataSerial.convertObjectToString(dataRequestCadastro);
            if (params == null) {
                Log.e("Erro", "erro ao processar object");
            } else {
                serviceCadastro(params);
            }
        } else {
            if (focus != null) {
                focus.requestFocus();
            }
        }
    }

    @Background
    public void serviceCadastro(String params) {
        try {
            showProgress(true);
            DataResponseCadastro db = new DataResponseCadastro();
            Object object = RestV.postToObject(params, "/prod/places", db, true);

            if (object != null) {
                if (object instanceof String) {
                    alertas(object.toString());
                } else {
                    db = (DataResponseCadastro) object;
                    if (db != null) {
                        if (db.getMessage() != null && db.getPlace_id() != null) {
                            alertas(db.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showProgress(false);

    }

    @AfterViews
    public void autoComplete() {
      /*  autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint("Nome do local, rua ....");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                                                            @Override
                                                            public void onPlaceSelected(Place place) {
                                                                Log.i("CErtooo", "Place: " + place.getName());
                                                                if (place != null) {
                                                                    rua.setText(place.getWebsiteUri().toString());

                                                                }
                                                            }

                                                            @Override
                                                            public void onError(Status status) {
                                                                Log.i("errado", "An error occurred: " + status);
                                                            }
                                                        }
        );
        searchAsync();*/

    }

    @UiThread
    public void alertas(String msg) {
        Alertas.alerta(this, msg);
    }

    @AfterViews
    public void montandoAutoComplete() {
        rua.setAdapter(new GoogleAdapter(this, android.R.layout.simple_expandable_list_item_1));
        rua.setThreshold(2);
        rua.performCompletion();
        //mudando largura do autocomplete
        Point pointSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(pointSize);
        rua.setDropDownWidth(pointSize.x);

        rua.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (responseEnderecoGoogle != null && responseEnderecoGoogle.getResults() != null && responseEnderecoGoogle.getResults().size() > 0 && responseEnderecoGoogle.getResults().get(i) != null) {
                    if (responseEnderecoGoogle.getResults().get(i).getGeometry() != null && responseEnderecoGoogle.getResults().get(i).getGeometry().getLocation() != null) {
                        latitude = responseEnderecoGoogle.getResults().get(i).getGeometry().getLocation().getLat();
                        longitude = responseEnderecoGoogle.getResults().get(i).getGeometry().getLocation().getLng();
                    }
                    for (AddressComponent r : responseEnderecoGoogle.getResults().get(i).getAddressComponents()) {
                        if (r.getTypes() != null) {
                            for (int y = 0; y < r.getTypes().size(); y++) {
                                if ("route".equals(r.getTypes().get(y))) {
                                    if (!TextUtils.isEmpty(r.getLongName())) {
                                        rua.setText(r.getLongName());
                                    }
                                }
                                if ("sublocality_level_1".equals(r.getTypes().get(y))) {
                                    if (!TextUtils.isEmpty(r.getLongName())) {
                                        bairro.setText(r.getLongName());
                                    }
                                }
                                if ("locality".equals(r.getTypes().get(y))) {
                                    if (!TextUtils.isEmpty(r.getLongName())) {
                                        cidade.setText(r.getLongName());
                                    }
                                }
                                if ("administrative_area_level_1".equals(r.getTypes().get(y))) {
                                    if (!TextUtils.isEmpty(r.getShortName())) {
                                        uf.setText(r.getShortName());
                                    }
                                }
                                if ("country".equals(r.getTypes().get(y))) {
                                    if (!TextUtils.isEmpty(r.getShortName())) {
                                        pais.setText(r.getShortName());
                                    }
                                }

                            }

                        }
                    }

                }


            }
        });
    }

    @Background
    void searchAsync() {


    }

  /*  @UiThread
    void updateTela(EditText editText, String text) {
        Toast.makeText(getApplicationContext(), greeting, Toast.LENGTH_LONG).show();
    }*/

    private boolean rotinaDeErro() {

        if (TextUtils.isEmpty(nomeLocal.getText().toString())) {
            nomeLocal.setError(getString(R.string.campoObrigatorio));
            focus = nomeLocal;
            return false;
        }
        if (TextUtils.isEmpty(rua.getText().toString())) {
            rua.setError(getString(R.string.campoObrigatorio));
            focus = rua;
            return false;
        }
        if (TextUtils.isEmpty(numero.getText().toString())) {
            numero.setError(getString(R.string.campoObrigatorio));
            focus = numero;
            return false;
        }
        if (TextUtils.isEmpty(bairro.getText().toString())) {
            bairro.setError(getString(R.string.campoObrigatorio));
            focus = bairro;
            return false;
        }
        if (TextUtils.isEmpty(cidade.getText().toString())) {
            cidade.setError(getString(R.string.campoObrigatorio));
            focus = cidade;
            return false;
        }
        if (TextUtils.isEmpty(uf.getText().toString())) {
            uf.setError(getString(R.string.campoObrigatorio));
            focus = uf;
            return false;
        }
        if (TextUtils.isEmpty(pais.getText().toString())) {
            pais.setError(getString(R.string.campoObrigatorio));
            focus = pais;
            return false;
        }
        if (bebida == -1) {
            focus = spinner;
            Alertas.alerta(this, getString(R.string.selecioneBebida));
            return false;
        }
        if (latitude == 0 && longitude == 0) {
            Alertas.alerta(this, getString(R.string.enderecoSemLatitudeELong));
            return false;
        }
        return true;
    }
}
