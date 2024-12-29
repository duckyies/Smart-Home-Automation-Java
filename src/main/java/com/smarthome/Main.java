package com.smarthome;

/**
 * Entry point for the Smart Home Automation project.
 * Automates smart home devices using data structures
 *
 * @author Abhinav Variyath
 * @author Anirudh Jayan
 * @author Tara Samiksha
 * @author Sarvesh Ram Kumar
 * @version 1.0
 * @date 2024-12-27
 */

import com.smarthome.PriorityQueue;
import com.smarthome.Task;

public class Main {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    PriorityQueue test = new PriorityQueue();
    test.enqueue(new Task<>("a",1));
    test.enqueue(new Task<>("a",3));
    test.enqueue(new Task<>("a",5));
    test.enqueue(new Task<>("a",7));
    test.enqueue(new Task<>("a",2));
    test.enqueue(new Task<>("a",4));
    test.enqueue(new Task<>("b",1));
    test.enqueue(new Task<>("b",2));
    test.enqueue(new Task<>("b",3));
    test.enqueue(new Task<>("b",7));
    test.print();
  }
}