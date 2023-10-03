package com.example.shelterapi.data;

public class InvalidQueryException extends RuntimeException{
    private final String query;

    public InvalidQueryException(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "InvalidQueryException{" +
                "query='" + query + '\'' +
                '}';
    }
}
