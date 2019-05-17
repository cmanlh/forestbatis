package com.lifeonwalden;

import org.apache.commons.math3.stat.Frequency;
import org.junit.Test;

/**
 * Unit builder for simple App.
 */
public class AppTest {
    @Test
    public void math() {
        Frequency f = new Frequency();
        f.addValue(1);
        f.addValue(new Integer(1));
        f.addValue(new Long(1));
        f.addValue(2);
        f.addValue(new Integer(-1));
        System.out.println(f.getCount(1));   // displays 3
        System.out.println(f.getCumPct(0));  // displays 0.2
        System.out.println(f.getPct(new Integer(1)));  // displays 0.6
        System.out.println(f.getCumPct(-2));   // displays 0
        System.out.println(f.getCumPct(10));  // displays 1
    }
}
