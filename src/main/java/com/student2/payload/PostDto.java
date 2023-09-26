package com.student2.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 2, message = "post title should be al least 2 character")
    private  String title;
    @NotEmpty
    @Size(min = 4, message = "post description should be al least 2 character")
    private  String description;
    @NotEmpty
    @Size(min = 6, message = "post content should be al least 2 character")
    private String content;
}
