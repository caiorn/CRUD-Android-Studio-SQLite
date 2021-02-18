package com.caio.carteiradeclientes;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by caios on 29/06/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TestTest {

    @Test
    public void testar_se_um_e_diferente_a_dois()
    {
        Assert.assertFalse(1 == 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testar_se_lancou_excessao() {
        if(1 == 1)
            throw new IllegalArgumentException("Erro");
    }
}
