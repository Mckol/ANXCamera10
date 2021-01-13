package com.miui.internal.webkit;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import java.lang.ref.WeakReference;

public class DeviceAccountLogin {
    private static final String TAG = "DeviceAccountLogin";
    protected AccountManager mAccountManager = AccountManager.get(this.mActivity.getApplicationContext());
    protected Activity mActivity;
    private AccountManagerCallback<Bundle> mCallback = new LoginCallback(this);

    private static class LoginCallback implements AccountManagerCallback<Bundle> {
        private WeakReference<DeviceAccountLogin> mLoginRef;

        public LoginCallback(DeviceAccountLogin deviceAccountLogin) {
            this.mLoginRef = new WeakReference<>(deviceAccountLogin);
        }

        @Override // android.accounts.AccountManagerCallback
        public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
            DeviceAccountLogin deviceAccountLogin = this.mLoginRef.get();
            if (deviceAccountLogin != null) {
                try {
                    String string = accountManagerFuture.getResult().getString("authtoken");
                    if (string == null) {
                        deviceAccountLogin.onLoginFail();
                    } else {
                        deviceAccountLogin.onLoginSuccess(string);
                    }
                } catch (Exception e2) {
                    Log.e(DeviceAccountLogin.TAG, "Fail to login", e2);
                    deviceAccountLogin.onLoginFail();
                }
            }
        }
    }

    public DeviceAccountLogin(Activity activity) {
        this.mActivity = activity;
    }

    public void login(String str, String str2, String str3) {
        Account account;
        Account[] accountsByType = this.mAccountManager.getAccountsByType(str);
        if (accountsByType.length == 0) {
            onLoginCancel();
            return;
        }
        Account account2 = null;
        int i = 0;
        if (!TextUtils.isEmpty(str2)) {
            int length = accountsByType.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                Account account3 = accountsByType[i];
                if (account3.name.equals(str2)) {
                    account = account3;
                    break;
                }
                i++;
            }
        } else {
            account2 = accountsByType[0];
        }
        account = account2;
        if (account != null) {
            onLoginStart();
            this.mAccountManager.getAuthToken(account, "weblogin:" + str3, (Bundle) null, (Activity) null, this.mCallback, (Handler) null);
            return;
        }
        onLoginCancel();
    }

    public void onLoginCancel() {
    }

    public void onLoginFail() {
    }

    public void onLoginPageFinished() {
    }

    public void onLoginStart() {
    }

    public void onLoginSuccess(String str) {
    }
}
