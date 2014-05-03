package test;



import omsu.svion.game.states.Playing;
import omsu.svion.game.states.State;

import java.util.Objects;

/**
 * Created by victor on 11.04.14.
 */
public class SimpleTest {
    public static void main(String [] args) {
        System.out.println((Playing.class.getSuperclass().getSuperclass()));
        //System.out.println(new Pair<Class,Class>(String.class,Integer.class).equals(new Pair<Class,Class>(String.class,Integer.class)));
    }
}
