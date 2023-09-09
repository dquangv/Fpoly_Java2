/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab6.Bai3;

/**
 *
 * @author Quang
 */
public class TestThread {
    public static void main(String[] args) throws InterruptedException {
        OddThread ot = new OddThread();
        EvenThread et = new EvenThread();
        Thread t1 = new Thread(ot);
        Thread t2 = new Thread(et);
        
        t1.start();
        t1.join();
        t2.start();
    }
}
