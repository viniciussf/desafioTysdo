package com.android.androidannotactions;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.androidannotactions.adapter.GoogleAdapter;
import com.android.androidannotactions.model.AddressComponent;
import com.android.androidannotactions.model.ResponseGoogle;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
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

    @ViewById
    public Spinner spinner;
    @ViewById
    public Button cadastrar;

    public PlaceAutocompleteFragment autocompleteFragment;

    @AfterViews
    public void mudaActionBar() {
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @AfterViews
    public void montandoSpiner() {


        List<String> bebidas = new ArrayList<String>();
        bebidas.add(getString(R.string.cafe));
        bebidas.add(getString(R.string.ceveja));
        bebidas.add(getString(R.string.ambos));


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bebidas);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
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

                if (responseEnderecoGoogle != null && responseEnderecoGoogle.getResults() != null && responseEnderecoGoogle.getResults().get(0) != null) {

                    for (AddressComponent r : responseEnderecoGoogle.getResults().get(0).getAddressComponents()) {
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
        return true;
    }
}
