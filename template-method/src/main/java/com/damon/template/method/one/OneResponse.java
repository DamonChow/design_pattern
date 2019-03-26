package com.damon.template.method.one;

import com.damon.template.method.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class OneResponse extends Response {

    private String one;

}
