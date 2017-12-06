package com.iconium.webalc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ogie on 11/23/2017.
 */

public class MainActvittyTest {

    @Test
    public void _isDataPulledFromApi() throws Exception{
        MainActivity mainActivity = new MainActivity();
        int temp = mainActivity.getConversionRates();
        assertEquals(1, temp);

    }
}
