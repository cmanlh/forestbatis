package com.lifeonwalden.forestbatis.bean;

public class Config {
    /**
     * 需要指定schema
     */
    private boolean withSchema = false;

    /**
     * schema
     */
    private String schema;

    /**
     * 是否大小写敏感
     */
    private boolean caseSensitive = false;

    private String sensitiveSign = "\"";

    public boolean isWithSchema() {
        return withSchema;
    }

    public Config setWithSchema(boolean withSchema) {
        this.withSchema = withSchema;

        return this;
    }

    public String getSensitiveSign() {
        return sensitiveSign;
    }

    public Config setSensitiveSign(String sensitiveSign) {
        this.sensitiveSign = sensitiveSign;

        return this;
    }

    public String getSchema() {
        return schema;
    }

    public Config setSchema(String schema) {
        this.schema = schema;

        return this;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public Config setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;

        return this;
    }
}
