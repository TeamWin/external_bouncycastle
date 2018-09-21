/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.cert.ocsp.jcajce;

import java.security.PublicKey;

import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.cert.ocsp.BasicOCSPRespBuilder;
import com.android.org.bouncycastle.cert.ocsp.OCSPException;
import com.android.org.bouncycastle.operator.DigestCalculator;

/**
 * @hide This class is not part of the Android public SDK API
 */
public class JcaBasicOCSPRespBuilder
    extends BasicOCSPRespBuilder
{
    public JcaBasicOCSPRespBuilder(PublicKey key, DigestCalculator digCalc)
        throws OCSPException
    {
        super(SubjectPublicKeyInfo.getInstance(key.getEncoded()), digCalc);
    }
}
