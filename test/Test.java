package test;

/**
 * Functional interface test
 * @author ubersandro
 */
interface Test {
    default void test() {
        System.out.println("this is a test");
    }
    default void no(Printer e){
        this.test();
        int x=2 ,y=3;
        e.print(this);
        System.out.println("No");
    }

}

interface Printer{
    void print(Object o);
}
