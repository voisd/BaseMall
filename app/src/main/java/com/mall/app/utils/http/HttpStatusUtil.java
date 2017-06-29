package com.mall.app.utils.http;


import com.mall.app.utils.StringHelper;

import org.json.JSONObject;

/**
 */
public class HttpStatusUtil {

    // 得到状态码
    public static boolean getStatus(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int statusCode = jsonObject.getInt("code");

            if (statusCode == 2000) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }

    /**
     * 得到状态提示
     *
     * @param json
     * @return
     */
    public static String getStatusMsg(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String message = jsonObject.getString("msg");

            if (!StringHelper.isEmpty(message)) {
                return message;
            }else {
                return json;
            }
        } catch (Exception ex) {

            return json;
        }
    }

    /**
     * 得到状态异常码
     *
     * @param json
     * @return
     */
    public static int getStatusError(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            int error = jsonObject.getInt("code");

            return error;
        } catch (Exception ex) {

            return 0;
        }
    }

    /**
     * 判断状态是否需要重新登录
     * @param json
     * @return
     */
    public static boolean isRelogin(String json) {

        int error = getStatusError(json);

        if (error == 5000) {

            return true;
        }

        return false;
    }
}
