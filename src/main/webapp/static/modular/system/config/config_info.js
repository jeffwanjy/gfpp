/**
 * 初始化上传管理详情对话框
 */
var ConfigInfoDlg = {
    configInfoData : {}
};

/**
 * 清除数据
 */
ConfigInfoDlg.clearData = function() {
    this.configInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ConfigInfoDlg.set = function(key, val) {
    this.configInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ConfigInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ConfigInfoDlg.close = function() {
    parent.layer.close(window.parent.Config.layerIndex);
}

/**
 * 收集数据
 */
ConfigInfoDlg.collectData = function() {
    this
    .set('id')
    .set('tips')
    .set('code');
}

/**
 * 提交添加
 */
ConfigInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/config/add", function(data){
        Feng.success("添加成功!");
        window.parent.Config.table.refresh();
        ConfigInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.configInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ConfigInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/config/update", function(data){
        Feng.success("修改成功!");
        window.parent.Config.table.refresh();
        ConfigInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.configInfoData);
    ajax.start();
}

$(function() {

});
