package com.lifeonwalden.forestbatis.example.test;

import com.lifeonwalden.forestbatis.bean.Config;

public interface DBConfig {
    Config config = new Config().setCaseSensitive(false).setWithSchema(false);
}
