package examples.architectures;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import examples.architectures.MVC.MVCActivity;
import examples.architectures.MVP.MVPActivity;
import examples.architectures.MVVM.view.MVVMActivity;

public class ArchitecturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architectures);
    }

    public void onMVC(View view) {
        startActivity(MVCActivity.getIntent(this));
    }

    public void onMVP(View view) {
        startActivity(MVPActivity.getIntent(this));
    }

    public void onMVVM(View view) {
        startActivity(MVVMActivity.getIntent(this));
    }
}
