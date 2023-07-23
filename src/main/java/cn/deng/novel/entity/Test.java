package cn.deng.novel.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Deng
 * @since 2023/07/23
 */
@Getter
@Setter
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Boolean test;

    private Integer test2;


}
