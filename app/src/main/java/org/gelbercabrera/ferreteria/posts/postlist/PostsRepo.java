package org.gelbercabrera.ferreteria.posts.postlist;


import org.gelbercabrera.ferreteria.entities.Post;

public interface PostsRepo {
    void likePost(Post post);
    void destroyListener();
    void subscribe();
    void unSubscribe();
}
