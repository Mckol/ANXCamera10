package com.ss.android.ugc.effectmanager.effect.task.task;

import android.os.Handler;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.ugc.effectmanager.common.EffectConstants;
import com.ss.android.ugc.effectmanager.common.EffectRequest;
import com.ss.android.ugc.effectmanager.common.ErrorConstants;
import com.ss.android.ugc.effectmanager.common.exception.StatusCodeException;
import com.ss.android.ugc.effectmanager.common.listener.ICache;
import com.ss.android.ugc.effectmanager.common.listener.IJsonConverter;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;
import com.ss.android.ugc.effectmanager.common.task.NormalTask;
import com.ss.android.ugc.effectmanager.common.utils.EffectCacheKeyGenerator;
import com.ss.android.ugc.effectmanager.common.utils.EffectUtils;
import com.ss.android.ugc.effectmanager.common.utils.NetworkUtils;
import com.ss.android.ugc.effectmanager.context.EffectContext;
import com.ss.android.ugc.effectmanager.effect.model.BuildEffectChannelResponse;
import com.ss.android.ugc.effectmanager.effect.model.Effect;
import com.ss.android.ugc.effectmanager.effect.model.EffectCategoryModel;
import com.ss.android.ugc.effectmanager.effect.model.EffectCategoryResponse;
import com.ss.android.ugc.effectmanager.effect.model.EffectChannelModel;
import com.ss.android.ugc.effectmanager.effect.model.EffectChannelResponse;
import com.ss.android.ugc.effectmanager.effect.model.net.EffectNetListResponse;
import com.ss.android.ugc.effectmanager.effect.task.result.EffectChannelTaskResult;
import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchEffectChannelTask extends NormalTask {
    private static final String TAG = "SDK_FETCH_LIST";
    private EffectConfiguration mConfiguration = this.mEffectContext.getEffectConfiguration();
    private int mCurCnt = this.mConfiguration.getRetryCount();
    private EffectContext mEffectContext;
    private ICache mFileCache = this.mConfiguration.getCache();
    private IJsonConverter mJsonConverter = this.mConfiguration.getJsonConverter();
    private String mRemoteIp;
    private String mRequestedUrl;
    private String mSelectedHost;
    private String panel;

    public FetchEffectChannelTask(EffectContext effectContext, String str, String str2, Handler handler) {
        super(handler, str2, EffectConstants.NETWORK);
        this.panel = str;
        this.mEffectContext = effectContext;
    }

    private EffectRequest buildEffectListRequest() {
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(this.mConfiguration.getAccessKey())) {
            hashMap.put(EffectConfiguration.KEY_ACCESS_KEY, this.mConfiguration.getAccessKey());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getAppVersion())) {
            hashMap.put("app_version", this.mConfiguration.getAppVersion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getSdkVersion())) {
            hashMap.put(EffectConfiguration.KEY_SDK_VERSION, this.mConfiguration.getSdkVersion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getChannel())) {
            hashMap.put("channel", this.mConfiguration.getChannel());
        }
        if (!TextUtils.isEmpty(this.panel)) {
            hashMap.put(EffectConfiguration.KEY_PANEL, this.panel);
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getPlatform())) {
            hashMap.put(EffectConfiguration.KEY_DEVICE_PLATFORM, this.mConfiguration.getPlatform());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getDeviceId())) {
            hashMap.put("device_id", this.mConfiguration.getDeviceId());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getRegion())) {
            hashMap.put("region", this.mConfiguration.getRegion());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getDeviceType())) {
            hashMap.put(EffectConfiguration.KEY_DEVICE_TYPE, this.mConfiguration.getDeviceType());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getAppID())) {
            hashMap.put(EffectConfiguration.KEY_APP_ID, this.mConfiguration.getAppID());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getAppLanguage())) {
            hashMap.put(EffectConfiguration.KEY_APP_LANGUAGE, this.mConfiguration.getAppLanguage());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getSysLanguage())) {
            hashMap.put(EffectConfiguration.KEY_SYS_LANGUAGE, this.mConfiguration.getSysLanguage());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getLongitude())) {
            hashMap.put("longitude", this.mConfiguration.getLongitude());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getLatitude())) {
            hashMap.put("latitude", this.mConfiguration.getLatitude());
        }
        if (!TextUtils.isEmpty(this.mConfiguration.getCityCode())) {
            hashMap.put(EffectConfiguration.KEY_CITY_CODE, this.mConfiguration.getCityCode());
        }
        this.mSelectedHost = this.mEffectContext.getLinkSelector().getBestHostUrl();
        String buildRequestUrl = NetworkUtils.buildRequestUrl(hashMap, this.mSelectedHost + this.mConfiguration.getApiAdress() + EffectConstants.ROUTE_EFFECT_LIST);
        this.mRequestedUrl = buildRequestUrl;
        try {
            this.mRemoteIp = InetAddress.getByName(new URL(buildRequestUrl).getHost()).getHostAddress();
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
        } catch (MalformedURLException e3) {
            e3.printStackTrace();
        }
        return new EffectRequest("GET", buildRequestUrl);
    }

    private EffectChannelResponse dealResponse(EffectChannelModel effectChannelModel) {
        EffectChannelResponse effectChannelResponse = new EffectChannelResponse();
        effectChannelResponse.setPanel(this.panel);
        effectChannelResponse.setVersion(effectChannelModel.getVersion());
        effectChannelResponse.setAllCategoryEffects(effectChannelModel.getEffects());
        effectChannelResponse.setCollections(effectChannelModel.getCollection());
        effectChannelResponse.setCategoryResponseList(initCategory(effectChannelModel));
        effectChannelResponse.setPanelModel(effectChannelModel.getPanel());
        effectChannelResponse.setFrontEffect(getEffect(effectChannelModel.getFront_effect_id(), effectChannelModel));
        effectChannelResponse.setRearEffect(getEffect(effectChannelModel.getRear_effect_id(), effectChannelModel));
        fillEffectPath(effectChannelModel.getEffects());
        fillEffectPath(effectChannelModel.getCollection());
        saveEffectList(effectChannelModel);
        return effectChannelResponse;
    }

    private void deleteEffect(Effect effect) {
        if (EffectUtils.isEffectValid(effect)) {
            this.mFileCache.remove(effect.getId());
            ICache iCache = this.mFileCache;
            iCache.remove(effect.getId() + ".zip");
        }
    }

    private void fillEffectPath(List<Effect> list) {
        if (!(list == null || list.isEmpty())) {
            for (Effect effect : list) {
                effect.setZipPath(this.mConfiguration.getEffectDir() + File.separator + effect.getId() + ".zip");
                StringBuilder sb = new StringBuilder();
                sb.append(this.mConfiguration.getEffectDir());
                sb.append(File.separator);
                sb.append(effect.getId());
                effect.setUnzipPath(sb.toString());
            }
        }
    }

    private List<Effect> getCategoryAddedEffects(List<Effect> list, List<Effect> list2) {
        if (list == null || list.isEmpty()) {
            return list2;
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            boolean z = true;
            for (Effect effect : list2) {
                for (Effect effect2 : list) {
                    if (effect.equals(effect2)) {
                        z = false;
                    }
                }
                if (z) {
                    arrayList.add(effect);
                }
            }
            return arrayList;
        }
    }

    private List<Effect> getCategoryAllEffects(EffectCategoryModel effectCategoryModel, Map<String, Effect> map) {
        ArrayList arrayList = new ArrayList();
        for (String str : effectCategoryModel.getEffects()) {
            Effect effect = map.get(str);
            if (effect != null) {
                arrayList.add(effect);
            }
        }
        return arrayList;
    }

    private List<Effect> getCategoryDeletedEffects(List<Effect> list, List<Effect> list2) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            loop0:
            while (true) {
                boolean z = true;
                for (Effect effect : list) {
                    for (Effect effect2 : list2) {
                        if (effect.equals(effect2)) {
                            z = false;
                        }
                    }
                    if (z) {
                        arrayList.add(effect);
                    }
                }
                break loop0;
            }
        }
        return arrayList;
    }

    private Effect getEffect(String str, EffectChannelModel effectChannelModel) {
        Effect effect = null;
        for (Effect effect2 : effectChannelModel.getEffects()) {
            if (TextUtils.equals(str, effect2.getEffectId())) {
                effect = effect2;
            }
        }
        return effect;
    }

    private List<EffectCategoryResponse> initCategory(EffectChannelModel effectChannelModel) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        for (Effect effect : effectChannelModel.getEffects()) {
            hashMap.put(effect.getEffectId(), effect);
        }
        if (!effectChannelModel.getCategory().isEmpty()) {
            for (EffectCategoryModel effectCategoryModel : effectChannelModel.getCategory()) {
                EffectCategoryResponse effectCategoryResponse = new EffectCategoryResponse();
                effectCategoryResponse.setId(effectCategoryModel.getId());
                effectCategoryResponse.setName(effectCategoryModel.getName());
                if (!effectCategoryModel.getIcon().getUrl_list().isEmpty()) {
                    effectCategoryResponse.setIcon_normal_url(effectCategoryModel.getIcon().getUrl_list().get(0));
                }
                if (!effectCategoryModel.getIcon_selected().getUrl_list().isEmpty()) {
                    effectCategoryResponse.setIcon_selected_url(effectCategoryModel.getIcon_selected().getUrl_list().get(0));
                }
                effectCategoryResponse.setTotalEffects(getCategoryAllEffects(effectCategoryModel, hashMap));
                effectCategoryResponse.setTags(effectCategoryModel.getTags());
                effectCategoryResponse.setTagsUpdateTime(effectCategoryModel.getTagsUpdateTime());
                effectCategoryResponse.setCollectionEffect(effectChannelModel.getCollection());
                arrayList.add(effectCategoryResponse);
            }
        }
        return arrayList;
    }

    private void saveEffectList(EffectChannelModel effectChannelModel) {
        this.mFileCache.save(EffectCacheKeyGenerator.generatePanelKey(this.mConfiguration.getChannel(), this.panel), this.mJsonConverter.convertObjToJson(effectChannelModel));
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", effectChannelModel.getVersion());
            ICache iCache = this.mFileCache;
            iCache.save("effect_version" + this.panel, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void execute() {
        EffectRequest buildEffectListRequest = buildEffectListRequest();
        while (true) {
            int i = this.mCurCnt;
            this.mCurCnt = i - 1;
            if (i != 0) {
                try {
                    if (isCanceled()) {
                        ExceptionResult exceptionResult = new ExceptionResult((int) ErrorConstants.CODE_CANCEL_DOWNLOAD);
                        exceptionResult.setTrackParams(this.mRequestedUrl, this.mSelectedHost, this.mRemoteIp);
                        sendMessage(14, new EffectChannelTaskResult(new EffectChannelResponse(this.panel), exceptionResult));
                        return;
                    }
                    EffectNetListResponse effectNetListResponse = (EffectNetListResponse) this.mConfiguration.getEffectNetWorker().execute(buildEffectListRequest, this.mJsonConverter, EffectNetListResponse.class);
                    if (effectNetListResponse.checkValued()) {
                        EffectChannelModel data = effectNetListResponse.getData();
                        EffectChannelResponse buildChannelResponse = new BuildEffectChannelResponse(this.panel, this.mEffectContext.getEffectConfiguration().getEffectDir().getAbsolutePath(), false).buildChannelResponse(data);
                        saveEffectList(data);
                        sendMessage(14, new EffectChannelTaskResult(buildChannelResponse, null));
                        return;
                    } else if (this.mCurCnt == 0) {
                        ExceptionResult exceptionResult2 = new ExceptionResult((int) ErrorConstants.CODE_DOWNLOAD_ERROR);
                        exceptionResult2.setTrackParams(this.mRequestedUrl, this.mSelectedHost, this.mRemoteIp);
                        sendMessage(14, new EffectChannelTaskResult(new EffectChannelResponse(this.panel), exceptionResult2));
                        return;
                    }
                } catch (Exception e2) {
                    if (this.mCurCnt == 0 || (e2 instanceof StatusCodeException)) {
                        sendMessage(14, new EffectChannelTaskResult(new EffectChannelResponse(this.panel), new ExceptionResult(e2)));
                    }
                }
            } else {
                return;
            }
        }
        sendMessage(14, new EffectChannelTaskResult(new EffectChannelResponse(this.panel), new ExceptionResult(e2)));
    }
}
