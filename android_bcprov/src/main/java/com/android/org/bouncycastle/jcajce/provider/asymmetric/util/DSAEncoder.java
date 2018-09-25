/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @hide This class is not part of the Android public SDK API
 */
public interface DSAEncoder
{
    byte[] encode(BigInteger r, BigInteger s)
        throws IOException;

    BigInteger[] decode(byte[] sig)
        throws IOException;
}
