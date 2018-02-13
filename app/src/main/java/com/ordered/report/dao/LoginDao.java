package com.ordered.report.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.ordered.report.db.DatabaseHelper;
import com.ordered.report.models.UsersEntity;

import java.sql.SQLException;

/**
 * Created by Admin on 1/1/2018.
 */

public class LoginDao {
    DatabaseHelper databaseHelper = null;
    Dao<UsersEntity, String> userDao = null;

    public LoginDao(Context context) throws Exception {
        this.databaseHelper = DatabaseHelper.getInstance(context);
        initDaos();
    }

    private void initDaos() throws Exception {
        userDao = databaseHelper.getDao(UsersEntity.class);
    }
    public UsersEntity getUserEntity(String userName) throws SQLException {
        return userDao.queryBuilder().where().eq(UsersEntity.USER_NAME, userName).queryForFirst();
    }

}
