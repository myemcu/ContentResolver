package com.myemcu.app_20contentresolver;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button          chooseDate, add, show;
    private EditText        date, subject, body;
    private ListView        result;
    private LinearLayout    title;
    private ContentResolver contentResolver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentResolver = getContentResolver();

        findViews();                            // 获取所有控件
        title.setVisibility(View.INVISIBLE);    // 隐藏标题栏

        // 日期选择键
        chooseDate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();// 获取当前日期
                new DatePickerDialog(MainActivity.this,// 日期选择器对话框
                                     new DatePickerDialog.OnDateSetListener() {// 日期改变监听器
                                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                            date.setText(year + "-" + (month + 1) + "-" + day);
                                        }
                                     },
                                     c.get(Calendar.YEAR),
                                     c.get(Calendar.MONTH),
                                     c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // 添加键
        add.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                ContentValues values = new ContentValues();

                values.put(APP_18_Mementos.Memento.SUBJECT, subject.getText().toString());
                values.put(APP_18_Mementos.Memento.BODY, body.getText().toString());
                values.put(APP_18_Mementos.Memento.DATE, date.getText().toString());

                contentResolver.insert(APP_18_Mementos.Memento.MEMENTOS_CONTENT_URI, values);

                Toast.makeText(MainActivity.this, "添加生词成功！", Toast.LENGTH_LONG).show();
            }
        });

        // 显示所有记录键
        show.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Cursor cursor = contentResolver.query(APP_18_Mementos.Memento.MEMENTOS_CONTENT_URI, null, null, null, null);
                System.out.println(cursor);
                SimpleCursorAdapter resultAdapter =
                        new SimpleCursorAdapter(
                                                MainActivity.this,
                                                R.layout.result,
                                                cursor,
                                                new String[] { APP_18_Mementos.Memento._ID, APP_18_Mementos.Memento.SUBJECT, APP_18_Mementos.Memento.BODY, APP_18_Mementos.Memento.DATE },
                                                new int[] { R.id.memento_num, R.id.memento_subject, R.id.memento_body, R.id.memento_date }
                                               );

                result.setAdapter(resultAdapter);
            }
        });
    }

    private void findViews() {

        subject = (EditText) findViewById(R.id.subject);
        body = (EditText) findViewById(R.id.body);
        date = (EditText) findViewById(R.id.date);
        chooseDate = (Button) findViewById(R.id.chooseDate);
        add = (Button) findViewById(R.id.add);
        show = (Button) findViewById(R.id.show);

        title = (LinearLayout) findViewById(R.id.title);
        result = (ListView) findViewById(R.id.result);
    }
}
