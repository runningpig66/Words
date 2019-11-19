package com.example.words;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWordsLive;

    WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getINSTANCE(context);
        wordDao = wordDatabase.getWordDao();
        allWordsLive = wordDao.getAllWordsLive();//LiveData数据不需要单独放子线程，系统会自动操作
    }

    LiveData<List<Word>> findWordsWithPattern(String pattern) {
        return wordDao.findWordsWithPattern("%" + pattern + "%");//模糊匹配
    }

    LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    void insertWords(Word... words) {
        new InsertAsyncTask(wordDao).execute(words);
    }

    void updateWords(Word... words) {
        new UpdateAsyncTask(wordDao).execute(words);
    }

    void deleteWords(Word... words) {
        new DeleteAsyncTask(wordDao).execute(words);
    }

    void deleteAllWords() {
        new DeleteAllAsyncTask(wordDao).execute();
    }

    public static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        UpdateAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        DeleteAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        DeleteAllAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
