package com.korea.mBoard.dto;

import com.korea.mBoard.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String time;
    private Integer views;

    // Entity → DTO 변환
    public static PostDTO fromEntity(Post post) {
        return new PostDTO(
            post.getId(),
            post.getTitle(),
            post.getAuthor(),
            post.getDescription(),
            post.getTime(),
            post.getViews()
        );
    }

    // DTO → Entity 변환
    public Post toEntity() {
        Post post = new Post();
        post.setId(this.id);  // id는 보통 DB에서 생성하지만, 필요 시 세팅
        post.setTitle(this.title);
        post.setAuthor(this.author);
        post.setDescription(this.description);
        post.setTime(this.time);
        post.setViews(this.views != null ? this.views : 0);
        return post;
    }
}
