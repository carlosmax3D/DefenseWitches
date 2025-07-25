package jp.co.voyagegroup.android.fluct.jar.util;

import android.content.Context;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctAd;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctConversionEntity;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting;
import jp.co.voyagegroup.android.fluct.jar.web.FluctHttpAccess;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FluctXMLParser {
    private static final String TAG = "FluctXMLParser";

    public static Document executeUrl(Context context, String str) {
        Log.d(TAG, "executeUrl : url is " + str);
        if (!FluctUtils.checkPermission(context)) {
            Log.e(TAG, "executeUrl : not set permission");
            return null;
        } else if (FluctUtils.isNetWorkAvailable(context)) {
            return FluctHttpAccess.getDocument(str);
        } else {
            Log.e(TAG, "executeUrl : network not available");
            return null;
        }
    }

    public static FluctSetting parserConfig(Context context, Document document, String str) {
        Log.d(TAG, "parserConfig : ");
        if (document == null) {
            Log.e(TAG, "parserConfig : configDocument is null");
            return null;
        }
        FluctSetting fluctSetting = new FluctSetting();
        fluctSetting.setFluctInterstitial((FluctInterstitialTable) null);
        fluctSetting.setFluctAd(new FluctAd());
        NodeList childNodes = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (!(item == null || item.getFirstChild() == null)) {
                setNode(context, item, fluctSetting, str);
            }
        }
        return fluctSetting;
    }

    private static void setAnimationNode(Node node, FluctSetting fluctSetting) {
        Log.d(TAG, "setAnimationNode : ");
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == 1 && item.getNodeName().equals(TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE)) {
                ArrayList arrayList = new ArrayList();
                if (item.getFirstChild() != null) {
                    String[] split = item.getFirstChild().getNodeValue().split(",");
                    for (String split2 : split) {
                        String[] split3 = split2.split(":");
                        if (split3.length == 2 && Integer.parseInt(split3[1]) != 0) {
                            switch (Integer.parseInt(split3[0])) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    arrayList.add(new FluctSetting.Animation(Integer.parseInt(split3[0]), Integer.parseInt(split3[1])));
                                    break;
                            }
                        }
                    }
                }
                fluctSetting.setAnimations(arrayList);
            }
        }
    }

    private static void setConversionNode(Node node, FluctSetting fluctSetting) {
        String nodeValue;
        String nodeValue2;
        Log.d(TAG, "setConversionNode : ");
        FluctConversionEntity fluctConversionEntity = new FluctConversionEntity();
        ArrayList arrayList = new ArrayList();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeName().equals("url")) {
                if (!(childNodes.item(i).getFirstChild() == null || (nodeValue2 = childNodes.item(i).getFirstChild().getNodeValue()) == null)) {
                    Log.v(TAG, "setConversionNode : convurl is " + nodeValue2);
                    arrayList.add(nodeValue2);
                }
            } else if (!(!childNodes.item(i).getNodeName().equals(FluctConstants.XML_NODE_CONV_BROWSER) || childNodes.item(i).getFirstChild() == null || (nodeValue = childNodes.item(i).getFirstChild().getNodeValue()) == null)) {
                Log.v(TAG, "setConversionNode : browserOpenUrl is " + nodeValue);
                fluctConversionEntity.setBrowserOpenUrl(nodeValue);
            }
        }
        fluctConversionEntity.setConvUrl(arrayList);
        fluctSetting.setFluctConversion(fluctConversionEntity);
    }

    private static void setErrorNode(Node node, FluctSetting fluctSetting) {
        Log.d(TAG, "setErrorNode : ");
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeName().equals("message")) {
                String nodeValue = childNodes.item(i).getFirstChild().getNodeValue();
                Log.v(TAG, "setErrorNode : error is " + nodeValue);
                fluctSetting.setErrorMessages(nodeValue);
            }
        }
    }

    private static void setInterStital(Context context, Node node, FluctSetting fluctSetting, String str) {
        Log.d(TAG, "setInterStital : ");
        NodeList childNodes = node.getChildNodes();
        FluctInterstitialTable fluctInterstitialTable = new FluctInterstitialTable();
        fluctInterstitialTable.setMediaId(str);
        fluctInterstitialTable.setUpdateTime(FluctUtils.getCurrentTimeSec());
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeName().equals(FluctConstants.XML_NODE_DISPLAY_RATE)) {
                if (item.getFirstChild() != null) {
                    fluctInterstitialTable.setRate(Integer.valueOf(item.getFirstChild().getNodeValue()).intValue());
                }
            } else if (item.getNodeName().equals(FluctConstants.XML_NODE_WIDTH)) {
                if (item.getFirstChild() != null) {
                    fluctInterstitialTable.setWidth(Integer.valueOf(item.getFirstChild().getNodeValue()).intValue());
                }
            } else if (item.getNodeName().equals(FluctConstants.XML_NODE_HEIGHT)) {
                if (item.getFirstChild() != null) {
                    fluctInterstitialTable.setHeight(Integer.valueOf(item.getFirstChild().getNodeValue()).intValue());
                }
            } else if (item.getNodeName().equals(FluctConstants.XML_NODE_ADHTML) && item.getFirstChild() != null) {
                fluctInterstitialTable.setAdHtml(item.getFirstChild().getNodeValue());
            }
        }
        fluctSetting.setFluctInterstitial(fluctInterstitialTable);
    }

    private static void setNode(Context context, Node node, FluctSetting fluctSetting, String str) {
        Log.d(TAG, "setNode : ");
        String nodeName = node.getNodeName();
        String nodeValue = node.getFirstChild().getNodeValue();
        if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_MODE)) {
            Log.v(TAG, "setNode : mode is " + nodeValue);
            fluctSetting.setMode(nodeValue);
        } else if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_BROWSER)) {
            Log.v(TAG, "setNode : browser is " + nodeValue);
            try {
                fluctSetting.setBrowser(Integer.parseInt(nodeValue));
            } catch (NumberFormatException e) {
                Log.e(TAG, "setNode : NumberFormatException is " + e.getLocalizedMessage());
            }
        } else if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_REFRESHTIME)) {
            Log.v(TAG, "setNode : refresh time is " + nodeValue);
            try {
                fluctSetting.setRefreshTime(Long.parseLong(nodeValue));
            } catch (NumberFormatException e2) {
                Log.e(TAG, "setNode : NumberFormatException is " + e2.getLocalizedMessage());
            }
        } else if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_LOADTIME)) {
            Log.v(TAG, "setNode : load time is " + nodeValue);
            try {
                fluctSetting.setLoadTime(Long.parseLong(nodeValue));
            } catch (NumberFormatException e3) {
                Log.e(TAG, "setNode : NumberFormatException is " + e3.getLocalizedMessage());
            }
        } else if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_BACKCOLOR)) {
            Log.v(TAG, "setNode : backColor is " + nodeValue);
            fluctSetting.getFluctAd().setBackColor(nodeValue);
        } else if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_ADHTML)) {
            Log.v(TAG, "setNode : adhtml is " + nodeValue);
            fluctSetting.getFluctAd().setAdHtml(nodeValue);
        } else if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_CONVERSION_URL)) {
            setConversionNode(node, fluctSetting);
        } else if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_UA)) {
            fluctSetting.setUserAgent(nodeValue);
        } else if (nodeName.equalsIgnoreCase(FluctConstants.XML_NODE_ANIMATIONS)) {
            setAnimationNode(node, fluctSetting);
        } else if (nodeName.equalsIgnoreCase("interstitial")) {
            setInterStital(context, node, fluctSetting, str);
        } else if (nodeName.equalsIgnoreCase("error")) {
            setErrorNode(node, fluctSetting);
            Log.d(TAG, "setNode : Error" + nodeValue);
        }
    }
}
