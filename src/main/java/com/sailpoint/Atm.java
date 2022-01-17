package com.sailpoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;

public final class Atm {
    private boolean isLogin = false;
    private boolean isRunning = true;
    private double balance = 0.0;
    private String pin = "1234";

    public static void main(String[] args) {
        Atm atm = new Atm();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (atm.isRunning) {
                String input = reader.readLine();
                String output = atm.run(input);
                System.out.println(output);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public String run(String input) {
        String result = "ok";
        String[] split = input.split(" ");

        try {
            if (split.length > 0) {
                switch (split[0]) {
                    case "login":
                        login(split);
                        break;
                    case "view":
                        result = view();
                        break;
                    case "deposit":
                        deposit(split);
                        break;
                    case "withdraw":
                        withdraw(split);
                        break;
                    case "exit":
                        isRunning = false;
                        break;
                    default:
                        throw new RuntimeException("Unknown command.");
                }
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }

        return result;
    }

    private void login(String[] split) {
        isLogin = false;

        if (split == null) {
            throw new NullPointerException("Invalid input");
        } else if (split.length < 2) {
            throw new InputMismatchException("No input pin");
        } else if (split[1].length() != pin.length()) {
            throw new InputMismatchException("Wrong pin length");
        } else if (!split[1].equals(pin)) {
            throw new RuntimeException("Wrong pin");
        } else {
            isLogin = true;
        }
    }

    private String view() {
        if (!isLogin) {
            throw new RuntimeException("Unauthorized");
        }

        return Double. toString(balance);
    }

    private void deposit(String[] split) {
        if (!isLogin) {
            throw new RuntimeException("Unauthorized");
        }

        balance += parse(split, Double.MAX_VALUE);
    }

    private void withdraw(String[] split) {
        if (!isLogin) {
            throw new RuntimeException("Unauthorized");
        }

        balance -= parse(split, balance);
    }

    private double parse(String[] split, double max) {
        if (split == null) {
            throw new NullPointerException("Invalid input");
        } else if (split.length < 2) {
            throw new InputMismatchException("No input number");
        }

        double number = Double.parseDouble(split[1]);
        if (number <= 0) {
            throw new NumberFormatException("Not positive number");
        } else if (number >= max) {
            throw new NumberFormatException("Exceed balance");
        }

        return number;
    }
}
