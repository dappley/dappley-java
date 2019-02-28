package com.dappley.android.util;

public class Constant {
    public static final int PAGE_SIZE = 10;
    public static final int REQ_PERM_STORAGE = 10001;
    public static final int REQ_PERM_CAMERA = 10002;
    public static final int REQ_ACT_QR_CODE = 10002;
    public static final int REQ_ACT_TRANSFER = 30001;
    public static final int REQ_ACT_CONVERT_SELECT = 30002;
    public static final int REQ_ACT_MODIFY_PASSWORD = 30003;

    public static final int MSG_HOME_BALANCE = 20001;
    public static final int MSG_HOME_BALANCE_ERROR = 20004;
    public static final int MSG_HOME_BALANCE_BREAK = 20014;
    public static final int MSG_WALLET_DETAIL = 20002;
    public static final int MSG_WALLET_DETAIL_ERROR = 20011;
    public static final int MSG_WALLET_DETAIL_REFRESH = 20013;
    public static final int MSG_TRANSFER_BALANCE = 20003;
    public static final int MSG_TRANSFER_BALANCE_ERROR = 20012;
    public static final int MSG_TRANSFER_FINISH = 20007;
    public static final int MSG_STEP_UPDATE = 20005;
    public static final int MSG_STEP_YESTERDAY = 20006;
    public static final int MSG_WALLET_SELECT_LIST = 20008;
    public static final int MSG_WALLET_SELECT_ERROR = 20009;
    public static final int MSG_CONVERT_FINISH = 20010;
    public static final int MSG_STEP_ALL = 20015;
    public static final int MSG_STEP_REFRESH = 20018;
    public static final int MSG_GOOGLE_LOGIN = 20016;
    public static final int MSG_GOOGLE_PERMISSION = 20017;
    public static final int MSG_GOOGLE_FAILED = 20019;

    public static final int REQ_WALLET_IMPORT = 2;

    public static final String BROAD_WALLET_LIST_UPDATE = "30001";

    public static final String PREF_CONVERTED_DAY = "converted-day";
    public static final String PREF_NATIVE_STEP = "isNativeStep";

    public static final int TYPE_APP_COUNTER = 1;
    public static final int TYPE_GOOGLE_COUNTER = 2;

    public static final int QR_WIDTH = 200;
    public static final int QR_HEIGHT = 200;

    //    public static final String ADDRESS_STEP_CONTRACT = "ccSAAixLKaM2wn6T6qr7JUPhZCLp3oUvE1";
    public static final String ADDRESS_STEP_CONTRACT = "cXuhH7BZKHuMAGtLkqyzGZWSBBSWqm19KY";

    public static final String STEP_CONTRACT = "{\"function\":\"record\",\"args\":[\"%s\",\"%d\"]}";

    public static final String URL_CONTRACT_BASE = "http://test.dappworks.xyz/";
//    public static final String URL_CONTRACT_ADDRESS = URL_CONTRACT_BASE + "contract_addr/record";
    public static final String URL_CONTRACT_ADDRESS = URL_CONTRACT_BASE + "contract_addr/record2";
}
