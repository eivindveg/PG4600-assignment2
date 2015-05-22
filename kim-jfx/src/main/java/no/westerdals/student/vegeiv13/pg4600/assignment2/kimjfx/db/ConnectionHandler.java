package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.db;

import com.j256.ormlite.dao.Dao;

public abstract class ConnectionHandler {

    public ConnectionHandler() {

    }

    public abstract <T, ID> Dao<T, ID> getDao(Class<T> clazz);
}
