package com.ss.android.ugc.effectmanager;

import android.content.Context;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.TaskManager;
import com.ss.android.ugc.effectmanager.common.listener.ICache;
import com.ss.android.ugc.effectmanager.common.listener.IEffectNetWorker;
import com.ss.android.ugc.effectmanager.common.listener.IJsonConverter;
import com.ss.android.ugc.effectmanager.common.listener.IMonitorService;
import com.ss.android.ugc.effectmanager.effect.bridge.EffectFetcher;
import com.ss.android.ugc.effectmanager.effect.task.task.DefaultEffectFetcher;
import com.ss.android.ugc.effectmanager.link.model.configuration.LinkSelectorConfiguration;
import com.ss.android.ugc.effectmanager.link.model.host.Host;
import com.ss.android.ugc.effectmanager.network.EffectNetWorkerWrapper;
import java.io.File;
import java.util.List;

public class EffectConfiguration {
    private static final String API_ADDRESS = "/effect/api";
    public static final String KEY_ACCESS_KEY = "access_key";
    public static final String KEY_APP_ID = "aid";
    public static final String KEY_APP_LANGUAGE = "app_language";
    public static final String KEY_APP_VERSION = "app_version";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CHANNEL = "channel";
    public static final String KEY_CITY_CODE = "city_code";
    public static final String KEY_COUNT = "count";
    public static final String KEY_CURSOR = "cursor";
    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_DEVICE_PLATFORM = "device_platform";
    public static final String KEY_DEVICE_TYPE = "device_type";
    public static final String KEY_EFFECT_IDS = "effect_ids";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_PANEL = "panel";
    public static final String KEY_PROVIDER_NAME = "library";
    public static final String KEY_REGION = "region";
    public static final String KEY_SDK_VERSION = "sdk_version";
    public static final String KEY_SEARCH_WORD = "word";
    public static final String KEY_SORTING_POSITION = "sorting_position";
    public static final String KEY_SYS_LANGUAGE = "language";
    public static final String KEY_TYPE = "type";
    public static final String KEY_VERSION = "version";
    public static final String KEY_WITH_CATEGORY_EFFECTS = "has_category_effects";
    private String cityCode;
    private IJsonConverter jsonConverter;
    private String latitude;
    private ListenerManger listenerManger;
    private String longitude;
    private String mAccessKey;
    private String mApiAddress;
    private String mAppID;
    private String mAppLanguage;
    private String mAppVersion;
    private ICache mCache;
    private String mChannel;
    private String mDeviceId;
    private String mDeviceType;
    private File mEffectDir;
    private EffectFetcher mEffectFetcher;
    private EffectNetWorkerWrapper mEffectNetWorker;
    private LinkSelectorConfiguration mLinkSelectorConfiguration;
    private String mPlatform;
    private String mRegion;
    private int mRetryCount;
    private String mSdkVersion;
    private String mSysLanguage;
    private TaskManager mTaskManager;
    private IMonitorService monitorService;

    public static final class Builder {
        private String accessKey;
        private String appID;
        private String appLanguage;
        private String appVersion;
        private ICache cache;
        private String channel;
        private String cityCode;
        private String deviceId;
        private String deviceType;
        private File effectDir;
        private EffectFetcher effectFetcher;
        private IEffectNetWorker effectNetWorker;
        private IJsonConverter jsonConverter;
        private String latitude;
        private String longitude;
        private LinkSelectorConfiguration mLinkSelectorConfiguration = new LinkSelectorConfiguration();
        private IMonitorService monitorService;
        private String platform;
        private String region;
        private int retryCount = 3;
        private String sdkVersion;
        private String sysLanguage;

        public Builder JsonConverter(IJsonConverter iJsonConverter) {
            this.jsonConverter = iJsonConverter;
            return this;
        }

        public Builder accessKey(String str) {
            this.accessKey = str;
            return this;
        }

        public Builder appID(String str) {
            this.appID = str;
            return this;
        }

        public Builder appLanguage(String str) {
            this.appLanguage = str;
            return this;
        }

        public Builder appVersion(String str) {
            this.appVersion = str;
            return this;
        }

        public EffectConfiguration build() {
            return new EffectConfiguration(this);
        }

        public Builder cache(ICache iCache) {
            this.cache = iCache;
            return this;
        }

        public Builder channel(String str) {
            this.channel = str;
            return this;
        }

        public Builder context(Context context) {
            this.mLinkSelectorConfiguration.setContext(context);
            return this;
        }

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder deviceType(String str) {
            this.deviceType = str;
            return this;
        }

        public Builder effectDir(File file) {
            this.effectDir = file;
            File file2 = this.effectDir;
            if (file2 != null && !file2.exists()) {
                this.effectDir.mkdirs();
            }
            return this;
        }

        public Builder effectFetcher(EffectFetcher effectFetcher2) {
            this.effectFetcher = effectFetcher2;
            return this;
        }

        public Builder effectNetWorker(IEffectNetWorker iEffectNetWorker) {
            this.effectNetWorker = iEffectNetWorker;
            return this;
        }

        public Builder hosts(List<Host> list) {
            this.mLinkSelectorConfiguration.setOriginHosts(list);
            return this;
        }

