package com.example.words;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//Database access object
public interface WordDao {
    @Insert
    void insertWords(Word... words);//返回插入的id，或修饰为void

    @Update
    void updateWords(Word... words);//返回修改记录的数量，或修饰为void

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
//    List<Word> getAllWords();
    LiveData<List<Word>> getAllWordsLive();

}
