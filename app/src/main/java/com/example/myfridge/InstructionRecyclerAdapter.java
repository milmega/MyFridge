package com.example.myfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InstructionRecyclerAdapter extends RecyclerView.Adapter<InstructionRecyclerAdapter.StepHolder> {
    Context context;
    ArrayList<String> steps;

    public InstructionRecyclerAdapter(Context context, ArrayList<String>steps){
        this.context = context;
        this.steps = steps;
    }

    public class StepHolder extends RecyclerView.ViewHolder{
        private TextView stepNumber;
        private TextView stepDescription;

        public StepHolder(final View view) {
            super(view);
            stepNumber = view.findViewById(R.id.stepNumber);
            stepDescription = view.findViewById(R.id.stepString);
        }
    }

    @NonNull
    @Override
    public InstructionRecyclerAdapter.StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_steps, parent, false);
        return new StepHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionRecyclerAdapter.StepHolder holder, int position) {
        int number = position+1;
        holder.stepNumber.setText("Step " + number);
        holder.stepDescription.setText(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}
