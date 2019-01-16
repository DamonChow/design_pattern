package com.damon.mediator.simple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：信息
 *
 * @author Damon
 * @since 2019-01-16 14:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String msg;
}
