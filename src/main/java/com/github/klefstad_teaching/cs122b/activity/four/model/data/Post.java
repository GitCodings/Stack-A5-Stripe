package com.github.klefstad_teaching.cs122b.activity.four.model.data;

public class Post
{
    private Long userId;
    private Long id;
    private String title;
    private String body;

    public Long getUserId()
    {
        return userId;
    }

    public Post setUserId(Long userId)
    {
        this.userId = userId;
        return this;
    }

    public Long getId()
    {
        return id;
    }

    public Post setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public Post setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getBody()
    {
        return body;
    }

    public Post setBody(String body)
    {
        this.body = body;
        return this;
    }
}
