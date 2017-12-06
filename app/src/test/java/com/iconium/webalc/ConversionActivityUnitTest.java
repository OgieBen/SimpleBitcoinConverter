package com.iconium.webalc;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ogie on 12/5/2017.
 */

public class ConversionActivityUnitTest {

    @Test
    public void isAmountToConvert_Double(){
        ConversionActivity conversionActivity = new ConversionActivity();
       double amountToConvert = conversionActivity.getAmountToConvert();

        assertThat(amountToConvert, is(0.0));

       // assertSame("amount to convert is not Double", amountToConvert,);
    }
}
