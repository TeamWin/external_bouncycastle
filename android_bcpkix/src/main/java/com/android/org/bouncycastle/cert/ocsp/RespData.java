/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.cert.ocsp;

import java.util.Date;

import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ocsp.ResponseData;
import com.android.org.bouncycastle.asn1.ocsp.SingleResponse;
import com.android.org.bouncycastle.asn1.x509.Extensions;

/**
 * @hide This class is not part of the Android public SDK API
 */
public class RespData
{
    private ResponseData    data;

    public RespData(
        ResponseData    data)
    {
        this.data = data;
    }

    public int getVersion()
    {
        return data.getVersion().getValue().intValue() + 1;
    }

    public RespID getResponderId()
    {
        return new RespID(data.getResponderID());
    }

    public Date getProducedAt()
    {
        return OCSPUtils.extractDate(data.getProducedAt());
    }

    public SingleResp[] getResponses()
    {
        ASN1Sequence    s = data.getResponses();
        SingleResp[]    rs = new SingleResp[s.size()];

        for (int i = 0; i != rs.length; i++)
        {
            rs[i] = new SingleResp(SingleResponse.getInstance(s.getObjectAt(i)));
        }

        return rs;
    }

    public Extensions getResponseExtensions()
    {
        return data.getResponseExtensions();
    }
}
