package com.lifeonwalden;

import com.lifeonwalden.forestbatis.meta.ColumnInfo;
import com.lifeonwalden.forestbatis.result.RecordHandler;

import java.sql.ResultSet;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        test(new App.TestHanlder());

        System.out.println("Hello World!");
    }

    public static void test(RecordHandler recordHandler) {

    }

    static class TestHanlder implements RecordHandler<String> {
        @Override
        public String convert(ResultSet resultSet, Set<String> tableColumnSet, Set<ColumnInfo> notTableColumnSet) {
            return null;
        }
    }
}
