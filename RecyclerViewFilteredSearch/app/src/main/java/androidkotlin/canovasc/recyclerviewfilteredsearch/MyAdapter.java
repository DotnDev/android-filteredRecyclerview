package androidkotlin.canovasc.recyclerviewfilteredsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Ad> fullList;
    private ArrayList<Ad> listAfterSpinner;
    private ArrayList<Ad> listAfterSearch;
    private ArrayList<Ad> list; //List after both combined?
    private boolean spinnerCheck;
    private boolean searchCheck;

    // We store the list in fullList to keep record of it - fullList will be used to reset the list when needed
    MyAdapter(Context context, ArrayList<Ad> list){
        this.list = list;
        this.context = context;
        fullList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.category.setText(list.get(position).getCategory());

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ad_title_textview);
            category = itemView.findViewById(R.id.ad_category_textview);


        }
    }

@Override
public Filter getFilter(){
        return searchFilter;
}

    //This filter works with the search function and filters the list depending on what's typed in the textbox
    private Filter searchFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults results = new FilterResults();
            listAfterSearch = new ArrayList<>();
            ArrayList<Ad> listSearchSpinner = new ArrayList<>();

            //Update the list depending on whether it's already been filtered with Spinners or not
            if (!spinnerCheck) {
                if (charSequence == null || charSequence.length() == 0) {
                    listAfterSearch.addAll(fullList);
                    searchCheck = false;

                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    searchCheck = true;

                    for (Ad item : fullList) {
                        if (item.getTitle().toLowerCase().contains(filterPattern)) {
                            listAfterSearch.add(item);
                        }
                    }
                }
                results.values = listAfterSearch;

            } else {
                if (charSequence == null || charSequence.length() == 0) {
                    searchCheck = false;
                    listSearchSpinner.addAll(listAfterSpinner);
                } else{
                    for (Ad item : listAfterSpinner){
                        searchCheck = true;
                        String filterPattern = charSequence.toString().toLowerCase().trim();

                        if (item.getTitle().toLowerCase().contains(filterPattern)) {
                            listSearchSpinner.add(item);
                        }
                    }
                }


                results.values = listSearchSpinner;

            }



            return results;

        }

        //This publishes the results of the Search Filtering - not spinners!
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list.clear();
            list.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };



    void  getSpinnerFilter (String mSpinner1) {
        listAfterSpinner = new ArrayList<>();
        ArrayList<Ad> listSpinnerSearch = new ArrayList<>();


        if(!searchCheck) {        //If no search was performed before...

            if (mSpinner1 == null) { //if no Spinner selected
                listAfterSpinner.addAll(fullList);
                spinnerCheck = false;

            } else { //if Spinner1 selected
                spinnerCheck = true;
                for (Ad item : fullList) {
                    if (item.getCategory().equals(mSpinner1)) {
                        listAfterSpinner.add(item);

                    }
                }
            }

            list.clear();
            list.addAll(listAfterSpinner);

        } else {                          //If Search was performed before...

            if (mSpinner1 != null) { //If Search + Spinner1 selected
                spinnerCheck = true;
                for (Ad item : listAfterSearch) {
                    if (item.getCategory().equals(mSpinner1)) {
                        listSpinnerSearch.add(item);

                    }
                }

                for (Ad item : fullList) {  //Storing the results in case Search is deleted? To test
                    if (item.getCategory().equals(mSpinner1)) {
                        listAfterSpinner.add(item);

                    }
                }

            } else {
                spinnerCheck = false;
                listSpinnerSearch.addAll(listAfterSearch);

            }

            list.clear();
            list.addAll(listSpinnerSearch);

        }


        notifyDataSetChanged();


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
