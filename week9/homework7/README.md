## 参考来源
https://blog.csdn.net/dhaibo1986/article/details/120762237



## 数据库

### 2.1 账户相关的表

分别设计了两张表：
T_BANK_ACCOUNT 账户表
T_BANK_FREEZE 冻结表



- T_BANK_ACCOUNT

  - ```sql
    SET FOREIGN_KEY_CHECKS=0;
    
    -- ----------------------------
    -- Table structure for t_bank_account
    -- ----------------------------
    DROP TABLE IF EXISTS `t_bank_account`;
    CREATE TABLE `t_bank_account` (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自增列',
      `customer_id` int(11) NOT NULL COMMENT '用户编号',
      `account_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '账户类型：1 人民币账户，2 美元账户',
      `balance` bigint(20) NOT NULL COMMENT '客户余额 单位 分',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户注册时间',
      `is_validate` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据是否有效标识：1有效数据，2 无效数据',
      `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
    ```

- T_BANK_FREEZE

  - ```sql
    SET FOREIGN_KEY_CHECKS=0;
    
    -- ----------------------------
    -- Table structure for t_bank_freeze
    -- ----------------------------
    DROP TABLE IF EXISTS `t_bank_freeze`;
    CREATE TABLE `t_bank_freeze` (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自增列',
      `customer_id` int(11) NOT NULL COMMENT '用户编号',
      `account_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '账户类型：1 人民币账户，2 美元账户',
      `amount` bigint(20) NOT NULL COMMENT '客户余额 单位 分',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `is_validate` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据是否有效标识：1有效数据，2 无效数据',
      `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
    ```

    

### 2.2 TCC 相关的表

```sql
DROP TABLE IF EXISTS `t_try_log`;
CREATE TABLE `t_try_log` (
  `tx_no` varchar(64) NOT NULL COMMENT '事务id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_confirm_log`;
CREATE TABLE `t_confirm_log` (
                                 `tx_no` varchar(64) NOT NULL COMMENT '事务id',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`tx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_cancel_log`;
CREATE TABLE `t_cancel_log` (
                                `tx_no` varchar(64) NOT NULL COMMENT '事务id',
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (`tx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```








### project 规划

| module              | 描述                              |
| ------------------- | --------------------------------- |
| bank-transfer-api   | dubbo的API                        |
| bank-transfer-orm   | 数据库的orm包，采用fluent自动生成 |
| bank-transfer-bank1 | bank1服务，发起转账的服务         |
| bank-transfer-bank2 | bank2服务，接受转账的服务         |
| bank-transfer-client | 客户端         |

