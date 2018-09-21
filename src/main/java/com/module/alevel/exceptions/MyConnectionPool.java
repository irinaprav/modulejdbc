package com.module.alevel.exceptions;

import java.sql.Connection;
import java.util.function.Supplier;

    public class MyConnectionPool implements Supplier<Connection> {

        private final Connection connection;

        public MyConnectionPool(Connection connection) {
            this.connection = connection;
        }

        @Override
        public Connection get() {
            return connection;
        }


    }
