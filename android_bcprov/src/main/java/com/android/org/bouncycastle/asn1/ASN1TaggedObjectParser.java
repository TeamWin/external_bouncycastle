/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.asn1;

import java.io.IOException;

/**
 * @hide This class is not part of the Android public SDK API
 */
public interface ASN1TaggedObjectParser
    extends ASN1Encodable, InMemoryRepresentable
{
    public int getTagNo();
    
    public ASN1Encodable getObjectParser(int tag, boolean isExplicit)
        throws IOException;
}
