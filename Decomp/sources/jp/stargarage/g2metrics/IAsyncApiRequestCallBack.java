package jp.stargarage.g2metrics;

interface IAsyncApiRequestCallBack {
    void onFailure(Integer num);

    void onSuccess(ApiEntityBase apiEntityBase);
}
