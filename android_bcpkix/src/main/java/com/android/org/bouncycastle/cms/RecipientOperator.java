/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.cms;

import java.io.InputStream;

import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.operator.InputDecryptor;
import com.android.org.bouncycastle.operator.MacCalculator;
import com.android.org.bouncycastle.util.io.TeeInputStream;

/**
 * @hide This class is not part of the Android public SDK API
 */
public class RecipientOperator
{
    private final AlgorithmIdentifier algorithmIdentifier;
    private final Object operator;

    public RecipientOperator(InputDecryptor decryptor)
    {
        this.algorithmIdentifier = decryptor.getAlgorithmIdentifier();
        this.operator = decryptor;
    }

    public RecipientOperator(MacCalculator macCalculator)
    {
        this.algorithmIdentifier = macCalculator.getAlgorithmIdentifier();
        this.operator = macCalculator;
    }

    public InputStream getInputStream(InputStream dataIn)
    {
        if (operator instanceof InputDecryptor)
        {
            return ((InputDecryptor)operator).getInputStream(dataIn);
        }
        else
        {
            return new TeeInputStream(dataIn, ((MacCalculator)operator).getOutputStream());
        }
    }

    public boolean isMacBased()
    {
        return operator instanceof MacCalculator;
    }

    public byte[] getMac()
    {
        return ((MacCalculator)operator).getMac();
    }
}
