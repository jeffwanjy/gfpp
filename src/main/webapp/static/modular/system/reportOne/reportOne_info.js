/**
 * 初始化报表一详情对话框
 */
var ReportOneInfoDlg = {
    reportOneInfoData : {}
};

/**
 * 清除数据
 */
ReportOneInfoDlg.clearData = function() {
    this.reportOneInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ReportOneInfoDlg.set = function(key, val) {
    this.reportOneInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ReportOneInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ReportOneInfoDlg.close = function() {
    parent.layer.close(window.parent.ReportOne.layerIndex);
}

/**
 * 收集数据
 */
ReportOneInfoDlg.collectData = function() {
    this
    .set('id')
    .set('num')
    .set('simplename')
    .set('fullname')
    .set('tips')
    .set('version');
}

/**
 * 提交添加
 */
ReportOneInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/reportOne/add", function(data){
        Feng.success("添加成功!");
        window.parent.ReportOne.table.refresh();
        ReportOneInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.reportOneInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ReportOneInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/reportOne/update", function(data){
        Feng.success("修改成功!");
        window.parent.ReportOne.table.refresh();
        ReportOneInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.reportOneInfoData);
    ajax.start();
}

$(function() {

});
