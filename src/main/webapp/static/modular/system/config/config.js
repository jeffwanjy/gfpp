/**
 * 配置表管理初始化
 */
var Config = {
    id: "ConfigTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Config.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '数据源', field: 'dataSource', visible: true, align: 'center', valign: 'middle'},
            {title: '关系', field: 'relationship', visible: true, align: 'center', valign: 'middle'},
            {title: 'sheet', field: 'tableSheet', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '操作', field: 'operations', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Config.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Config.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加配置表
 */
Config.openAddConfig = function () {
    var index = layer.open({
        type: 2,
        title: '添加配置表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/config/config_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看配置表详情
 */
Config.openConfigDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '配置表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/config/config_update/' + Config.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除配置表
 */
Config.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/config/delete", function (data) {
            Feng.success("删除成功!");
            Config.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("configId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询配置表列表
 */
Config.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Config.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Config.initColumn();
    var table = new BSTable(Config.id, "/config/list", defaultColunms);
    table.setPaginationType("client");
    Config.table = table.init();
});
