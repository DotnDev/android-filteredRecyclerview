package androidkotlin.canovasc.recyclerviewfilteredsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener, View.OnTouchListener {

    private ArrayList<Ad> mAdsList;
    private MyAdapter mAdapter;
    Spinner category_spinner;
    String category_string;
    SearchView searchView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        category_spinner = findViewById(R.id.cat_spinner);
        searchView = findViewById(R.id.searchview);
        recyclerView = findViewById(R.id.recyclerView);

        category_spinner.setOnItemSelectedListener(this);

        searchView.clearFocus();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdsList = new ArrayList<>();
        mAdsList = generateList();
        mAdapter = new MyAdapter(this,mAdsList);
        recyclerView.setAdapter(mAdapter);


        findViewById(R.id.MainLayout).setOnTouchListener(this);
        


    }

    //Spinner method
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {



                if(position==0){
                    category_string = null;
                }else{
                    category_string = category_spinner.getSelectedItem().toString();
                }

                mAdapter.getSpinnerFilter(category_string);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    //SearchView method
    @Override
    public boolean onQueryTextChange(String searchTerms) {
        mAdapter.getFilter().filter(searchTerms);
        return false;
    }


    //Deactivate keyboard when tapping out of text box
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(searchView.getWindowToken(),0);

        view.performClick();
        return false;
    }


    @Override //To deactivate the searchview focus on resume
    public void onResume(){
        super.onResume();
        searchView.clearFocus();
    }

    public ArrayList<Ad> generateList(){ //For testing only...

        ArrayList <Ad> newList = new ArrayList<>();

        Ad ad1 = new Ad(getResources().getString(R.string.title1));
        ad1.setCategory(getResources().getString(R.string.category1));

        newList.add(ad1);

        Ad ad2 = new Ad(getResources().getString(R.string.title2));
        ad2.setCategory(getResources().getString(R.string.category1));

        newList.add(ad2);

        Ad ad3 = new Ad(getResources().getString(R.string.title3));
        ad3.setCategory(getResources().getString(R.string.category2));

        newList.add(ad3);

        Ad ad4 = new Ad(getResources().getString(R.string.title4));
        ad4.setCategory(getResources().getString(R.string.category3));

        newList.add(ad4);

        Ad ad5 = new Ad(getResources().getString(R.string.title5));
        ad5.setCategory(getResources().getString(R.string.category3));

        newList.add(ad5);

        Ad ad6 = new Ad(getResources().getString(R.string.title6));
        ad6.setCategory(getResources().getString(R.string.category2));


        newList.add(ad6);


        return newList;
    }
}
