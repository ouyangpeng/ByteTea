package net.mikaelzero.bytetea.encryptstr;

import com.ss.android.ugc.bytex.common.BaseExtension;

import java.util.List;

public class EncryptStringExtension extends BaseExtension {
    /**
     * 要加密的包名列表
     */
    private List<String> encryptPackages;

    public List<String> getEncryptPackages() {
        return encryptPackages;
    }

    public void setEncryptPackages(List<String> encryptPackages) {
        this.encryptPackages = encryptPackages;
    }

    @Override
    public String getName() {
        return "encrypt_string";
    }
}
