package com.example.java_threads_2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {

    private int max = 200_000;
    private int value;
    private int threadLimit = 4;
//    private Lock lock = new ReentrantLock();
    private Output output;


    public Counter(int max) {
        this.max = max;
    }

    public Counter()
    {
        // Will default to max if nothing is specified
    }

    public void startIncrementing()
    {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < threadLimit; i++)
        {
            int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < max / threadLimit; i++)
                    {
                        increment();
                    }
                    if (output != null)
                    {
                        output.append("Thread " + finalI + " finished counting.\n");
                    }
                }
            });
            threadList.add(thread);
        }
        for (Thread thread : threadList)
            thread.start();

        for (Thread thread : threadList)
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void increment()
    {
//        lock.lock();
        value++;
//        lock.unlock();
    }


    public int getValue() {
        return value;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public static void main(String[] args) {
        Counter counter_1 = new Counter(500_000);
        Counter counter_2 = new Counter(1_000_000);

        counter_1.setOutput(new Output() {
            @Override
            public void append(String message) {
                System.out.println(message);
            }
        });

        counter_2.setOutput(new Output() {
            @Override
            public void append(String message) {
                System.out.println(message);
            }
        });


        counter_1.startIncrementing();
        counter_2.startIncrementing();

//        System.out.println("\nThe counter is: " + counter_1.getValue());
//        System.out.println("The counter is: " + counter_2.getValue());
    }
}
