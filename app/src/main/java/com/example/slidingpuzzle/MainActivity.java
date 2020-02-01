package com.example.slidingpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] pics = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
            R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9,
            R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.img13, R.drawable.img14,
            R.drawable.img15, R.drawable.blank};

    private final int BLANK = R.drawable.blank;
    private final int NUMBER_OF_COLUMN = 4;
    private int moveSoFar = 0;
    int[] randomImgList = shuffleImages(pics);
    private MyCustomGridViewAdapter adapter;
    String notiTxt = "";


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        final TextView moveNum = findViewById(R.id.moveNumberTxtView);
        final TextView noti = findViewById(R.id.notiTxtView);
        outState.putIntArray("ImgList", randomImgList);
        outState.putInt("numMove", moveSoFar);
        outState.putString("noti", notiTxt);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        final TextView moveNum = findViewById(R.id.moveNumberTxtView);
        final TextView noti = findViewById(R.id.notiTxtView);
        moveSoFar = savedInstanceState.getInt("numMove");
        notiTxt = savedInstanceState.getString("noti");
        if (moveSoFar > 0) {
            moveNum.setText(String.valueOf(moveSoFar));
        }
        noti.setText(notiTxt);
        randomImgList = savedInstanceState.getIntArray("ImgList");
        adapter = new MyCustomGridViewAdapter(MainActivity.this, randomImgList);
        GridView gridView = findViewById(R.id.puzzleGridView);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridView = findViewById(R.id.puzzleGridView);
        final TextView moveNum = findViewById(R.id.moveNumberTxtView);
        final TextView noti = findViewById(R.id.notiTxtView);
        Button newPuzzle = findViewById(R.id.newPuzzleBtn);
        final Button solvePuzzle = findViewById(R.id.solvePuzzleBtn);

        adapter = new MyCustomGridViewAdapter(this, randomImgList);

        gridView.setNumColumns(NUMBER_OF_COLUMN);
        gridView.setAdapter(adapter);
        if (!checkWin(randomImgList)) {


        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!checkWin(randomImgList)) {
                    if ((position + 1 < randomImgList.length) && randomImgList[position + 1] == BLANK) {
                        swap(randomImgList, position, position + 1);
                        adapter.notifyDataSetChanged();
                        moveSoFar++;
                        moveNum.setText(String.valueOf(moveSoFar));
                        notiTxt = "";
                    } else if ((position - 1 >= 0) && randomImgList[position - 1] == BLANK) {
                        swap(randomImgList, position, position - 1);
                        adapter.notifyDataSetChanged();
                        moveSoFar++;
                        moveNum.setText(String.valueOf(moveSoFar));
                        notiTxt = "";
                    } else if ((position + NUMBER_OF_COLUMN < randomImgList.length) && randomImgList[position + NUMBER_OF_COLUMN] == BLANK) {
                        swap(randomImgList, position, position + NUMBER_OF_COLUMN);
                        adapter.notifyDataSetChanged();
                        moveSoFar++;
                        moveNum.setText(String.valueOf(moveSoFar));
                        notiTxt = "";
                    } else if ((position - NUMBER_OF_COLUMN >= 0) && randomImgList[position - NUMBER_OF_COLUMN] == BLANK) {
                        swap(randomImgList, position, position - NUMBER_OF_COLUMN);
                        adapter.notifyDataSetChanged();
                        moveSoFar++;
                        moveNum.setText(String.valueOf(moveSoFar));
                        notiTxt = "";
                    } else {
                        notiTxt = "Illegal move!";
                    }
                } else {
                    notiTxt = "Please select a new puzzle to restart!";
                }

                if (checkWin(randomImgList)) {
                    if (moveSoFar > 0) {
                        notiTxt = "You solved the puzzle in " + moveSoFar + " moves!";
                        moveSoFar = 0;
                        solvePuzzle.setEnabled(false);
                    }

                }
                noti.setText(notiTxt);
            }
        });

        newPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solvePuzzle.setEnabled(true);
                randomImgList = shuffleImages(randomImgList);
                adapter = new MyCustomGridViewAdapter(MainActivity.this, randomImgList);
                gridView.setAdapter(adapter);
                moveSoFar = 0;
                moveNum.setText(null);
                noti.setText(null);
            }
        });
        solvePuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomImgList = Arrays.copyOf(pics, pics.length);
                adapter = new MyCustomGridViewAdapter(MainActivity.this, randomImgList);
                gridView.setAdapter(adapter);
                moveSoFar = 0;
                moveNum.setText(null);
                noti.setText(null);
                solvePuzzle.setEnabled(false);

            }
        });


    }

    public int[] shuffleImages(int[] originalImgList) {
        int[] shuffleImg = Arrays.copyOf(originalImgList, originalImgList.length);
        int index = 0;
        int temp;
        Random random = new Random();
        for (int i = shuffleImg.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = shuffleImg[index];
            shuffleImg[index] = shuffleImg[i];
            shuffleImg[i] = temp;
        }
        return shuffleImg;
    }

    public void swap(int[] imgList, int position1, int position2) {
        int temp;
        temp = imgList[position1];
        imgList[position1] = imgList[position2];
        imgList[position2] = temp;
    }

    public boolean checkWin(int[] imgList) {
        boolean isSolved = false;
        for (int i = 0; i < imgList.length; i++) {
            if (imgList[i] == pics[i]) {
                isSolved = true;
            } else {
                isSolved = false;
                break;
            }
        }
        return isSolved;

    }
}
