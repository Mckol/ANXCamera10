package com.google.android.apps.gsa.search.shared.service.proto;

import com.google.protobuf.Internal;

public enum ClientEventId implements Internal.EnumLite {
    UNKNOWN(0),
    SEARCH_TOOLS_CLICKED(1),
    WINDOW_FOCUS_CHANGED(2),
    PADDING_CHANGED(3),
    MAIN_VIEW_TOUCHED(4),
    REFRESH_DOODLE_DATA(5),
    EXPAND_SEARCH_PLATE_SUGGESTIONS(6),
    LOCATION_SETTINGS_DIALOG_RESPONSE(7),
    LOG_SUGGESTIONS(9),
    ACTION_DRAG_BEGIN(10),
    SUGGESTION_ACTION_BUTTON_CLICKED(11),
    CONTACT_SELECTED_EXTERNALLY(12),
    FINALIZE_RESULT_ON_USER_INTERACTION(14),
    SECURITY_ICON_CLICKED(15),
    OPEN_DOODLE(16),
    SUGGEST_RESPONSE_RENDERED(17),
    ACTION_VE_LOGGING_EVENT(18),
    ACTION_CHANGED_EXTERNALLY(23),
    SUGGESTION_CLICK(24),
    REMOVE_SUGGESTION(25),
    CANVAS_WORKER_BINDER_REQUEST(27),
    CANVAS_WORKER_BINDER_PROVIDER_REQUEST(127),
    FETCH_NATIVE_RESULT(29),
    LOGO_CLICKED(31),
    NEW_ACTIVE_NOTIFICATIONS(32),
    CLEAR_ACTIVE_NOTIFICATIONS(64),
    CANVAS_RENDER_COMPLETE(33),
    ACTION_COUNTDOWN_CANCELED(34),
    ESCAPE_HATCH_CLICKED(35),
    ACQUIRE_KEYBOARD_FOCUS(36),
    RELEASE_KEYBOARD_FOCUS(37),
    ACTION_USER_INTERACTION(38),
    SAVE_USER_INTERACTION(39),
    QUEUE_QUERY_FOR_BACKGROUND_RETRY(40),
    SET_GWS_LOGGABLE_EVENT(41),
    SUPPRESS_GWS_LOGGABLE_EVENT(42),
    NOW_CARDS_REFRESH_REQUEST(43),
    UNDOABLE_INTERACTION_NOT_HANDLED(44),
    UNMUTE_AFFORDANCE_CLICKED(45),
    CLEAR_VOICE_ACTIONS(46),
    SEARCH_TOOLS_CLEAR_BUTTON_CLICKED(47),
    SEARCH_TOOLS_OPTION_SELECTED(48),
    STOP_ENDLESS_MODE_ON_USER_INTERACTION(49),
    BROWSABLE_CATEGORY_SELECTED(51),
    HANDLE_ACTIONS_BACK_PRESS(52),
    GCM_MESSAGE_RECEIVED(53),
    OPT_IN_REQUEST(54),
    DEQUEUE_QUERY_FOR_BACKGROUND_RETRY(55),
    LEGACY_ACTIVE_NOTIFICATIONS(57),
    HOTWORD_DETECTED_IN_INTERACTOR(58),
    HOTWORD_REJECTED_AFTER_DSP_TRIGGERED(59),
    ACTIVITY_COMPLETED_WITH_RESULT(60),
    TTS_PLAYBACK_COMPLETE(61),
    HANDLE_MANUAL_RETRY(62),
    BROWSABLE_INTENT_LAUNCHED_IN_CLIENT(63),
    GEARHEAD_CONNECTED(65),
    GEARHEAD_DISCONNECTED(66),
    TIMESTAMP_UI_GET_LATEST_CLICKED(67),
    REMOTE_ACTION_TRIGGERED(68),
    STOP_LISTENING(69),
    STOP_SPEAKING(70),
    GO_BACK(71),
    SET_BROWSER_DIMENSION(72),
    TTS_STOP_SPEAKING(73),
    WEBVIEW_VISIBLE(74),
    UNSUPPRESS_CORPORA(75),
    SET_SPEECH_DETECTION_ENABLED(76),
    SET_HOTWORD_DETECTION_ENABLED(77),
    QUERY_SET(78),
    QUERY_EDIT(79),
    QUERY_COMMIT(80),
    RECENTLY_SCREENSHOT_CAPTURED(81),
    PROMOTED_SOUND_SEARCH_CLICKED(82),
    CANCEL(83),
    REQUEST_EXECUTE_ACTION(84),
    REQUEST_CANCEL_ACTION_EXECUTION(85),
    STOP_MAGIC_MIC(86),
    SWITCH_QUERY_FOR_VOICE_CORRECTION(87),
    LOG_VOICE_CORRECTION(88),
    CORPUS_CHANGE(89),
    CLEAR_ALL_BACKGROUND_RETRY_QUERIES(90),
    DEBUG_TRIM_MEMORY(92),
    PRERENDER_AMP_VIEWER(93),
    OPEN_AMP_VIEWER(94),
    PRERENDER_AMP_DOCUMENT(338),
    OPEN_AMP_DOCUMENT(339),
    AMP_VIEWER_SUPPORT_REQUEST(95),
    SEARCH_PLATE_TEXT_SELECTED(96),
    LOG_RESHOW_CARDS_REQUEST(97),
    START_CLIENT(98),
    STOP_CLIENT(99),
    MONET_CLIENT_EVENT(100),
    SEARCH_TOOLS_DIALOG_OPTION_CLICK(103),
    SUGGEST_UI_INFO(105),
    ACTION_UI_READY(106),
    SELECT_CURRENT_DEVICE_FOR_HOTWORD_TRIGGERS(107),
    LOG_APP_ENTRY(108),
    LITESWITCH_GO_TO_ORIGINAL_CLICKED(110),
    LITESWITCH_GO_BACK_TO_LITE_CLICKED(111),
    GCM_TOPIC_SUBSCRIPTION_CHANGE(112),
    RECENTLY_ENTRIES_REQUEST_REMOVE(113),
    MONET_CLIENT_LIFECYCLE_EVENT(114),
    NEW_PERMISSIONS_GRANTED(115),
    SHARE_SCREENSHOT(116),
    EXECUTE_MEDIA(117),
    DOODLE_GCM_TOPIC_SUBSCRIPTION_CHANGE(118),
    PERSISTENT_SEARCH_NOTIFICATION_SETTING_CHANGED(119),
    SUGGESTION_LONG_CLICK(120),
    OPA_UI_MODE(121),
    REFRESH_SERVICE_STATE(122),
    BACKGROUND_RETRY_COMPLETED_QUERY_TAPPED(123),
    NEW_SHORTCUTS_AVAILABLE(124),
    BACKGROUND_RETRY_QUERY_DELETED(125),
    VOICE_ASSIST_FROM_KEYGUARD_TRIGGERED(126),
    HOTWORD_TRIGGERED_ON_DSP(128),
    VOICE_SEARCH_HANDS_FREE(129),
    DUMMY_CLIENT_EVENT(130),
    DUMMY_BACKGROUND_TASK_EVENT(131),
    LOG_TELEMETRY_TO_CLEARCUT(132),
    GENERIC_SUGGEST_EVENT(133),
    RETRY_BUTTON_TAPPED(134),
    GET_NOTIFIED_BUTTON_TAPPED(135),
    CANCEL_SEARCH_BUTTON_TAPPED(136),
    OPEN_AIRPLANE_MODE_SETTINGS_BUTTON_TAPPED(137),
    OPEN_DATETIME_SETTINGS_BUTTON_TAPPED(138),
    OPEN_DATA_USAGE_SETTINGS_BUTTON_TAPPED(306),
    OPEN_DATA_ROAMING_SETTINGS_BUTTON_TAPPED(307),
    OPEN_WIFI_SETTINGS_BUTTON_TAPPED(308),
    SIGN_IN_TO_NETWORK_BUTTON_TAPPED(139),
    CONTINUE_INSECURELY_BUTTON_TAPPED(140),
    OPEN_PERMISSIONS_DIALOG_BUTTON_TAPPED(141),
    MANAGE_PENDING_SEARCHES_BUTTON_TAPPED(142),
    OPEN_OFFLINE_SEARCH_SETTINGS_BUTTON_TAPPED(143),
    INSTALL_LANGUAGE_PACK_BUTTON_TAPPED(144),
    SEARCH_MAPS_BUTTON_TAPPED(145),
    GOOGLE_HELP_BUTTON_TAPPED(146),
    SCREEN_SEARCH(147),
    DEEPLINK(148),
    SEARCH_DEEPLINK(149),
    CACHE_BACKGROUND_RETRY(150),
    SEARCH_PLATE_MIC_TAPPED(151),
    START_PRONUNCIATION_LEARNING(152),
    CANCEL_PRONUNCIATION_LEARNING(153),
    PLAY_PRONUNCIATION_LEARNING_RESULT(154),
    OPA_INPUT_MODE(155),
    UPDATE_GSA_CONFIGS(156),
    FETCH_CONFIGS_FROM_PHENOTYPE(157),
    OFFLINE_LANDING_PAGES_ONBOARDING_ACCEPTED(158),
    START_VISUAL_SEARCH(161),
    SEARCH_PLATE_LENS_BUTTON_CLICKED(254),
    NEW_HOTWORD_MODEL_AVAILABLE(162),
    NEW_UPGRADE_HOTWORD_MODEL_AVAILABLE(295),
    NEW_SPEECH_DETECTION_MODEL_AVAILABLE(163),
    OPA_EYES_STARTED(166),
    OPA_EYES_STOPPED(167),
    OPA_EYES_PROVIDE_IMAGES_REQUEST(192),
    OPA_EYES_ACTION_INTENT(256),
    OPA_EYES_CAMERA_START(263),
    OPA_EYES_CAMERA_STOP(264),
    OPA_EYES_SCREEN_SEARCH(303),
    OPA_EYES_IMAGE_INJECT(341),
    OPA_EYES_CAMERA_ATTACH_SURFACE(265),
    OPA_EYES_FORWARDED_SERVICE_EVENT(290),
    FINGERPRINT_AUTH_CLIENT_EVENT(168),
    REAUTH_CLIENT_EVENT(169),
    MAIN_CONTENT_SCROLLED(170),
    DSP_ERROR_NOTIFICATION(171),
    OPA_CLIENT_INPUT(173),
    COMMON_BROADCAST_MESSAGE_RECEIVED(174),
    BACKGROUND_TASK_STARTED(255),
    VIEW_SAVES_CLICKED(175),
    SHOW_BACKGROUND_RETRY_DISABLE_DATA_SAVER_DIALOG(178),
    SHOW_BACKGROUND_RETRY_ENABLE_NOTIFICATIONS_DIALOG(179),
    LOG_ATTEMPTED_SEARCH(180),
    BACKGROUND_RETRY_ONBOARDING_ACCEPTED(181),
    BACKGROUND_RETRY_ONBOARDING_DECLINED(182),
    BACKGROUND_RETRY_ONBOARDING_DISMISSED(183),
    REFRESH_ZERO_PREFIX_SUGGESTIONS(184),
    CLIENT_READY(185),
    CHANGE_WERNICKE_PLAYBACK_STATE(186),
    BISTO_PRE_CACHING(187),
    OPA_CHROME_OS_MODE(188),
    BACKUP_DATA(189),
    RESTORE_DATA(190),
    START_OPA_ACTIVITY_FOR_L_ASSIST(191),
    WIDGET_INTENT_RECEIVED(194),
    REQUEST_SHORTCUTS(195),
    PROPERTIES_UPDATE_IN_PHENOTYPE(196),
    OPEN_IMAGE_SHARE_EVENT(197),
    MAY_SHOW_HATS(198),
    BISTO_DEVICE_INFO_PUBLISH(199),
    BISTO_SYNC_CONFIG(200),
    PROACTIVE_REQUEST_DRAWER_STATE(294),
    PROACTIVE_REQUEST_CLUSTERED_CARDS(203),
    PROACTIVE_LOG_ACTION_LIST(204),
    PROACTIVE_DISMISS_ENTRY(205),
    PROACTIVE_REMOVE_GROUP_CHILD_ENTRY(206),
    PROACTIVE_REQUEST_STATIC_MAP_WITH_OPTIONS(207),
    PROACTIVE_REQUEST_RESOLVE_TRAINING_QUESTION(208),
    PROACTIVE_SET_TRAINING_ANSWER(209),
    PROACTIVE_SEND_TRAINING_ACTION(210),
    PROACTIVE_REQUEST_PHOTO_GALLERY_INTENT(211),
    PROACTIVE_SNOOZE_REMINDER(212),
    PROACTIVE_REQUEST_IS_REMINDER_SMART_ACTION_SUPPORTED(213),
    PROACTIVE_INVALIDATE_ENTRIES(214),
    PROACTIVE_REQUEST_TRANSLATE_IN_PLACE(215),
    PROACTIVE_REQUEST_ENABLE_SEARCH_HISTORY_FOR_ACTIVE_ACCOUNT(216),
    PROACTIVE_DELETE_NOTIFICATIONS_FOR_ENTRY(218),
    PROACTIVE_RECORD_PREDICTIVE_INTERACTION(219),
    PROACTIVE_RECORD_EXECUTED_USER_ACTIONS(220),
    PROACTIVE_REFRESH_ENTRIES_AND_LOG(221),
    PROACTIVE_FRESHEN_ENTRIES(222),
    PROACTIVE_REFRESH_FROM_CARD_SELECTOR(223),
    PROACTIVE_REQUEST_HELP_INTENT(224),
    PROACTIVE_RECORD_FEEDBACK_PROMPT_ACTION(225),
    PROACTIVE_QUEUE_DISMISS_ENTRY_ACTION(226),
    PROACTIVE_REQUEST_CREATE_CALENDAR_EVENT(227),
    PROACTIVE_REQUEST_CURRENT_ACCOUNT(228),
    PROACTIVE_MAYBE_SHOW_LOCATION_PERMISSION_NOTIFICATION(230),
    PROACTIVE_HANDLE_CONTAINER_VISIBILTY_CHANGE(231),
    PROACTIVE_HANDLE_RENDERING_STOPPED(232),
    PROACTIVE_REQUEST_LOCATION_SETTINGS_RESOLUTION(233),
    PROACTIVE_NON_BLOCKING_FETCH(235),
    PROACTIVE_REQUEST_SHARABLE_SHORT_URL(236),
    PROACTIVE_START_EVENT(237),
    PROACTIVE_ADD_ENDSTATE_EXTRA_INFO(304),
    PROACTIVE_LOG_ENDSTATE(238),
    PROACTIVE_CREATE_ENDSTATE(239),
    PROACTIVE_DELETE_ENDSTATE(240),
    PROACTIVE_LOG_ENDSTATES(241),
    PROACTIVE_MARK_ALL_CARDS_RENDERED(242),
    PROACTIVE_MARK_ALL_CARDS_RENDERED_NON_TAB_STREAM(243),
    PROACTIVE_REQUEST_NEW_CARDS_INFO(244),
    PROACTIVE_REQUEST_NEW_CARDS_INFO_NON_TAB_STREAM(245),
    PROACTIVE_REMOVE_BOTTOM_BAR_PROMO(246),
    PROACTIVE_UPDATE_BACK_OF_CARD_FOLLOW_CLICK_FOR_ENTRY(247),
    PROACTIVE_REQUEST(248),
    PROACTIVE_NEW_ENTRY_TREE(309),
    FEEDBACK_LINK_TAPPED(249),
    TROUBLESHOOT_LINK_TAPPED(250),
    PIXEL_APPS(251),
    SEARCH_MAPS_COUNTDOWN_FINISHED(253),
    OPA_DEEPLINK(257),
    COMMON_CONTENT_PROVIDER_QUERY(258),
    COMMON_CONTENT_PROVIDER_GET_TYPE(259),
    COMMON_CONTENT_PROVIDER_INSERT(260),
    COMMON_CONTENT_PROVIDER_BULK_INSERT(270),
    COMMON_CONTENT_PROVIDER_DELETE(261),
    COMMON_CONTENT_PROVIDER_UPDATE(262),
    COMMON_CONTENT_PROVIDER_OPEN_FILE(274),
    OPA_REQUEST_SHERLOG_ENTRIES(267),
    SEARCH_MAPS_COUNTDOWN_CANCELED(268),
    OPA_ACTIVITY_LIFECYCLE_STATE(269),
    SEARCH_TAB_CLICKED(271),
    SEARCH_PLATE_MIC_TAPPED_FOR_OPA_PROMO_NOTIFICATIONS(272),
    REMOVE_QUERY_FROM_RECENTLY_HISTORY(273),
    CLEAR_ALL_STORED_SHORTCUTS_DATA(275),
    RECENTLY_DOODLE_CLICKED(276),
    LOG_APP_FLOW_EVENT(277),
    BISTO_BULK_PRE_CACHING_REQUEST(278),
    DISABLE_SPEECH_DETECTION(279),
    NOW_PREFERENCE_SELECTION(280),
    BISTO_START_ACTIVITY_REQUEST(281),
    WEBVIEW_LONG_PRESS(282),
    OPA_DISPLAY_CONTEXT_UPDATED(283),
    PIXEL_SEARCH_STARTED(284),
    BISTO_CLEAR_JSON_LD_ENCODED_SCHEMA_ENTITIES(285),
    OPA_ACTIVITY_SIZE(286),
    BURGER_CLICKED_IN_NOW(287),
    BURGER_CLICKED_IN_LOGO_HEADER(288),
    PODCAST_PLAYER_EVENT(289),
    UPDATE_TIP_ACCEPT_BUTTON_CLICK(291),
    UPDATE_TIP_REJECT_BUTTON_CLICK(292),
    SEND_GTM_EVENT_FROM_LEGACY_UI(293),
    UPDATE_TOOLTIP_SHOWN(296),
    ACETONE_LOG_VERSION(297),
    OPA_FOREGROUND_APP_UPDATED(298),
    OPA_REQUEST_MEDIA_SESSION_TOKEN(299),
    RSS_GMM_COMMUTE_UPDATE(300),
    RSS_GMM_COMMUTE_QUERY(301),
    NOW_RENDER_FINISHED(302),
    PREPARE_CCT_SESSION(160),
    PLACE_DETAILS_CLIENT_EVENT(305),
    PIXEL_HOMESCREEN_QSB_TAPPED(311),
    OPA_SOURCE_UPDATED(312),
    REGENERATE_CLIENT_OPTIN_CONTEXT_PROTO(313),
    SUGGEST_FEEDBACK_CLICK(314),
    OPA_CANCEL_CONVERSATION(315),
    FETCH_CUSTOM_LOCATION_DETAILS(316),
    SAVE_ENTRY_LOCATION(317),
    SAVE_RELATIONSHIP(318),
    OPA_REQUEST_ZERO_STATE_CONTENT(319),
    BISTO_UPDATE_CUSTOMIZATION(320),
    DEVICE_BOOT(321),
    APP_INSTALL(322),
    HTTP_EXECUTE_REQUEST_UNBUFFERED(323),
    HTTP_EXECUTE_REQUEST(326),
    HTTP_CREATE_GRPC_CHANNEL(331),
    HTTP_GET_STATISTICS(324),
    HTTP_FORCE_START_NETLOG(328),
    HTTP_CAPTURE_NETLOG(329),
    HTTP_SET_TESTING_URL_REWRITER(330),
    ASSISTANT_CLIENT_SYNC_START(325),
    RELOAD_QUERY_STATE(327),
    FETCH_SEARCH_SERVICE_BACKGROUND_TASK_NAMES(332),
    DELIVER_ACTIVE_NOTIFICATIONS(333),
    SEARCH_NOW_ACTIVITY_BOOTSTRAPPED(334),
    MAPS_NAVIGATION_STATE_UPDATE(335),
    BISTO_STARTUP_PREF_CHANGED(336),
    SCREEN_STATE_CHANGE(337),
    UPDATE_RECENTLY(340),
    GET_SETTINGS_CLIENT_EVENT(342),
    UPDATE_SETTINGS_CLIENT_EVENT(343),
    LINK_DEVICE_CLIENT_EVENT(344),
    END_SESSION(345),
    OPA_REQUEST_USER_NAME(346),
    LENS_SERVICE_WARM_UP_ACTIVITY(347),
    TARGET_LENS_SERVICE_API_VERSION(348);
    
