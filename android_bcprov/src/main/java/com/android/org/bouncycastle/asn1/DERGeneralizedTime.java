/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.asn1;

import java.util.Date;

/**
 * DER Generalized time object.
 * @hide This class is not part of the Android public SDK API
 */
public class DERGeneralizedTime
    extends ASN1GeneralizedTime
{

    DERGeneralizedTime(byte[] bytes)
    {
        super(bytes);
    }

    public DERGeneralizedTime(Date time)
    {
        super(time);
    }

    public DERGeneralizedTime(String time)
    {
        super(time);
    }

    // TODO: create proper DER encoding.
}
