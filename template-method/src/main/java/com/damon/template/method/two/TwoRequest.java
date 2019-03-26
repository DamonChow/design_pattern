package com.damon.template.method.two;

import com.damon.template.method.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-25 17:51
 */
@Data
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TwoRequest extends Request {

    private Long id;
}