    public static final int ACETONE_LOG_VERSION_VALUE = 297;
    public static final int ACQUIRE_KEYBOARD_FOCUS_VALUE = 36;
    public static final int ACTION_CHANGED_EXTERNALLY_VALUE = 23;
    public static final int ACTION_COUNTDOWN_CANCELED_VALUE = 34;
    public static final int ACTION_DRAG_BEGIN_VALUE = 10;
    public static final int ACTION_UI_READY_VALUE = 106;
    public static final int ACTION_USER_INTERACTION_VALUE = 38;
    public static final int ACTION_VE_LOGGING_EVENT_VALUE = 18;
    public static final int ACTIVITY_COMPLETED_WITH_RESULT_VALUE = 60;
    public static final int AMP_VIEWER_SUPPORT_REQUEST_VALUE = 95;
    public static final int APP_INSTALL_VALUE = 322;
    public static final int ASSISTANT_CLIENT_SYNC_START_VALUE = 325;
    public static final int BACKGROUND_RETRY_COMPLETED_QUERY_TAPPED_VALUE = 123;
    public static final int BACKGROUND_RETRY_ONBOARDING_ACCEPTED_VALUE = 181;
    public static final int BACKGROUND_RETRY_ONBOARDING_DECLINED_VALUE = 182;
    public static final int BACKGROUND_RETRY_ONBOARDING_DISMISSED_VALUE = 183;
    public static final int BACKGROUND_RETRY_QUERY_DELETED_VALUE = 125;
    public static final int BACKGROUND_TASK_STARTED_VALUE = 255;
    public static final int BACKUP_DATA_VALUE = 189;
    public static final int BISTO_BULK_PRE_CACHING_REQUEST_VALUE = 278;
    public static final int BISTO_CLEAR_JSON_LD_ENCODED_SCHEMA_ENTITIES_VALUE = 285;
    public static final int BISTO_DEVICE_INFO_PUBLISH_VALUE = 199;
    public static final int BISTO_PRE_CACHING_VALUE = 187;
    public static final int BISTO_STARTUP_PREF_CHANGED_VALUE = 336;
    public static final int BISTO_START_ACTIVITY_REQUEST_VALUE = 281;
    public static final int BISTO_SYNC_CONFIG_VALUE = 200;
    public static final int BISTO_UPDATE_CUSTOMIZATION_VALUE = 320;
    public static final int BROWSABLE_CATEGORY_SELECTED_VALUE = 51;
    public static final int BROWSABLE_INTENT_LAUNCHED_IN_CLIENT_VALUE = 63;
    public static final int BURGER_CLICKED_IN_LOGO_HEADER_VALUE = 288;
    public static final int BURGER_CLICKED_IN_NOW_VALUE = 287;
    public static final int CACHE_BACKGROUND_RETRY_VALUE = 150;
    public static final int CANCEL_PRONUNCIATION_LEARNING_VALUE = 153;
    public static final int CANCEL_SEARCH_BUTTON_TAPPED_VALUE = 136;
    public static final int CANCEL_VALUE = 83;
    public static final int CANVAS_RENDER_COMPLETE_VALUE = 33;
    public static final int CANVAS_WORKER_BINDER_PROVIDER_REQUEST_VALUE = 127;
    public static final int CANVAS_WORKER_BINDER_REQUEST_VALUE = 27;
    public static final int CHANGE_WERNICKE_PLAYBACK_STATE_VALUE = 186;
    public static final int CLEAR_ACTIVE_NOTIFICATIONS_VALUE = 64;
    public static final int CLEAR_ALL_BACKGROUND_RETRY_QUERIES_VALUE = 90;
    public static final int CLEAR_ALL_STORED_SHORTCUTS_DATA_VALUE = 275;
    public static final int CLEAR_VOICE_ACTIONS_VALUE = 46;
    public static final int CLIENT_READY_VALUE = 185;
    public static final int COMMON_BROADCAST_MESSAGE_RECEIVED_VALUE = 174;
    public static final int COMMON_CONTENT_PROVIDER_BULK_INSERT_VALUE = 270;
    public static final int COMMON_CONTENT_PROVIDER_DELETE_VALUE = 261;
    public static final int COMMON_CONTENT_PROVIDER_GET_TYPE_VALUE = 259;
    public static final int COMMON_CONTENT_PROVIDER_INSERT_VALUE = 260;
    public static final int COMMON_CONTENT_PROVIDER_OPEN_FILE_VALUE = 274;
    public static final int COMMON_CONTENT_PROVIDER_QUERY_VALUE = 258;
    public static final int COMMON_CONTENT_PROVIDER_UPDATE_VALUE = 262;
    public static final int CONTACT_SELECTED_EXTERNALLY_VALUE = 12;
    public static final int CONTINUE_INSECURELY_BUTTON_TAPPED_VALUE = 140;
    public static final int CORPUS_CHANGE_VALUE = 89;
    public static final int DEBUG_TRIM_MEMORY_VALUE = 92;
    public static final int DEEPLINK_VALUE = 148;
    public static final int DELIVER_ACTIVE_NOTIFICATIONS_VALUE = 333;
    public static final int DEQUEUE_QUERY_FOR_BACKGROUND_RETRY_VALUE = 55;
    public static final int DEVICE_BOOT_VALUE = 321;
    public static final int DISABLE_SPEECH_DETECTION_VALUE = 279;
    public static final int DOODLE_GCM_TOPIC_SUBSCRIPTION_CHANGE_VALUE = 118;
    public static final int DSP_ERROR_NOTIFICATION_VALUE = 171;
    public static final int DUMMY_BACKGROUND_TASK_EVENT_VALUE = 131;
    public static final int DUMMY_CLIENT_EVENT_VALUE = 130;
    public static final int END_SESSION_VALUE = 345;
    public static final int ESCAPE_HATCH_CLICKED_VALUE = 35;
    public static final int EXECUTE_MEDIA_VALUE = 117;
    public static final int EXPAND_SEARCH_PLATE_SUGGESTIONS_VALUE = 6;
    public static final int FEEDBACK_LINK_TAPPED_VALUE = 249;
    public static final int FETCH_CONFIGS_FROM_PHENOTYPE_VALUE = 157;
    public static final int FETCH_CUSTOM_LOCATION_DETAILS_VALUE = 316;
    public static final int FETCH_NATIVE_RESULT_VALUE = 29;
    public static final int FETCH_SEARCH_SERVICE_BACKGROUND_TASK_NAMES_VALUE = 332;
    public static final int FINALIZE_RESULT_ON_USER_INTERACTION_VALUE = 14;
    public static final int FINGERPRINT_AUTH_CLIENT_EVENT_VALUE = 168;
    public static final int GCM_MESSAGE_RECEIVED_VALUE = 53;
    public static final int GCM_TOPIC_SUBSCRIPTION_CHANGE_VALUE = 112;
    public static final int GEARHEAD_CONNECTED_VALUE = 65;
    public static final int GEARHEAD_DISCONNECTED_VALUE = 66;
    public static final int GENERIC_SUGGEST_EVENT_VALUE = 133;
    public static final int GET_NOTIFIED_BUTTON_TAPPED_VALUE = 135;
    public static final int GET_SETTINGS_CLIENT_EVENT_VALUE = 342;
    public static final int GOOGLE_HELP_BUTTON_TAPPED_VALUE = 146;
    public static final int GO_BACK_VALUE = 71;
    public static final int HANDLE_ACTIONS_BACK_PRESS_VALUE = 52;
    public static final int HANDLE_MANUAL_RETRY_VALUE = 62;
    public static final int HOTWORD_DETECTED_IN_INTERACTOR_VALUE = 58;
    public static final int HOTWORD_REJECTED_AFTER_DSP_TRIGGERED_VALUE = 59;
    public static final int HOTWORD_TRIGGERED_ON_DSP_VALUE = 128;
    public static final int HTTP_CAPTURE_NETLOG_VALUE = 329;
    public static final int HTTP_CREATE_GRPC_CHANNEL_VALUE = 331;
    public static final int HTTP_EXECUTE_REQUEST_UNBUFFERED_VALUE = 323;
    public static final int HTTP_EXECUTE_REQUEST_VALUE = 326;
    public static final int HTTP_FORCE_START_NETLOG_VALUE = 328;
    public static final int HTTP_GET_STATISTICS_VALUE = 324;
    public static final int HTTP_SET_TESTING_URL_REWRITER_VALUE = 330;
    public static final int INSTALL_LANGUAGE_PACK_BUTTON_TAPPED_VALUE = 144;
    public static final int LEGACY_ACTIVE_NOTIFICATIONS_VALUE = 57;
    public static final int LENS_SERVICE_WARM_UP_ACTIVITY_VALUE = 347;
    public static final int LINK_DEVICE_CLIENT_EVENT_VALUE = 344;
    public static final int LITESWITCH_GO_BACK_TO_LITE_CLICKED_VALUE = 111;
    public static final int LITESWITCH_GO_TO_ORIGINAL_CLICKED_VALUE = 110;
    public static final int LOCATION_SETTINGS_DIALOG_RESPONSE_VALUE = 7;
    public static final int LOGO_CLICKED_VALUE = 31;
    public static final int LOG_APP_ENTRY_VALUE = 108;
    public static final int LOG_APP_FLOW_EVENT_VALUE = 277;
    public static final int LOG_ATTEMPTED_SEARCH_VALUE = 180;
    public static final int LOG_RESHOW_CARDS_REQUEST_VALUE = 97;
    public static final int LOG_SUGGESTIONS_VALUE = 9;
    public static final int LOG_TELEMETRY_TO_CLEARCUT_VALUE = 132;
    public static final int LOG_VOICE_CORRECTION_VALUE = 88;
    public static final int MAIN_CONTENT_SCROLLED_VALUE = 170;
    public static final int MAIN_VIEW_TOUCHED_VALUE = 4;
    public static final int MANAGE_PENDING_SEARCHES_BUTTON_TAPPED_VALUE = 142;
    public static final int MAPS_NAVIGATION_STATE_UPDATE_VALUE = 335;
    public static final int MAY_SHOW_HATS_VALUE = 198;
    public static final int MONET_CLIENT_EVENT_VALUE = 100;
    public static final int MONET_CLIENT_LIFECYCLE_EVENT_VALUE = 114;
    public static final int NEW_ACTIVE_NOTIFICATIONS_VALUE = 32;
    public static final int NEW_HOTWORD_MODEL_AVAILABLE_VALUE = 162;
    public static final int NEW_PERMISSIONS_GRANTED_VALUE = 115;
    public static final int NEW_SHORTCUTS_AVAILABLE_VALUE = 124;
    public static final int NEW_SPEECH_DETECTION_MODEL_AVAILABLE_VALUE = 163;
    public static final int NEW_UPGRADE_HOTWORD_MODEL_AVAILABLE_VALUE = 295;
    public static final int NOW_CARDS_REFRESH_REQUEST_VALUE = 43;
    public static final int NOW_PREFERENCE_SELECTION_VALUE = 280;
    public static final int NOW_RENDER_FINISHED_VALUE = 302;
    public static final int OFFLINE_LANDING_PAGES_ONBOARDING_ACCEPTED_VALUE = 158;
    public static final int OPA_ACTIVITY_LIFECYCLE_STATE_VALUE = 269;
    public static final int OPA_ACTIVITY_SIZE_VALUE = 286;
    public static final int OPA_CANCEL_CONVERSATION_VALUE = 315;
    public static final int OPA_CHROME_OS_MODE_VALUE = 188;
    public static final int OPA_CLIENT_INPUT_VALUE = 173;
    public static final int OPA_DEEPLINK_VALUE = 257;
    public static final int OPA_DISPLAY_CONTEXT_UPDATED_VALUE = 283;
    public static final int OPA_EYES_ACTION_INTENT_VALUE = 256;
    public static final int OPA_EYES_CAMERA_ATTACH_SURFACE_VALUE = 265;
    public static final int OPA_EYES_CAMERA_START_VALUE = 263;
    public static final int OPA_EYES_CAMERA_STOP_VALUE = 264;
    public static final int OPA_EYES_FORWARDED_SERVICE_EVENT_VALUE = 290;
    public static final int OPA_EYES_IMAGE_INJECT_VALUE = 341;
    public static final int OPA_EYES_PROVIDE_IMAGES_REQUEST_VALUE = 192;
    public static final int OPA_EYES_SCREEN_SEARCH_VALUE = 303;
    public static final int OPA_EYES_STARTED_VALUE = 166;
    public static final int OPA_EYES_STOPPED_VALUE = 167;
    public static final int OPA_FOREGROUND_APP_UPDATED_VALUE = 298;
    public static final int OPA_INPUT_MODE_VALUE = 155;
    public static final int OPA_REQUEST_MEDIA_SESSION_TOKEN_VALUE = 299;
    public static final int OPA_REQUEST_SHERLOG_ENTRIES_VALUE = 267;
    public static final int OPA_REQUEST_USER_NAME_VALUE = 346;
    public static final int OPA_REQUEST_ZERO_STATE_CONTENT_VALUE = 319;
    public static final int OPA_SOURCE_UPDATED_VALUE = 312;
    public static final int OPA_UI_MODE_VALUE = 121;
    public static final int OPEN_AIRPLANE_MODE_SETTINGS_BUTTON_TAPPED_VALUE = 137;
    public static final int OPEN_AMP_DOCUMENT_VALUE = 339;
    public static final int OPEN_AMP_VIEWER_VALUE = 94;
    public static final int OPEN_DATA_ROAMING_SETTINGS_BUTTON_TAPPED_VALUE = 307;
    public static final int OPEN_DATA_USAGE_SETTINGS_BUTTON_TAPPED_VALUE = 306;
    public static final int OPEN_DATETIME_SETTINGS_BUTTON_TAPPED_VALUE = 138;
    public static final int OPEN_DOODLE_VALUE = 16;
    public static final int OPEN_IMAGE_SHARE_EVENT_VALUE = 197;
    public static final int OPEN_OFFLINE_SEARCH_SETTINGS_BUTTON_TAPPED_VALUE = 143;
    public static final int OPEN_PERMISSIONS_DIALOG_BUTTON_TAPPED_VALUE = 141;
    public static final int OPEN_WIFI_SETTINGS_BUTTON_TAPPED_VALUE = 308;
    public static final int OPT_IN_REQUEST_VALUE = 54;
    public static final int PADDING_CHANGED_VALUE = 3;
    public static final int PERSISTENT_SEARCH_NOTIFICATION_SETTING_CHANGED_VALUE = 119;
    public static final int PIXEL_APPS_VALUE = 251;
    public static final int PIXEL_HOMESCREEN_QSB_TAPPED_VALUE = 311;
    public static final int PIXEL_SEARCH_STARTED_VALUE = 284;
    public static final int PLACE_DETAILS_CLIENT_EVENT_VALUE = 305;
    public static final int PLAY_PRONUNCIATION_LEARNING_RESULT_VALUE = 154;
    public static final int PODCAST_PLAYER_EVENT_VALUE = 289;
    public static final int PREPARE_CCT_SESSION_VALUE = 160;
    public static final int PRERENDER_AMP_DOCUMENT_VALUE = 338;
    public static final int PRERENDER_AMP_VIEWER_VALUE = 93;
    public static final int PROACTIVE_ADD_ENDSTATE_EXTRA_INFO_VALUE = 304;
    public static final int PROACTIVE_CREATE_ENDSTATE_VALUE = 239;
    public static final int PROACTIVE_DELETE_ENDSTATE_VALUE = 240;
    public static final int PROACTIVE_DELETE_NOTIFICATIONS_FOR_ENTRY_VALUE = 218;
    public static final int PROACTIVE_DISMISS_ENTRY_VALUE = 205;
    public static final int PROACTIVE_FRESHEN_ENTRIES_VALUE = 222;
    public static final int PROACTIVE_HANDLE_CONTAINER_VISIBILTY_CHANGE_VALUE = 231;
    public static final int PROACTIVE_HANDLE_RENDERING_STOPPED_VALUE = 232;
    public static final int PROACTIVE_INVALIDATE_ENTRIES_VALUE = 214;
    public static final int PROACTIVE_LOG_ACTION_LIST_VALUE = 204;
    public static final int PROACTIVE_LOG_ENDSTATES_VALUE = 241;
    public static final int PROACTIVE_LOG_ENDSTATE_VALUE = 238;
    public static final int PROACTIVE_MARK_ALL_CARDS_RENDERED_NON_TAB_STREAM_VALUE = 243;
    public static final int PROACTIVE_MARK_ALL_CARDS_RENDERED_VALUE = 242;
    public static final int PROACTIVE_MAYBE_SHOW_LOCATION_PERMISSION_NOTIFICATION_VALUE = 230;
    public static final int PROACTIVE_NEW_ENTRY_TREE_VALUE = 309;
    public static final int PROACTIVE_NON_BLOCKING_FETCH_VALUE = 235;
    public static final int PROACTIVE_QUEUE_DISMISS_ENTRY_ACTION_VALUE = 226;
    public static final int PROACTIVE_RECORD_EXECUTED_USER_ACTIONS_VALUE = 220;
    public static final int PROACTIVE_RECORD_FEEDBACK_PROMPT_ACTION_VALUE = 225;
    public static final int PROACTIVE_RECORD_PREDICTIVE_INTERACTION_VALUE = 219;
    public static final int PROACTIVE_REFRESH_ENTRIES_AND_LOG_VALUE = 221;
    public static final int PROACTIVE_REFRESH_FROM_CARD_SELECTOR_VALUE = 223;
    public static final int PROACTIVE_REMOVE_BOTTOM_BAR_PROMO_VALUE = 246;
    public static final int PROACTIVE_REMOVE_GROUP_CHILD_ENTRY_VALUE = 206;
    public static final int PROACTIVE_REQUEST_CLUSTERED_CARDS_VALUE = 203;
    public static final int PROACTIVE_REQUEST_CREATE_CALENDAR_EVENT_VALUE = 227;
    public static final int PROACTIVE_REQUEST_CURRENT_ACCOUNT_VALUE = 228;
    public static final int PROACTIVE_REQUEST_DRAWER_STATE_VALUE = 294;
    public static final int PROACTIVE_REQUEST_ENABLE_SEARCH_HISTORY_FOR_ACTIVE_ACCOUNT_VALUE = 216;
    public static final int PROACTIVE_REQUEST_HELP_INTENT_VALUE = 224;
    public static final int PROACTIVE_REQUEST_IS_REMINDER_SMART_ACTION_SUPPORTED_VALUE = 213;
    public static final int PROACTIVE_REQUEST_LOCATION_SETTINGS_RESOLUTION_VALUE = 233;
    public static final int PROACTIVE_REQUEST_NEW_CARDS_INFO_NON_TAB_STREAM_VALUE = 245;
    public static final int PROACTIVE_REQUEST_NEW_CARDS_INFO_VALUE = 244;
    public static final int PROACTIVE_REQUEST_PHOTO_GALLERY_INTENT_VALUE = 211;
    public static final int PROACTIVE_REQUEST_RESOLVE_TRAINING_QUESTION_VALUE = 208;
    public static final int PROACTIVE_REQUEST_SHARABLE_SHORT_URL_VALUE = 236;
    public static final int PROACTIVE_REQUEST_STATIC_MAP_WITH_OPTIONS_VALUE = 207;
    public static final int PROACTIVE_REQUEST_TRANSLATE_IN_PLACE_VALUE = 215;
    public static final int PROACTIVE_REQUEST_VALUE = 248;
    public static final int PROACTIVE_SEND_TRAINING_ACTION_VALUE = 210;
    public static final int PROACTIVE_SET_TRAINING_ANSWER_VALUE = 209;
    public static final int PROACTIVE_SNOOZE_REMINDER_VALUE = 212;
    public static final int PROACTIVE_START_EVENT_VALUE = 237;
    public static final int PROACTIVE_UPDATE_BACK_OF_CARD_FOLLOW_CLICK_FOR_ENTRY_VALUE = 247;
    public static final int PROMOTED_SOUND_SEARCH_CLICKED_VALUE = 82;
    public static final int PROPERTIES_UPDATE_IN_PHENOTYPE_VALUE = 196;
    public static final int QUERY_COMMIT_VALUE = 80;
    public static final int QUERY_EDIT_VALUE = 79;
    public static final int QUERY_SET_VALUE = 78;
    public static final int QUEUE_QUERY_FOR_BACKGROUND_RETRY_VALUE = 40;
    public static final int REAUTH_CLIENT_EVENT_VALUE = 169;
    public static final int RECENTLY_DOODLE_CLICKED_VALUE = 276;
    public static final int RECENTLY_ENTRIES_REQUEST_REMOVE_VALUE = 113;
    public static final int RECENTLY_SCREENSHOT_CAPTURED_VALUE = 81;
    public static final int REFRESH_DOODLE_DATA_VALUE = 5;
    public static final int REFRESH_SERVICE_STATE_VALUE = 122;
    public static final int REFRESH_ZERO_PREFIX_SUGGESTIONS_VALUE = 184;
    public static final int REGENERATE_CLIENT_OPTIN_CONTEXT_PROTO_VALUE = 313;
    public static final int RELEASE_KEYBOARD_FOCUS_VALUE = 37;
    public static final int RELOAD_QUERY_STATE_VALUE = 327;
    public static final int REMOTE_ACTION_TRIGGERED_VALUE = 68;
    public static final int REMOVE_QUERY_FROM_RECENTLY_HISTORY_VALUE = 273;
    public static final int REMOVE_SUGGESTION_VALUE = 25;
    public static final int REQUEST_CANCEL_ACTION_EXECUTION_VALUE = 85;
    public static final int REQUEST_EXECUTE_ACTION_VALUE = 84;
    public static final int REQUEST_SHORTCUTS_VALUE = 195;
    public static final int RESTORE_DATA_VALUE = 190;
    public static final int RETRY_BUTTON_TAPPED_VALUE = 134;
    public static final int RSS_GMM_COMMUTE_QUERY_VALUE = 301;
    public static final int RSS_GMM_COMMUTE_UPDATE_VALUE = 300;
    public static final int SAVE_ENTRY_LOCATION_VALUE = 317;
    public static final int SAVE_RELATIONSHIP_VALUE = 318;
    public static final int SAVE_USER_INTERACTION_VALUE = 39;
    public static final int SCREEN_SEARCH_VALUE = 147;
    public static final int SCREEN_STATE_CHANGE_VALUE = 337;
    public static final int SEARCH_DEEPLINK_VALUE = 149;
    public static final int SEARCH_MAPS_BUTTON_TAPPED_VALUE = 145;
    public static final int SEARCH_MAPS_COUNTDOWN_CANCELED_VALUE = 268;
    public static final int SEARCH_MAPS_COUNTDOWN_FINISHED_VALUE = 253;
    public static final int SEARCH_NOW_ACTIVITY_BOOTSTRAPPED_VALUE = 334;
    public static final int SEARCH_PLATE_LENS_BUTTON_CLICKED_VALUE = 254;
    public static final int SEARCH_PLATE_MIC_TAPPED_FOR_OPA_PROMO_NOTIFICATIONS_VALUE = 272;
    public static final int SEARCH_PLATE_MIC_TAPPED_VALUE = 151;
    public static final int SEARCH_PLATE_TEXT_SELECTED_VALUE = 96;
    public static final int SEARCH_TAB_CLICKED_VALUE = 271;
    public static final int SEARCH_TOOLS_CLEAR_BUTTON_CLICKED_VALUE = 47;
    public static final int SEARCH_TOOLS_CLICKED_VALUE = 1;
    public static final int SEARCH_TOOLS_DIALOG_OPTION_CLICK_VALUE = 103;
    public static final int SEARCH_TOOLS_OPTION_SELECTED_VALUE = 48;
    public static final int SECURITY_ICON_CLICKED_VALUE = 15;
    public static final int SELECT_CURRENT_DEVICE_FOR_HOTWORD_TRIGGERS_VALUE = 107;
    public static final int SEND_GTM_EVENT_FROM_LEGACY_UI_VALUE = 293;
    public static final int SET_BROWSER_DIMENSION_VALUE = 72;
    public static final int SET_GWS_LOGGABLE_EVENT_VALUE = 41;
    public static final int SET_HOTWORD_DETECTION_ENABLED_VALUE = 77;
    public static final int SET_SPEECH_DETECTION_ENABLED_VALUE = 76;
    public static final int SHARE_SCREENSHOT_VALUE = 116;
    public static final int SHOW_BACKGROUND_RETRY_DISABLE_DATA_SAVER_DIALOG_VALUE = 178;
    public static final int SHOW_BACKGROUND_RETRY_ENABLE_NOTIFICATIONS_DIALOG_VALUE = 179;
    public static final int SIGN_IN_TO_NETWORK_BUTTON_TAPPED_VALUE = 139;
    public static final int START_CLIENT_VALUE = 98;
    public static final int START_OPA_ACTIVITY_FOR_L_ASSIST_VALUE = 191;
    public static final int START_PRONUNCIATION_LEARNING_VALUE = 152;
    public static final int START_VISUAL_SEARCH_VALUE = 161;
    public static final int STOP_CLIENT_VALUE = 99;
    public static final int STOP_ENDLESS_MODE_ON_USER_INTERACTION_VALUE = 49;
    public static final int STOP_LISTENING_VALUE = 69;
    public static final int STOP_MAGIC_MIC_VALUE = 86;
    public static final int STOP_SPEAKING_VALUE = 70;
    public static final int SUGGESTION_ACTION_BUTTON_CLICKED_VALUE = 11;
    public static final int SUGGESTION_CLICK_VALUE = 24;
    public static final int SUGGESTION_LONG_CLICK_VALUE = 120;
    public static final int SUGGEST_FEEDBACK_CLICK_VALUE = 314;
    public static final int SUGGEST_RESPONSE_RENDERED_VALUE = 17;
    public static final int SUGGEST_UI_INFO_VALUE = 105;
    public static final int SUPPRESS_GWS_LOGGABLE_EVENT_VALUE = 42;
    public static final int SWITCH_QUERY_FOR_VOICE_CORRECTION_VALUE = 87;
    public static final int TARGET_LENS_SERVICE_API_VERSION_VALUE = 348;
    public static final int TIMESTAMP_UI_GET_LATEST_CLICKED_VALUE = 67;
    public static final int TROUBLESHOOT_LINK_TAPPED_VALUE = 250;
    public static final int TTS_PLAYBACK_COMPLETE_VALUE = 61;
    public static final int TTS_STOP_SPEAKING_VALUE = 73;
    public static final int UNDOABLE_INTERACTION_NOT_HANDLED_VALUE = 44;
    public static final int UNKNOWN_VALUE = 0;
    public static final int UNMUTE_AFFORDANCE_CLICKED_VALUE = 45;
    public static final int UNSUPPRESS_CORPORA_VALUE = 75;
    public static final int UPDATE_GSA_CONFIGS_VALUE = 156;
    public static final int UPDATE_RECENTLY_VALUE = 340;
    public static final int UPDATE_SETTINGS_CLIENT_EVENT_VALUE = 343;
    public static final int UPDATE_TIP_ACCEPT_BUTTON_CLICK_VALUE = 291;
    public static final int UPDATE_TIP_REJECT_BUTTON_CLICK_VALUE = 292;
    public static final int UPDATE_TOOLTIP_SHOWN_VALUE = 296;
    public static final int VIEW_SAVES_CLICKED_VALUE = 175;
    public static final int VOICE_ASSIST_FROM_KEYGUARD_TRIGGERED_VALUE = 126;
    public static final int VOICE_SEARCH_HANDS_FREE_VALUE = 129;
    public static final int WEBVIEW_LONG_PRESS_VALUE = 282;
    public static final int WEBVIEW_VISIBLE_VALUE = 74;
    public static final int WIDGET_INTENT_RECEIVED_VALUE = 194;
    public static final int WINDOW_FOCUS_CHANGED_VALUE = 2;
    private static final Internal.EnumLiteMap<ClientEventId> internalValueMap = new Internal.EnumLiteMap<ClientEventId>() {
        /* class com.google.android.apps.gsa.search.shared.service.proto.ClientEventId.AnonymousClass1 */

        public ClientEventId findValueByNumber(int i) {
            return ClientEventId.forNumber(i);
        }
    };
    private final int value;

