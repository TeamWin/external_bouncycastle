/* GENERATED SOURCE. DO NOT MODIFY. */
package com.android.org.bouncycastle.jcajce.provider.config;

import java.util.Map;
import java.util.Set;

import javax.crypto.spec.DHParameterSpec;

import com.android.org.bouncycastle.jce.spec.ECParameterSpec;

public interface ProviderConfiguration
{
    ECParameterSpec getEcImplicitlyCa();

    DHParameterSpec getDHDefaultParameters(int keySize);

    Set getAcceptableNamedCurves();

    Map getAdditionalECParameters();
}
