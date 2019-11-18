package com.example.words;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 5, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static volatile WordDatabase INSTANCE;

    static WordDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (WordDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, WordDatabase.class, "word_database")
//                            .allowMainThreadQueries()
//                            .fallbackToDestructiveMigration()//破坏式迁移
                            .addMigrations(MIGRATION_4_5)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();

    //添加列
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1 ");
        }
    };

    //删除列
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //创建新表
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL," +
                    "english_word TEXT," +
                    "chinese_meaning TEXT)");
            //复制旧表数据
            database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meaning) " +
                    "SELECT id,english_word,chinese_meaning FROM word");
            //删除旧表
            database.execSQL("DROP TABLE word");
            //重命名新表
            database.execSQL("ALTER TABLE word_temp RENAME TO word");
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN chinese_invisible INTEGER NOT NULL DEFAULT 0");
        }
    };

}
