package com.kreativeco.pjaroabarrotero.libraries;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public interface KCOAsyncResponse {
    void processFinish(ArrayList<HashMap<String, String>> output);
}
