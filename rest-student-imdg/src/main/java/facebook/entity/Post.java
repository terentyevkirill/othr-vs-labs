package facebook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Post implements Serializable {
    @XmlAttribute
    private int postId;
    private int authorId;
    private String text;
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Date publishedAt;

    public Post() {
    }

    public Post(int postId, int authorId, String text) {
        this.postId = postId;
        this.authorId = authorId;
        this.text = text;
        this.publishedAt = new Date();
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;

        Post post = (Post) o;

        return getPostId() == post.getPostId();
    }

    @Override
    public int hashCode() {
        return getPostId();
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + authorId +
                ", text='" + text + '\'' +
                ", publishedAt=" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(publishedAt) +
                '}';
    }
}
