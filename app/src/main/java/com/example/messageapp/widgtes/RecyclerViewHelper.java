package com.example.messageapp.widgtes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RecyclerViewHelper<T> {
    private final RecyclerView recyclerView;
    private final Context context;
    private List<T> dataList;
    private Function<T, Integer> itemLayoutResolver;
    private BindItemCallback<T> onBindItem;

    public RecyclerViewHelper(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.dataList = new ArrayList<>();// Initialize dataList here
    }


    public RecyclerViewHelper<T> setData(List<T> dataList) {
        this.dataList = new ArrayList<>(dataList); // Clone to be safe
        return this;
    }


    public RecyclerViewHelper<T> setItemLayout(Function<T, Integer> layoutResolver) {
        this.itemLayoutResolver = layoutResolver;
        return this;
    }
 
    public interface BindItemCallback<T> {
        void onBindItem(View view, T item, int index);
    }

    public RecyclerViewHelper<T> setOnBindItem(BindItemCallback<T> onBindItem) {
        this.onBindItem = onBindItem;
        return this;
    }

    public void updateData(List<T> newData) {
        if (newData == null) {
            newData = new ArrayList<>();
        }

        // Clear the old data and add new data
        dataList.clear();
        dataList.addAll(newData);

        RecyclerView.Adapter<?> adapter = this.recyclerView.getAdapter();
        if (adapter == null) {
            build();  // Only build adapter once
        } else {
            adapter.notifyDataSetChanged();  // Notify the adapter of data changes
        }
    }


    public void build() {
        if (dataList == null || itemLayoutResolver == null || onBindItem == null) {
            throw new IllegalStateException("Error: Missing data, layout resolver, or binding function.");
        }

        // Only create the adapter once
        if (this.recyclerView.getAdapter() == null) {
            RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                @Override
                public int getItemViewType(int position) {
                    return itemLayoutResolver.apply(dataList.get(position));  // Get layout based on item
                }

                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(context).inflate(viewType, parent, false);
                    return new RecyclerView.ViewHolder(view) {
                    };
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    onBindItem.onBindItem(holder.itemView, dataList.get(position), position);
                }

                @Override
                public int getItemCount() {
                    return dataList.size();
                }
            };

            this.recyclerView.setAdapter(adapter);  // Set adapter only once
        }
    }

}


// ****************** HOW TO USE ******************

/*
    RecyclerView recyclerView = view.findViewById(R.id.example_recycler_view);
    private RecyclerViewHelper<ExampleModel> recycleHelper = new RecyclerViewHelper<>(getActivity(), recyclerView);

    recycleHelper.setData(new ArrayList<>());
            recycleHelper.setItemLayout(item -> R.layout.application_list_item);
            recycleHelper.setOnBindItem((viewItem, item) -> {
                CustomListTile itemTile = viewItem.findViewById(R.id.application_tile);
                itemTile.setTitle(item.getApplicationNo());
                Date date = DateConverter.convertStringToDate(item.getCreateDate(), "yyyy-MM-dd HH:mm:ss");
                String formattedDate = DateConverter.dateToString(date, "dd MMMM yyyy");
                itemTile.setSubtitle(formattedDate);
                String statusName = item.getApplicationStatusName();
                itemTile.setStatus("Status :", statusName);
                itemTile.setStatusColor(ContextCompat.getColor(requireContext(), getStatusColor(statusName)));

                itemTile.setOnClickListener(v -> {
                    ResidencePassHistoryVM.getInstance().setSelectedApplication(item);
                    Navigator.push(getActivity(), ResidencePassDetailActivity.class);
                });
            });

    recycleHelper.build();

// Update data if there a new
    recycleHelper.updateData(result);

*/