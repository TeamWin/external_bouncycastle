/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.operator.bc;

import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.crypto.ExtendedDigest;
import com.android.org.bouncycastle.operator.OperatorCreationException;

/**
 * @hide This class is not part of the Android public SDK API
 */
public interface BcDigestProvider
{
    ExtendedDigest get(AlgorithmIdentifier digestAlgorithmIdentifier)
        throws OperatorCreationException;
}
