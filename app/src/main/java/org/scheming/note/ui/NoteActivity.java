package org.scheming.note.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.scheming.greendao.bean.Note;
import org.scheming.greendao.dao.NoteDao;
import org.scheming.note.R;
import org.scheming.note.adapter.RecyclerAdapter;
import org.scheming.note.entity.User;
import org.scheming.note.frame.NoteApplication;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;


public class NoteActivity extends AppCompatActivity implements
        View.OnClickListener, RecyclerAdapter.ItemClickListener, RecyclerAdapter.ItemLongClickListener {
    public static final int ADD_NOTE = 1;
    public static final String KEY = "8bda19ba37d9522f34817dc4c33eb65d";

    @InjectView(R.id.frame_note_list)
    RecyclerView recyclerView;
    @InjectView(R.id.frame_btn_add)
    FloatingActionButton addBtn;
    @InjectView(R.id.app_bar)
    Toolbar toolbar;
    @InjectView(R.id.frame_drawer)
    DrawerLayout drawerLayout;

    private RecyclerAdapter mAdapter;
    private NoteDao noteDao;
    private List<Note> notes;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.inject(this);

        init();
    }


    private void init() {
        setSupportActionBar(toolbar);

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        noteDao = NoteApplication.getInstance().getDaoSession().getNoteDao();

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        addBtn.setOnClickListener(this);

        notes = noteDao.queryBuilder().list();

        mAdapter = new RecyclerAdapter(notes);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(mAdapter);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Bmob.initialize(this, KEY);
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(this, EditActivity.class), ADD_NOTE);
//        User user = new User("123", "石皓洋");
//        user.save(this, new SaveListener() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(NoteActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            notes = noteDao.queryBuilder().list();
            mAdapter.setDatas(notes);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("content", notes.get(position).getContent());
        intent.putExtra("id", notes.get(position).getId());
        startActivityForResult(intent, ADD_NOTE);
    }

    @Override
    public void OnItemLongClickListener(View view, int position) {
//        view.setBackgroundColor(getResources().getColor(R.color.holo_blue_light));
        Log.i(this.getClass().getSimpleName(), view.toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        正确退出应用
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }
}
