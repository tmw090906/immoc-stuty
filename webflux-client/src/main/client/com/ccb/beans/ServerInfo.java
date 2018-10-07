package com.ccb.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务器信息类
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerInfo {

    /**
     * 服务器Url
     */
    private String url;

}
