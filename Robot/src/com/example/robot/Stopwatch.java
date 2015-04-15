package com.example.robot;

public class Stopwatch { 

    private long start;

    public Stopwatch() {
    } 

    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
    
    public void start(){
    	start = System.currentTimeMillis();
    }

} 
