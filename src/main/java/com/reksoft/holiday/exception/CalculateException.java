package com.reksoft.holiday.exception;

public class CalculateException extends Exception{
        private String message;

        public CalculateException(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
}
