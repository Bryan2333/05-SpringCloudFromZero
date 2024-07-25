package com.bryan.cloud.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 表名：t_account
*/
@Table(name = "t_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    /**
     * ID
     * -- GETTER --
     *  获取ID
     *
     *
     * -- SETTER --
     *  设置ID
     *
     @return id - ID
      * @param id ID

     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户ID
     * -- GETTER --
     *  获取用户ID
     *
     *
     * -- SETTER --
     *  设置用户ID
     *
     @return userId - 用户ID
      * @param userId 用户ID

     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 总额度
     * -- GETTER --
     *  获取总额度
     *
     *
     * -- SETTER --
     *  设置总额度
     *
     @return total - 总额度
      * @param total 总额度

     */
    private Long total;

    /**
     * 已用额度
     * -- GETTER --
     *  获取已用额度
     *
     *
     * -- SETTER --
     *  设置已用额度
     *
     @return used - 已用额度
      * @param used 已用额度

     */
    private Long used;

    /**
     * 剩余额度
     * -- GETTER --
     *  获取剩余额度
     *
     *
     * -- SETTER --
     *  设置剩余额度
     *
     @return residue - 剩余额度
      * @param residue 剩余额度

     */
    private Long residue;

}
