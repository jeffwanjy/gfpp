package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 配置表
 * </p>
 *
 * @author jeff
 * @since 2019-04-22
 */
@TableName("dim_config")
public class Config extends Model<Config> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 数据源
     */
    @TableField("data_source")
    private String dataSource;
    /**
     * 关系
     */
    private String relationship;
    /**
     * sheet
     */
    @TableField("table_sheet")
    private String tableSheet;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 操作
     */
    private String operations;
    /**
     * 版本（乐观锁保留字段）
     */
    private Integer version;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getTableSheet() {
        return tableSheet;
    }

    public void setTableSheet(String tableSheet) {
        this.tableSheet = tableSheet;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Config{" +
        ", id=" + id +
        ", dataSource=" + dataSource +
        ", relationship=" + relationship +
        ", tableSheet=" + tableSheet +
        ", status=" + status +
        ", operations=" + operations +
        ", version=" + version +
        "}";
    }
}
