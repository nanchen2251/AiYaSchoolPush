package com.example.nanchen.aiyaschoolpush.ui.view.contact;

import android.support.v7.widget.RecyclerView;

import com.example.nanchen.aiyaschoolpush.ui.view.contact.model.People;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * Adapter holding a list of animal names of type String. Note that each item must be unique.
 */
public abstract class ContactListAdapter<VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {
  private ArrayList<People> items = new ArrayList<>();

  public ContactListAdapter() {
    setHasStableIds(true);
  }

  public void add(People object) {
    items.add(object);
    notifyDataSetChanged();
  }

  public void add(int index, People object) {
    items.add(index, object);
    notifyDataSetChanged();
  }

  public void addAll(Collection<? extends People> collection) {
    if (collection != null) {
      items.addAll(collection);
      notifyDataSetChanged();
    }
  }

  public void addAll(People... items) {
    addAll(Arrays.asList(items));
  }

  public void clear() {
    items.clear();
    notifyDataSetChanged();
  }

  public void remove(String object) {
    items.remove(object);
    notifyDataSetChanged();
  }

  public People getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).hashCode();
  }

  @Override
  public int getItemCount() {
    return items.size();
  }
}
