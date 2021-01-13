package java.miui.autoinstall.config.pm;

/* compiled from: MarketInstallerListener */
public interface a {
    void onRefuseInstall(String str, int i);

    void onServiceDead();

    void packageInstalled(String str, int i);
}
