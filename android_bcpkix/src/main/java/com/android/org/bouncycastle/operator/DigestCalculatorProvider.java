/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.operator;

import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/**
 * @hide This class is not part of the Android public SDK API
 */
public interface DigestCalculatorProvider
{
    DigestCalculator get(AlgorithmIdentifier digestAlgorithmIdentifier)
        throws OperatorCreationException;
}
