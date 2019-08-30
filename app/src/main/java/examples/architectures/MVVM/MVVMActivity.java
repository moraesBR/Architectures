package examples.architectures.MVVM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import examples.architectures.MVC.MVCActivity;
import examples.architectures.R;

public class MVVMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("MVVM");
    }

    public static Intent getIntent(Context context){
        return new Intent(context, MVVMActivity.class);
    }
}
