package org.scheming.note.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import org.scheming.greendao.bean.Note;
import org.scheming.greendao.dao.NoteDao;
import org.scheming.note.R;
import org.scheming.note.frame.NoteApplication;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditActivity extends AppCompatActivity {
    @InjectView(R.id.edit_content)
    EditText add_text;
    @InjectView(R.id.app_bar)
    Toolbar toolbar;

    private InputMethodManager manager;
    private NoteDao noteDao;
    private long noteId;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        noteDao = NoteApplication.getInstance().getDaoSession().getNoteDao();
        noteId = getIntent().getLongExtra("id", -1);
        if (noteId != -1) {
            content = getIntent().getStringExtra("content");
            add_text.setText(content);
        }

        add_text.requestFocus();
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        add_text.postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.showSoftInput(add_text, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 100);

    }

    @Override
    public void onBackPressed() {
        content = add_text.getText().toString();
        if (noteId != -1) {
            noteDao.update(new Note(noteId, content, new Date(System.currentTimeMillis())));
            setResult(RESULT_OK);
        } else if (!content.equals("")) {
            noteDao.insert(new Note(null, content, new Date(System.currentTimeMillis())));
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            overridePendingTransition(R.anim.push_up_out, R.anim.push_bottom_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