    private ClientEventId(int i) {
        this.value = i;
    }

    public static ClientEventId forNumber(int i) {
        switch (i) {
            case 0:
                return UNKNOWN;
            case 1:
                return SEARCH_TOOLS_CLICKED;
            case 2:
                return WINDOW_FOCUS_CHANGED;
            case 3:
                return PADDING_CHANGED;
            case 4:
                return MAIN_VIEW_TOUCHED;
            case 5:
                return REFRESH_DOODLE_DATA;
            case 6:
                return EXPAND_SEARCH_PLATE_SUGGESTIONS;
            case 7:
                return LOCATION_SETTINGS_DIALOG_RESPONSE;
            case 8:
            case 13:
            case 19:
            case 20:
            case 21:
            case 22:
            case 26:
            case 28:
            case 30:
            case 50:
            case 56:
            case 91:
            case 101:
            case 102:
            case 104:
            case 109:
            case 159:
            case 164:
            case 165:
            case 172:
            case 176:
            case 177:
            case 193:
            case 201:
            case 202:
            case 217:
            case 229:
            case 234:
            case 252:
            case 266:
            case 310:
            default:
                return null;
            case 9:
                return LOG_SUGGESTIONS;
            case 10:
                return ACTION_DRAG_BEGIN;
            case 11:
                return SUGGESTION_ACTION_BUTTON_CLICKED;
            case 12:
                return CONTACT_SELECTED_EXTERNALLY;
            case 14:
                return FINALIZE_RESULT_ON_USER_INTERACTION;
            case 15:
                return SECURITY_ICON_CLICKED;
            case 16:
                return OPEN_DOODLE;
            case 17:
                return SUGGEST_RESPONSE_RENDERED;
            case 18:
                return ACTION_VE_LOGGING_EVENT;
            case 23:
                return ACTION_CHANGED_EXTERNALLY;
            case 24:
                return SUGGESTION_CLICK;
            case 25:
                return REMOVE_SUGGESTION;
            case 27:
                return CANVAS_WORKER_BINDER_REQUEST;
            case 29:
                return FETCH_NATIVE_RESULT;
            case 31:
                return LOGO_CLICKED;
            case 32:
                return NEW_ACTIVE_NOTIFICATIONS;
            case 33:
                return CANVAS_RENDER_COMPLETE;
            case 34:
                return ACTION_COUNTDOWN_CANCELED;
            case 35:
                return ESCAPE_HATCH_CLICKED;
            case 36:
                return ACQUIRE_KEYBOARD_FOCUS;
            case 37:
                return RELEASE_KEYBOARD_FOCUS;
            case 38:
                return ACTION_USER_INTERACTION;
            case 39:
                return SAVE_USER_INTERACTION;
            case 40:
                return QUEUE_QUERY_FOR_BACKGROUND_RETRY;
            case 41:
                return SET_GWS_LOGGABLE_EVENT;
            case 42:
                return SUPPRESS_GWS_LOGGABLE_EVENT;
            case 43:
                return NOW_CARDS_REFRESH_REQUEST;
            case 44:
                return UNDOABLE_INTERACTION_NOT_HANDLED;
            case 45:
                return UNMUTE_AFFORDANCE_CLICKED;
            case 46:
                return CLEAR_VOICE_ACTIONS;
            case 47:
                return SEARCH_TOOLS_CLEAR_BUTTON_CLICKED;
            case 48:
                return SEARCH_TOOLS_OPTION_SELECTED;
            case 49:
                return STOP_ENDLESS_MODE_ON_USER_INTERACTION;
            case 51:
                return BROWSABLE_CATEGORY_SELECTED;
            case 52:
                return HANDLE_ACTIONS_BACK_PRESS;
            case 53:
                return GCM_MESSAGE_RECEIVED;
            case 54:
                return OPT_IN_REQUEST;
            case 55:
                return DEQUEUE_QUERY_FOR_BACKGROUND_RETRY;
            case 57:
                return LEGACY_ACTIVE_NOTIFICATIONS;
            case 58:
                return HOTWORD_DETECTED_IN_INTERACTOR;
            case 59:
                return HOTWORD_REJECTED_AFTER_DSP_TRIGGERED;
            case 60:
                return ACTIVITY_COMPLETED_WITH_RESULT;
            case 61:
                return TTS_PLAYBACK_COMPLETE;
            case 62:
                return HANDLE_MANUAL_RETRY;
            case 63:
                return BROWSABLE_INTENT_LAUNCHED_IN_CLIENT;
            case 64:
                return CLEAR_ACTIVE_NOTIFICATIONS;
            case 65:
                return GEARHEAD_CONNECTED;
            case 66:
                return GEARHEAD_DISCONNECTED;
            case 67:
                return TIMESTAMP_UI_GET_LATEST_CLICKED;
            case 68:
                return REMOTE_ACTION_TRIGGERED;
            case 69:
                return STOP_LISTENING;
            case 70:
                return STOP_SPEAKING;
            case 71:
                return GO_BACK;
            case 72:
                return SET_BROWSER_DIMENSION;
            case 73:
                return TTS_STOP_SPEAKING;
            case 74:
                return WEBVIEW_VISIBLE;
            case 75:
                return UNSUPPRESS_CORPORA;
            case 76:
                return SET_SPEECH_DETECTION_ENABLED;
            case 77:
                return SET_HOTWORD_DETECTION_ENABLED;
            case 78:
                return QUERY_SET;
            case 79:
                return QUERY_EDIT;
            case 80:
                return QUERY_COMMIT;
            case 81:
                return RECENTLY_SCREENSHOT_CAPTURED;
            case 82:
                return PROMOTED_SOUND_SEARCH_CLICKED;
            case 83:
                return CANCEL;
            case 84:
                return REQUEST_EXECUTE_ACTION;
            case 85:
                return REQUEST_CANCEL_ACTION_EXECUTION;
            case 86:
                return STOP_MAGIC_MIC;
            case 87:
                return SWITCH_QUERY_FOR_VOICE_CORRECTION;
            case 88:
                return LOG_VOICE_CORRECTION;
            case 89:
                return CORPUS_CHANGE;
            case 90:
                return CLEAR_ALL_BACKGROUND_RETRY_QUERIES;
            case 92:
                return DEBUG_TRIM_MEMORY;
            case 93:
                return PRERENDER_AMP_VIEWER;
            case 94:
                return OPEN_AMP_VIEWER;
            case 95:
                return AMP_VIEWER_SUPPORT_REQUEST;
            case 96:
                return SEARCH_PLATE_TEXT_SELECTED;
            case 97:
                return LOG_RESHOW_CARDS_REQUEST;
            case 98:
                return START_CLIENT;
            case 99:
                return STOP_CLIENT;
            case 100:
                return MONET_CLIENT_EVENT;
            case 103:
                return SEARCH_TOOLS_DIALOG_OPTION_CLICK;
            case 105:
                return SUGGEST_UI_INFO;
            case 106:
                return ACTION_UI_READY;
            case 107:
                return SELECT_CURRENT_DEVICE_FOR_HOTWORD_TRIGGERS;
            case 108:
                return LOG_APP_ENTRY;
            case 110:
                return LITESWITCH_GO_TO_ORIGINAL_CLICKED;
            case 111:
                return LITESWITCH_GO_BACK_TO_LITE_CLICKED;
            case 112:
                return GCM_TOPIC_SUBSCRIPTION_CHANGE;
            case 113:
                return RECENTLY_ENTRIES_REQUEST_REMOVE;
            case 114:
                return MONET_CLIENT_LIFECYCLE_EVENT;
            case 115:
                return NEW_PERMISSIONS_GRANTED;
            case 116:
                return SHARE_SCREENSHOT;
            case 117:
                return EXECUTE_MEDIA;
            case 118:
                return DOODLE_GCM_TOPIC_SUBSCRIPTION_CHANGE;
            case 119:
                return PERSISTENT_SEARCH_NOTIFICATION_SETTING_CHANGED;
            case 120:
                return SUGGESTION_LONG_CLICK;
            case 121:
                return OPA_UI_MODE;
            case 122:
                return REFRESH_SERVICE_STATE;
            case 123:
                return BACKGROUND_RETRY_COMPLETED_QUERY_TAPPED;
            case 124:
                return NEW_SHORTCUTS_AVAILABLE;
            case 125:
                return BACKGROUND_RETRY_QUERY_DELETED;
            case 126:
                return VOICE_ASSIST_FROM_KEYGUARD_TRIGGERED;
            case 127:
                return CANVAS_WORKER_BINDER_PROVIDER_REQUEST;
            case 128:
                return HOTWORD_TRIGGERED_ON_DSP;
            case 129:
                return VOICE_SEARCH_HANDS_FREE;
            case 130:
                return DUMMY_CLIENT_EVENT;
            case 131:
                return DUMMY_BACKGROUND_TASK_EVENT;
            case 132:
                return LOG_TELEMETRY_TO_CLEARCUT;
            case 133:
                return GENERIC_SUGGEST_EVENT;
            case 134:
                return RETRY_BUTTON_TAPPED;
            case 135:
                return GET_NOTIFIED_BUTTON_TAPPED;
            case 136:
                return CANCEL_SEARCH_BUTTON_TAPPED;
            case 137:
                return OPEN_AIRPLANE_MODE_SETTINGS_BUTTON_TAPPED;
            case 138:
                return OPEN_DATETIME_SETTINGS_BUTTON_TAPPED;
            case 139:
                return SIGN_IN_TO_NETWORK_BUTTON_TAPPED;
            case 140:
                return CONTINUE_INSECURELY_BUTTON_TAPPED;
            case 141:
                return OPEN_PERMISSIONS_DIALOG_BUTTON_TAPPED;
            case 142:
                return MANAGE_PENDING_SEARCHES_BUTTON_TAPPED;
            case 143:
                return OPEN_OFFLINE_SEARCH_SETTINGS_BUTTON_TAPPED;
            case 144:
                return INSTALL_LANGUAGE_PACK_BUTTON_TAPPED;
            case 145:
                return SEARCH_MAPS_BUTTON_TAPPED;
            case 146:
                return GOOGLE_HELP_BUTTON_TAPPED;
            case 147:
                return SCREEN_SEARCH;
            case 148:
                return DEEPLINK;
            case 149:
                return SEARCH_DEEPLINK;
            case 150:
                return CACHE_BACKGROUND_RETRY;
            case 151:
                return SEARCH_PLATE_MIC_TAPPED;
            case 152:
                return START_PRONUNCIATION_LEARNING;
            case 153:
                return CANCEL_PRONUNCIATION_LEARNING;
            case 154:
                return PLAY_PRONUNCIATION_LEARNING_RESULT;
            case 155:
                return OPA_INPUT_MODE;
            case 156:
                return UPDATE_GSA_CONFIGS;
            case 157:
                return FETCH_CONFIGS_FROM_PHENOTYPE;
            case 158:
                return OFFLINE_LANDING_PAGES_ONBOARDING_ACCEPTED;
            case 160:
                return PREPARE_CCT_SESSION;
            case 161:
                return START_VISUAL_SEARCH;
            case 162:
                return NEW_HOTWORD_MODEL_AVAILABLE;
            case 163:
                return NEW_SPEECH_DETECTION_MODEL_AVAILABLE;
            case 166:
                return OPA_EYES_STARTED;
            case 167:
                return OPA_EYES_STOPPED;
            case 168:
                return FINGERPRINT_AUTH_CLIENT_EVENT;
            case 169:
                return REAUTH_CLIENT_EVENT;
            case 170:
                return MAIN_CONTENT_SCROLLED;
            case 171:
                return DSP_ERROR_NOTIFICATION;
            case 173:
                return OPA_CLIENT_INPUT;
            case 174:
                return COMMON_BROADCAST_MESSAGE_RECEIVED;
            case 175:
                return VIEW_SAVES_CLICKED;
            case 178:
                return SHOW_BACKGROUND_RETRY_DISABLE_DATA_SAVER_DIALOG;
            case 179:
                return SHOW_BACKGROUND_RETRY_ENABLE_NOTIFICATIONS_DIALOG;
            case 180:
                return LOG_ATTEMPTED_SEARCH;
            case 181:
                return BACKGROUND_RETRY_ONBOARDING_ACCEPTED;
            case 182:
                return BACKGROUND_RETRY_ONBOARDING_DECLINED;
            case 183:
                return BACKGROUND_RETRY_ONBOARDING_DISMISSED;
            case 184:
                return REFRESH_ZERO_PREFIX_SUGGESTIONS;
            case 185:
                return CLIENT_READY;
            case 186:
                return CHANGE_WERNICKE_PLAYBACK_STATE;
            case 187:
                return BISTO_PRE_CACHING;
            case 188:
                return OPA_CHROME_OS_MODE;
            case 189:
                return BACKUP_DATA;
            case 190:
                return RESTORE_DATA;
            case 191:
                return START_OPA_ACTIVITY_FOR_L_ASSIST;
            case 192:
                return OPA_EYES_PROVIDE_IMAGES_REQUEST;
            case 194:
                return WIDGET_INTENT_RECEIVED;
            case 195:
                return REQUEST_SHORTCUTS;
            case 196:
                return PROPERTIES_UPDATE_IN_PHENOTYPE;
            case 197:
                return OPEN_IMAGE_SHARE_EVENT;
            case 198:
                return MAY_SHOW_HATS;
            case 199:
                return BISTO_DEVICE_INFO_PUBLISH;
            case 200:
                return BISTO_SYNC_CONFIG;
            case 203:
                return PROACTIVE_REQUEST_CLUSTERED_CARDS;
            case 204:
                return PROACTIVE_LOG_ACTION_LIST;
            case 205:
                return PROACTIVE_DISMISS_ENTRY;
            case 206:
                return PROACTIVE_REMOVE_GROUP_CHILD_ENTRY;
            case 207:
                return PROACTIVE_REQUEST_STATIC_MAP_WITH_OPTIONS;
            case 208:
                return PROACTIVE_REQUEST_RESOLVE_TRAINING_QUESTION;
            case 209:
                return PROACTIVE_SET_TRAINING_ANSWER;
            case 210:
                return PROACTIVE_SEND_TRAINING_ACTION;
            case 211:
                return PROACTIVE_REQUEST_PHOTO_GALLERY_INTENT;
            case 212:
                return PROACTIVE_SNOOZE_REMINDER;
            case 213:
                return PROACTIVE_REQUEST_IS_REMINDER_SMART_ACTION_SUPPORTED;
            case 214:
                return PROACTIVE_INVALIDATE_ENTRIES;
            case 215:
                return PROACTIVE_REQUEST_TRANSLATE_IN_PLACE;
            case 216:
                return PROACTIVE_REQUEST_ENABLE_SEARCH_HISTORY_FOR_ACTIVE_ACCOUNT;
            case 218:
                return PROACTIVE_DELETE_NOTIFICATIONS_FOR_ENTRY;
            case 219:
                return PROACTIVE_RECORD_PREDICTIVE_INTERACTION;
            case 220:
                return PROACTIVE_RECORD_EXECUTED_USER_ACTIONS;
            case 221:
                return PROACTIVE_REFRESH_ENTRIES_AND_LOG;
            case 222:
                return PROACTIVE_FRESHEN_ENTRIES;
            case 223:
                return PROACTIVE_REFRESH_FROM_CARD_SELECTOR;
            case 224:
                return PROACTIVE_REQUEST_HELP_INTENT;
            case 225:
                return PROACTIVE_RECORD_FEEDBACK_PROMPT_ACTION;
            case 226:
                return PROACTIVE_QUEUE_DISMISS_ENTRY_ACTION;
            case 227:
                return PROACTIVE_REQUEST_CREATE_CALENDAR_EVENT;
            case 228:
                return PROACTIVE_REQUEST_CURRENT_ACCOUNT;
            case 230:
                return PROACTIVE_MAYBE_SHOW_LOCATION_PERMISSION_NOTIFICATION;
            case 231:
                return PROACTIVE_HANDLE_CONTAINER_VISIBILTY_CHANGE;
            case 232:
                return PROACTIVE_HANDLE_RENDERING_STOPPED;
            case 233:
                return PROACTIVE_REQUEST_LOCATION_SETTINGS_RESOLUTION;
            case 235:
                return PROACTIVE_NON_BLOCKING_FETCH;
            case 236:
                return PROACTIVE_REQUEST_SHARABLE_SHORT_URL;
            case 237:
                return PROACTIVE_START_EVENT;
            case 238:
                return PROACTIVE_LOG_ENDSTATE;
            case 239:
                return PROACTIVE_CREATE_ENDSTATE;
            case 240:
                return PROACTIVE_DELETE_ENDSTATE;
            case 241:
                return PROACTIVE_LOG_ENDSTATES;
            case 242:
                return PROACTIVE_MARK_ALL_CARDS_RENDERED;
            case 243:
                return PROACTIVE_MARK_ALL_CARDS_RENDERED_NON_TAB_STREAM;
            case 244:
                return PROACTIVE_REQUEST_NEW_CARDS_INFO;
            case 245:
                return PROACTIVE_REQUEST_NEW_CARDS_INFO_NON_TAB_STREAM;
            case 246:
                return PROACTIVE_REMOVE_BOTTOM_BAR_PROMO;
            case 247:
                return PROACTIVE_UPDATE_BACK_OF_CARD_FOLLOW_CLICK_FOR_ENTRY;
            case 248:
                return PROACTIVE_REQUEST;
            case 249:
                return FEEDBACK_LINK_TAPPED;
            case 250:
                return TROUBLESHOOT_LINK_TAPPED;
            case 251:
                return PIXEL_APPS;
            case 253:
                return SEARCH_MAPS_COUNTDOWN_FINISHED;
            case 254:
                return SEARCH_PLATE_LENS_BUTTON_CLICKED;
            case 255:
                return BACKGROUND_TASK_STARTED;
            case 256:
                return OPA_EYES_ACTION_INTENT;
            case 257:
                return OPA_DEEPLINK;
            case 258:
                return COMMON_CONTENT_PROVIDER_QUERY;
            case 259:
                return COMMON_CONTENT_PROVIDER_GET_TYPE;
            case 260:
                return COMMON_CONTENT_PROVIDER_INSERT;
            case 261:
                return COMMON_CONTENT_PROVIDER_DELETE;
            case 262:
                return COMMON_CONTENT_PROVIDER_UPDATE;
            case 263:
                return OPA_EYES_CAMERA_START;
            case 264:
                return OPA_EYES_CAMERA_STOP;
            case 265:
                return OPA_EYES_CAMERA_ATTACH_SURFACE;
            case 267:
                return OPA_REQUEST_SHERLOG_ENTRIES;
            case 268:
                return SEARCH_MAPS_COUNTDOWN_CANCELED;
            case 269:
                return OPA_ACTIVITY_LIFECYCLE_STATE;
            case 270:
                return COMMON_CONTENT_PROVIDER_BULK_INSERT;
            case 271:
                return SEARCH_TAB_CLICKED;
            case 272:
                return SEARCH_PLATE_MIC_TAPPED_FOR_OPA_PROMO_NOTIFICATIONS;
            case 273:
                return REMOVE_QUERY_FROM_RECENTLY_HISTORY;
            case 274:
                return COMMON_CONTENT_PROVIDER_OPEN_FILE;
            case 275:
                return CLEAR_ALL_STORED_SHORTCUTS_DATA;
            case 276:
                return RECENTLY_DOODLE_CLICKED;
            case 277:
                return LOG_APP_FLOW_EVENT;
            case 278:
                return BISTO_BULK_PRE_CACHING_REQUEST;
            case 279:
                return DISABLE_SPEECH_DETECTION;
            case 280:
                return NOW_PREFERENCE_SELECTION;
            case 281:
                return BISTO_START_ACTIVITY_REQUEST;
            case 282:
                return WEBVIEW_LONG_PRESS;
            case 283:
                return OPA_DISPLAY_CONTEXT_UPDATED;
            case 284:
                return PIXEL_SEARCH_STARTED;
            case 285:
                return BISTO_CLEAR_JSON_LD_ENCODED_SCHEMA_ENTITIES;
            case 286:
                return OPA_ACTIVITY_SIZE;
            case 287:
                return BURGER_CLICKED_IN_NOW;
            case 288:
                return BURGER_CLICKED_IN_LOGO_HEADER;
            case 289:
                return PODCAST_PLAYER_EVENT;
            case 290:
                return OPA_EYES_FORWARDED_SERVICE_EVENT;
            case 291:
                return UPDATE_TIP_ACCEPT_BUTTON_CLICK;
            case 292:
                return UPDATE_TIP_REJECT_BUTTON_CLICK;
            case 293:
                return SEND_GTM_EVENT_FROM_LEGACY_UI;
            case 294:
                return PROACTIVE_REQUEST_DRAWER_STATE;
            case 295:
                return NEW_UPGRADE_HOTWORD_MODEL_AVAILABLE;
            case 296:
                return UPDATE_TOOLTIP_SHOWN;
            case 297:
                return ACETONE_LOG_VERSION;
            case 298:
                return OPA_FOREGROUND_APP_UPDATED;
            case 299:
                return OPA_REQUEST_MEDIA_SESSION_TOKEN;
            case 300:
                return RSS_GMM_COMMUTE_UPDATE;
            case 301:
                return RSS_GMM_COMMUTE_QUERY;
            case 302:
                return NOW_RENDER_FINISHED;
            case 303:
                return OPA_EYES_SCREEN_SEARCH;
            case 304:
                return PROACTIVE_ADD_ENDSTATE_EXTRA_INFO;
            case 305:
                return PLACE_DETAILS_CLIENT_EVENT;
            case 306:
                return OPEN_DATA_USAGE_SETTINGS_BUTTON_TAPPED;
            case 307:
                return OPEN_DATA_ROAMING_SETTINGS_BUTTON_TAPPED;
            case 308:
                return OPEN_WIFI_SETTINGS_BUTTON_TAPPED;
            case 309:
                return PROACTIVE_NEW_ENTRY_TREE;
            case 311:
                return PIXEL_HOMESCREEN_QSB_TAPPED;
            case 312:
                return OPA_SOURCE_UPDATED;
            case 313:
                return REGENERATE_CLIENT_OPTIN_CONTEXT_PROTO;
            case 314:
                return SUGGEST_FEEDBACK_CLICK;
            case 315:
                return OPA_CANCEL_CONVERSATION;
            case 316:
                return FETCH_CUSTOM_LOCATION_DETAILS;
            case 317:
                return SAVE_ENTRY_LOCATION;
            case 318:
                return SAVE_RELATIONSHIP;
            case 319:
                return OPA_REQUEST_ZERO_STATE_CONTENT;
            case 320:
                return BISTO_UPDATE_CUSTOMIZATION;
            case 321:
                return DEVICE_BOOT;
            case 322:
                return APP_INSTALL;
            case 323:
                return HTTP_EXECUTE_REQUEST_UNBUFFERED;
            case 324:
                return HTTP_GET_STATISTICS;
            case 325:
                return ASSISTANT_CLIENT_SYNC_START;
            case 326:
                return HTTP_EXECUTE_REQUEST;
            case 327:
                return RELOAD_QUERY_STATE;
            case 328:
                return HTTP_FORCE_START_NETLOG;
            case 329:
                return HTTP_CAPTURE_NETLOG;
            case 330:
                return HTTP_SET_TESTING_URL_REWRITER;
            case 331:
                return HTTP_CREATE_GRPC_CHANNEL;
            case 332:
                return FETCH_SEARCH_SERVICE_BACKGROUND_TASK_NAMES;
            case 333:
                return DELIVER_ACTIVE_NOTIFICATIONS;
            case 334:
                return SEARCH_NOW_ACTIVITY_BOOTSTRAPPED;
            case 335:
                return MAPS_NAVIGATION_STATE_UPDATE;
            case 336:
                return BISTO_STARTUP_PREF_CHANGED;
            case 337:
                return SCREEN_STATE_CHANGE;
            case 338:
                return PRERENDER_AMP_DOCUMENT;
            case 339:
                return OPEN_AMP_DOCUMENT;
            case 340:
                return UPDATE_RECENTLY;
            case 341:
                return OPA_EYES_IMAGE_INJECT;
            case 342:
                return GET_SETTINGS_CLIENT_EVENT;
            case 343:
                return UPDATE_SETTINGS_CLIENT_EVENT;
            case 344:
                return LINK_DEVICE_CLIENT_EVENT;
            case 345:
                return END_SESSION;
            case 346:
                return OPA_REQUEST_USER_NAME;
            case 347:
                return LENS_SERVICE_WARM_UP_ACTIVITY;
            case 348:
                return TARGET_LENS_SERVICE_API_VERSION;
        }
    }

    public static Internal.EnumLiteMap<ClientEventId> internalGetValueMap() {
        return internalValueMap;
    }

    public final int getNumber() {
        return this.value;
    }
}
