package examples.architectures.MVVM.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import examples.architectures.MVVM.viewmodel.CountriesViewModel;
import examples.architectures.R;

public class MVVMActivity extends AppCompatActivity {
    private ListView list;
    private ProgressBar progressBar;
    private Button button;

    private ArrayAdapter<String> adapter;
    private List<String> listValues = new ArrayList<>();
    private CountriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("MVVM");

        viewModel = ViewModelProviders.of(this).get(CountriesViewModel.class);


        list = findViewById(R.id.lv_countries);
        progressBar = findViewById(R.id.progress);
        button = findViewById(R.id.btn_retry);

        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listtext, listValues);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVVMActivity.this, "You clicked " + listValues.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getCountries().observe(this, countries -> {
            if (countries != null) {
                listValues.clear();
                listValues.addAll(countries);
                list.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
            else {
                list.setVisibility(View.GONE);
            }
        });

        viewModel.getCountryError().observe(this, error -> {
            progressBar.setVisibility(View.GONE);
            if(error) {
                Toast.makeText(MVVMActivity.this, "Unable to get country names. Please retry later.", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
            }
            else
                button.setVisibility(View.GONE);

        });
    }

    public static Intent getIntent(Context context){
        return new Intent(context, MVVMActivity.class);
    }


    public void onRetry(View view) {
        viewModel.onRefresh();
        list.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}
