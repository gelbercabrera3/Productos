package org.gelbercabrera.ferreteria.posts.postlist;


import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.posts.postlist.events.PostsListEvent;

public interface PostsPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void likePost(Post post);
    void onEventMainThread(PostsListEvent event);
}
