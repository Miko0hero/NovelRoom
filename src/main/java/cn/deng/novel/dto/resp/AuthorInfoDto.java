package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Deng
 * @date 2023/8/1
 * @description 作家信息Dto
 */
@Data
@Builder
public class AuthorInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String penName;

    private Integer status;
}
