package test;

import java.util.Comparator;

/**
 * Test class
 * @author ubersandro
 * @version 1.0
 */
public class Prova extends Test1 {
    static abstract class Nodo<T>{
        Nodo<T> next;
        Nodo<T> previous;
        protected abstract Nodo<T> n();
    }
    static class P<T> extends Nodo{
        @Override
        protected Nodo n() {
            return new P<T>();
        }
    }
    static int ciao;
    public static void main(String[] args) {
        Comparator<Integer> co = (i1,i2) -> {
            return i1>i2?1:0;/*NAIF*/
        };
        new Prova().no(System.out::print); /*reference method*/
        Runnable b = new Runnable(){
           public void run(){
               System.out.println("Ciao");
           }
        };
        boolean t = false;
        char c =1;

        if(~c==-1) System.out.println("c");
        new Thread(b).start();
    }
    @Override
    protected double cast(){
        return (double)x*x;
    }

}

