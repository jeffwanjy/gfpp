/**
 * 报表一管理初始化
 */
var ReportOne = {
    id: "ReportOneTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ReportOne.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'num', visible: true, align: 'center', valign: 'middle'},
            {title: '简称', field: 'simplename', visible: true, align: 'center', valign: 'middle'},
            {title: '全称', field: 'fullname', visible: true, align: 'center', valign: 'middle'},
            {title: '提示', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '版本（乐观锁保留字段）', field: 'version', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ReportOne.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ReportOne.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加报表一
 */
ReportOne.openAddReportOne = function () {
    var index = layer.open({
        type: 2,
        title: '添加报表一',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/reportOne/reportOne_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看报表一详情
 */
ReportOne.openReportOneDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '报表一详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/reportOne/reportOne_update/' + ReportOne.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除报表一
 */
ReportOne.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/reportOne/delete", function (data) {
            Feng.success("删除成功!");
            ReportOne.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("reportOneId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询报表一列表
 */
ReportOne.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ReportOne.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ReportOne.initColumn();
    var table = new BSTable(ReportOne.id, "/reportOne/list", defaultColunms);
    table.setPaginationType("client");
    ReportOne.table = table.init();
});
