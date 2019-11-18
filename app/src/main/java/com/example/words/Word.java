package com.example.words;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity//实体类
public class Word {
    @PrimaryKey(autoGenerate = true)//主键，自动生成
    private int id;
    @ColumnInfo(name = "english_word")//列名称
    private String word;
    @ColumnInfo(name = "chinese_meaning")
    private String chineseMeaning;
    //    @ColumnInfo(name = "foo_data")
//    private boolean foo;
//    @ColumnInfo(name = "bar_data")
//    private boolean bar;
    @ColumnInfo(name = "chinese_invisible")
    private boolean chineseInvisible;

    public Word(String word, String chineseMeaning) {//id自动生成，不用写
        this.word = word;
        this.chineseMeaning = chineseMeaning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getChineseMeaning() {
        return chineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {
        this.chineseMeaning = chineseMeaning;
    }

//    public boolean isFoo() {
//        return foo;
//    }
//
//    public void setFoo(boolean foo) {
//        this.foo = foo;
//    }
//
//    public boolean isBar() {
//        return bar;
//    }
//
//    public void setBar(boolean bar) {
//        this.bar = bar;
//    }

    public boolean isChineseInvisible() {
        return chineseInvisible;
    }

    public void setChineseInvisible(boolean chineseInvisible) {
        this.chineseInvisible = chineseInvisible;
    }
}
