package com.github.dbs.builder;

import java.util.*;
import java.util.stream.Collectors;

public class SqlBuilder {

    private SqlBuilder() {
    }

    public static class Select {
        private String fields;
        private String table;
        private List<String> conditions = new ArrayList<>();

        public Select(String fields) {
            this.fields = fields;
        }

        public Select from(String table) {
            this.table = table;
            return this;
        }

        public Select where(String field, String comparation, String value) {
            this.conditions.add(field + " " + comparation + " '" + value + "'");
            return this;
        }

        public Select where(String field, String comparation, Integer value) {
            this.conditions.add(field + " " + comparation + " " + value);
            return this;
        }

        public String buildString() {
            return "SELECT " + fields + " FROM " + table + " WHERE " + String.join(" AND ", conditions);
        }
    }

    public static class Delete {
        private String table;
        private String condition;

        public Delete from(String table) {
            this.table = table;
            return this;
        }

        public Delete where(String field, String comparation, String value) {
            this.condition = field + " " + comparation + " '" + value + "'";
            return this;
        }

        public Delete where(String field, String comparation, Integer value) {
            this.condition = field + " " + comparation + " " + value;
            return this;
        }

        public String buildString() {
            return "DELETE FROM " + table + " WHERE " + condition;
        }
    }

    public static class Update {
        private List<String> sets = new ArrayList<>();
        private String table;
        private String condition;

        public Update(String table) {
            this.table = table;
        }

        public Update set(String field, String value) {
            this.sets.add(field + " = '" + value + "'");
            return this;
        }

        public Update where(String field, String comparation, String value) {
            this.condition = field + " " + comparation + " '" + value + "'";
            return this;
        }

        public Update where(String field, String comparation, Integer value) {
            this.condition = field + " " + comparation + " " + value;
            return this;
        }

        public String buildString() {
            return "UPDATE " + table + " SET " + String.join(",", sets) + " WHERE " + condition;
        }
    }

    public static class Insert {
        private LinkedHashMap<String, String> columnValue = new LinkedHashMap<>();
        private String table;

        public Insert(String table) {
            this.table = table;
        }

        public Insert column(String column, String value) {
            this.columnValue.put(column, value);
            return this;
        }

        private String getColums() {
            return String.join(",", this.columnValue.keySet());
        }

        private String getValues() {
            Collection<String> formattedColumnValue = this.columnValue
                    .values()
                    .stream()
                    .map(value -> "'" + value + "'")
                    .collect(Collectors.toList());

            return String.join(",", formattedColumnValue);
        }

        public String buildString() {
            return "INSERT INTO " + table + " (" + this.getColums() + ") VALUES (" + this.getValues() + ")";
        }
    }
}
