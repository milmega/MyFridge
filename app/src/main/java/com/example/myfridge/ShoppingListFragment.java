package com.example.myfridge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class ShoppingListFragment extends Fragment {
    EditText note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_shopping_list, container, false);
        note = view.findViewById(R.id.noteID);
        showSavedText();

        note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NOTE", 0);
                SharedPreferences.Editor editor_sp = sharedPreferences.edit();
                editor_sp.putString("shoppingList", note.getText().toString());
                editor_sp.commit();
            }
        });

        return view;
    }
    void showSavedText(){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("NOTE", 0);
        String savedText = sharedPreferences.getString("shoppingList", "");
        note.setText(savedText);
    }
}