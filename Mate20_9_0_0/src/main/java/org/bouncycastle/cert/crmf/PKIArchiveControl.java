package org.bouncycastle.cert.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.crmf.CRMFObjectIdentifiers;
import org.bouncycastle.asn1.crmf.EncryptedKey;
import org.bouncycastle.asn1.crmf.PKIArchiveOptions;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSException;

public class PKIArchiveControl implements Control {
    public static final int archiveRemGenPrivKey = 2;
    public static final int encryptedPrivKey = 0;
    public static final int keyGenParameters = 1;
    private static final ASN1ObjectIdentifier type = CRMFObjectIdentifiers.id_regCtrl_pkiArchiveOptions;
    private final PKIArchiveOptions pkiArchiveOptions;

    public PKIArchiveControl(PKIArchiveOptions pKIArchiveOptions) {
        this.pkiArchiveOptions = pKIArchiveOptions;
    }

    public int getArchiveType() {
        return this.pkiArchiveOptions.getType();
    }

    public CMSEnvelopedData getEnvelopedData() throws CRMFException {
        StringBuilder stringBuilder;
        try {
            return new CMSEnvelopedData(new ContentInfo(CMSObjectIdentifiers.envelopedData, EnvelopedData.getInstance(EncryptedKey.getInstance(this.pkiArchiveOptions.getValue()).getValue())));
        } catch (CMSException e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("CMS parsing error: ");
            stringBuilder.append(e.getMessage());
            throw new CRMFException(stringBuilder.toString(), e.getCause());
        } catch (Throwable e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("CRMF parsing error: ");
            stringBuilder.append(e2.getMessage());
            throw new CRMFException(stringBuilder.toString(), e2);
        }
    }

    public ASN1ObjectIdentifier getType() {
        return type;
    }

    public ASN1Encodable getValue() {
        return this.pkiArchiveOptions;
    }

    public boolean isEnvelopedData() {
        return EncryptedKey.getInstance(this.pkiArchiveOptions.getValue()).isEncryptedValue() ^ 1;
    }
}