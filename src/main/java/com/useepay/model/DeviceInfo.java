package com.useepay.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * @BelongsProject: useepay-java
 * @Author: zhi.wang
 * @CreateTime: 2023-09-26  17:44
 * @Description: 设备信息
 * @Version: 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeviceInfo extends UseePayObject{
    /**
     * max=128 设备指纹 ID
     */
    @Length(max = 128,message = "设备指纹[fingerPrintId]不能多于128位")
    private String fingerPrintId;
    /**
     * max=64 用交易设备 mac 地址
     */
    @Length(max = 64,message = "用交易设备mac不能多于128位")
    private String mac;
}
