package com.lifeonwalden.forestbatis.example;

import com.lifeonwalden.forestbatis.bean.Config;

public interface DBConfig {
    Config config = new Config().setCaseSensitive(true).setWithSchema(false);
}
