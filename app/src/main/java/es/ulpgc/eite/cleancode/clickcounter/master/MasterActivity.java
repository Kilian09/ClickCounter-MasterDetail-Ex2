package es.ulpgc.eite.cleancode.clickcounter.master;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.ulpgc.eite.cleancode.clickcounter.R;
import es.ulpgc.eite.cleancode.clickcounter.data.CounterData;

public class MasterActivity
        extends AppCompatActivity implements MasterContract.View {

    public static String TAG = MasterActivity.class.getSimpleName();

    private MasterContract.Presenter presenter;

    private ListView listView;
    private MasterAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        getSupportActionBar().setTitle(R.string.app_name);

        // do the setup
        MasterScreen.configure(this);

        if (savedInstanceState == null) {
            presenter.onStart();

        } else {
            presenter.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // load the data
        presenter.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        presenter.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }


    public void onButtonPressed(View view) {
        presenter.onButtonPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataUpdated(MasterViewModel viewModel) {
        //Log.e(TAG, "onDataUpdated()");

        // deal with the datasource
        ((ListView) findViewById(R.id.list)).setAdapter(new MasterAdapter(
                        this, viewModel.datasource, new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        CounterData data = (CounterData) view.getTag();
                        presenter.selectProductListData(data);
                    }
                })
        );
    }

    @Override
    public void injectPresenter(MasterContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
