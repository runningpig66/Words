package com.example.words.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.words.MyAdapterNew;
import com.example.words.R;
import com.example.words.Word;
import com.example.words.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordsFragment extends Fragment {
    private static final String VIEW_TYPE_SHP = "view_type_shp";
    private static final String IS_USING_CARD_VIEW = "is_using_card_view";

    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private MyAdapterNew myAdapterNew1, myAdapterNew2;
    private FloatingActionButton floatingActionButton;
    private LiveData<List<Word>> filteredWords;

    public WordsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);//显示Menu
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(700);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                filteredWords.removeObservers(requireActivity());//...
                filteredWords = wordViewModel.findWordsWithPattern(pattern);
                filteredWords.observe(requireActivity(), new Observer<List<Word>>() {
                    @Override
                    public void onChanged(List<Word> words) {
                        Log.d("myLog", "onChanged: " + words);
                        int temp = myAdapterNew1.getItemCount();
                        myAdapterNew1.setAllWords(words);
                        myAdapterNew2.setAllWords(words);
                        if (temp != words.size()) {
                            myAdapterNew1.notifyDataSetChanged();
                            myAdapterNew2.notifyDataSetChanged();
                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearData:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("清空数据");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wordViewModel.deleteAllWords();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create();
                builder.show();
                break;
            case R.id.switchViewType:
                SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shp.edit();
                boolean viewType = shp.getBoolean(IS_USING_CARD_VIEW, false);
                if (viewType) {
                    recyclerView.setAdapter(myAdapterNew1);
                    editor.putBoolean(IS_USING_CARD_VIEW, false);
                } else {
                    recyclerView.setAdapter(myAdapterNew2);
                    editor.putBoolean(IS_USING_CARD_VIEW, true);
                }
                editor.apply();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordViewModel = ViewModelProviders.of(requireActivity()).get(WordViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapterNew1 = new MyAdapterNew(false, wordViewModel);
        myAdapterNew2 = new MyAdapterNew(true, wordViewModel);

        SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
        boolean viewType = shp.getBoolean(IS_USING_CARD_VIEW, false);
        if (viewType) {
            recyclerView.setAdapter(myAdapterNew2);
        } else {
            recyclerView.setAdapter(myAdapterNew1);
        }

        recyclerView.setAdapter(myAdapterNew1);
        filteredWords = wordViewModel.getAllWordsLive();
        wordViewModel.getAllWordsLive().observe(requireActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapterNew1.getItemCount();
                myAdapterNew1.setAllWords(words);
                myAdapterNew2.setAllWords(words);
                if (temp != words.size()) {
                    myAdapterNew1.notifyDataSetChanged();
                    myAdapterNew2.notifyDataSetChanged();
                }
            }
        });
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });
    }

//    @Override
//    public void onResume() {
//        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//        super.onResume();
//    }
}