        public Builder lazy(boolean z) {
            this.mLinkSelectorConfiguration.setLazy(z);
            return this;
        }

        public Builder monitorService(IMonitorService iMonitorService) {
            this.monitorService = iMonitorService;
            return this;
        }

        public Builder netWorkChangeMonitor(boolean z) {
            this.mLinkSelectorConfiguration.setNetworkChangeMonitor(z);
            return this;
        }

        public Builder platform(String str) {
            this.platform = str;
            return this;
        }

        public Builder poi(String str, String str2, String str3) {
            this.longitude = str;
            this.latitude = str2;
            this.cityCode = str3;
            return this;
        }

        public Builder region(String str) {
            this.region = str;
            return this;
        }

        public Builder repeatTime(int i) {
            this.mLinkSelectorConfiguration.setRepeatTime(i);
            return this;
        }

        public Builder retryCount(int i) {
            this.retryCount = i;
            return this;
        }

        public Builder sdkVersion(String str) {
            this.sdkVersion = str;
            return this;
        }

        public Builder speedApi(String str) {
            this.mLinkSelectorConfiguration.setSpeedApi(str);
            return this;
        }

        public Builder speedTimeOut(int i) {
            this.mLinkSelectorConfiguration.setSpeedTimeOut(i);
            return this;
        }

        public Builder sysLanguage(String str) {
            this.sysLanguage = str;
            return this;
        }
    }

    private EffectConfiguration(Builder builder) {
        String str;
        this.mChannel = EffectConstants.CHANNEL_ONLINE;
        this.mRetryCount = 3;
        this.mApiAddress = API_ADDRESS;
        this.mAccessKey = builder.accessKey;
        this.mSdkVersion = builder.sdkVersion;
        this.mAppVersion = builder.appVersion;
        this.mDeviceId = builder.deviceId;
        this.mChannel = (TextUtils.equals(EffectConstants.CHANNEL_TEST, builder.channel) || TextUtils.equals(EffectConstants.CHANNEL_LOCAL_TEST, builder.channel)) ? EffectConstants.CHANNEL_TEST : str;
        this.mPlatform = builder.platform;
        this.mDeviceType = builder.deviceType;
        this.mEffectDir = builder.effectDir;
        this.mEffectNetWorker = new EffectNetWorkerWrapper(builder.effectNetWorker);
        this.mRegion = builder.region;
        this.mCache = builder.cache;
        this.mRetryCount = builder.retryCount;
        this.jsonConverter = builder.jsonConverter;
        this.mAppID = builder.appID;
        this.mAppLanguage = builder.appLanguage;
        this.mSysLanguage = builder.sysLanguage;
        this.mLinkSelectorConfiguration = builder.mLinkSelectorConfiguration;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.cityCode = builder.cityCode;
        this.monitorService = builder.monitorService;
        this.mEffectFetcher = builder.effectFetcher == null ? new DefaultEffectFetcher(this.mEffectNetWorker) : builder.effectFetcher;
        this.listenerManger = new ListenerManger();
    }

    public String getAccessKey() {
        return this.mAccessKey;
    }

    public String getApiAdress() {
        return this.mApiAddress;
    }

    public String getAppID() {
        return this.mAppID;
    }

    public String getAppLanguage() {
        return this.mAppLanguage;
    }

    public String getAppVersion() {
        return this.mAppVersion;
    }

    public ICache getCache() {
        return this.mCache;
    }

    public String getChannel() {
        return this.mChannel;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public String getDeviceId() {
        return this.mDeviceId;
    }

    public String getDeviceType() {
        return this.mDeviceType;
    }

    public File getEffectDir() {
        return this.mEffectDir;
    }

    public EffectFetcher getEffectFetcher() {
        return this.mEffectFetcher;
    }

    public EffectNetWorkerWrapper getEffectNetWorker() {
        return this.mEffectNetWorker;
    }

    public IJsonConverter getJsonConverter() {
        return this.jsonConverter;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public LinkSelectorConfiguration getLinkSelectorConfiguration() {
        return this.mLinkSelectorConfiguration;
    }

    public ListenerManger getListenerManger() {
        return this.listenerManger;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public IMonitorService getMonitorService() {
        return this.monitorService;
    }

    public String getPlatform() {
        return this.mPlatform;
    }

    public String getRegion() {
        return this.mRegion;
    }

    public int getRetryCount() {
        return this.mRetryCount;
    }

    public String getSdkVersion() {
        return this.mSdkVersion;
    }

    public String getSysLanguage() {
        return this.mSysLanguage;
    }

    public TaskManager getTaskManager() {
        return this.mTaskManager;
    }

    public void setCache(ICache iCache) {
        this.mCache = iCache;
    }

    public void setCityCode(String str) {
        this.cityCode = str;
    }

    public void setDeviceId(String str) {
        this.mDeviceId = str;
    }

    public void setEffectDir(File file) {
        this.mEffectDir = file;
    }

    public void setEffectNetWorker(IEffectNetWorker iEffectNetWorker) {
        this.mEffectNetWorker.setIEffectNetWorker(iEffectNetWorker);
    }

    public void setLatitude(String str) {
        this.latitude = str;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.mTaskManager = taskManager;
    }
}
