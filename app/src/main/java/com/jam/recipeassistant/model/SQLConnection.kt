package com.jam.recipeassistant.model

import android.os.StrictMode
import java.lang.Error
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet


class SQLConnection() {
    var conn : Connection? = null;
    init {
        //Connection conn = null
        val ip = "34.86.143.65"
        val login = "recipeassistant-db"
        val db = "recipeassistantdb"
        val pass = "RAdb2021$$"

        val tp = StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            var conn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+":1433/"+db, login, pass);
            this.conn = conn
        } catch (e: Exception) {

        }
    }

    fun executeQuery(query:String):ResultSet? {
        if (this.conn != null) {
            var conn = this.conn!!
            try {
                var statement = conn.createStatement()
                var result = statement.executeQuery(query)
                return result
            } catch (e : Exception) {
                return null
            }
        }
        return null
    }

}