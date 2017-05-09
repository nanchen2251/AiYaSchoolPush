package com.example.nanchen.aiyaschoolpush.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.ui.view.contact.ContactListWithHeadersAdapter;
import com.example.nanchen.aiyaschoolpush.ui.view.contact.ContactListWithHeadersAdapter.OnItemClickListener;
import com.example.nanchen.aiyaschoolpush.ui.view.contact.DividerDecoration;
import com.example.nanchen.aiyaschoolpush.ui.view.contact.constants.DataConstants;
import com.example.nanchen.aiyaschoolpush.ui.view.contact.model.People;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.MaterialSearchBar.OnSearchActionListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.fragment
 * @date 2016/10/08  08:57
 */

public class ContactFragment extends FragmentBase implements OnQuickSideBarTouchListener,OnSearchActionListener{

    private RecyclerView mRecyclerView;
    private QuickSideBarView mQuickSideBar;
    private QuickSideBarTipsView mQuickSideBarTips;
    private HashMap<String,Integer> letters = new HashMap<>();
    private MaterialSearchBar mSearchBar;
    private List<String> lastSerches;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list,container,false);
        bindView(view);
        return view;
    }

    private void bindView(View view) {

        // 搜索框
        mSearchBar = (MaterialSearchBar) view.findViewById(R.id.contact_searchBar);
        mSearchBar.setHint("Custom hint");
        mSearchBar.setOnSearchActionListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.release_recycler);
        mQuickSideBar = (QuickSideBarView) view.findViewById(R.id.quickSideBarView);
        mQuickSideBarTips = (QuickSideBarTipsView) view.findViewById(R.id.quickSideBarTipsView);



        //设置监听
        mQuickSideBar.setOnQuickSideBarTouchListener(this);


        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        ContactListWithHeadersAdapter adapter = new ContactListWithHeadersAdapter();

        //GSON解释出来
        Type listType = new TypeToken<LinkedList<People>>(){}.getType();
        Gson gson = new Gson();
        final LinkedList<People> names = gson.fromJson(DataConstants.peopleDataList, listType);

        ArrayList<String> customLetters = new ArrayList<>();

        int position = 0;
        for(People people : names){
            String letter = people.getFirstLetter();
            //如果没有这个key则加入并把位置也加入
            if(!letters.containsKey(letter)){
                letters.put(letter,position);
                customLetters.add(letter);
            }
            position++;
        }

        //不自定义则默认26个字母
        mQuickSideBar.setLetters(customLetters);
        adapter.addAll(names);
        mRecyclerView.setAdapter(adapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        mRecyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        mRecyclerView.addItemDecoration(new DividerDecoration(getActivity()));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Toast.makeText(getActivity(),"你点击了"+names.get(position).getPeopleName(),Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public void onLetterChanged(String letter, int position, float y) {
        mQuickSideBarTips.setText(letter, position, y);
        //有此key则获取位置并滚动到该位置
        if(letters.containsKey(letter)) {
            mRecyclerView.scrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
        mQuickSideBarTips.setVisibility(touching? View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public void onSearchStateChanged(boolean b) {

    }

    @Override
    public void onSearchConfirmed(CharSequence charSequence) {

    }

    @Override
    public void onButtonClicked(int i) {

    }

}
