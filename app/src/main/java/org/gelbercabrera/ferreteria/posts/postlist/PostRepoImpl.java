package org.gelbercabrera.ferreteria.posts.postlist;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;
import org.gelbercabrera.ferreteria.posts.postlist.events.PostsListEvent;

public class PostRepoImpl implements PostsRepo {
    private FirebaseHelper helper;
    private ChildEventListener listener;
    private ValueEventListener likeListener;
    private ChildEventListener friendsListener;
    private EventBus eventBus;
    private final static String DATE = "date";
    private final static String LIKES_NUM = "likesNum";
    private final static String LIKES = "likes";

    public PostRepoImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void likePost(final Post post) {
        likeListener = new ValueEventListener() {
        String emailAuth = helper.getAuthUserEmail().replace(".", "_");
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(LIKES).child(emailAuth).exists() &&
                        (Boolean)dataSnapshot.child(LIKES).child(emailAuth).getValue()){
                    removeLike(dataSnapshot);
                }
                else {
                    addLike(dataSnapshot);
                }
            }

            public void addLike(DataSnapshot dataSnapshot){
                helper.getOnePostReference(post).child(LIKES).child(emailAuth).setValue(true);

                int likesNum = 0;
                if(dataSnapshot.child(LIKES_NUM).toString() != null ) {
                    try {
                        String likes_num =dataSnapshot.child(LIKES_NUM).getValue().toString();
                        likesNum = Integer.parseInt(likes_num) + 1;
                    }catch (NumberFormatException num){}
                }
                helper.getOnePostReference(post).child(LIKES_NUM).setValue(likesNum);
            }

            public void removeLike(DataSnapshot dataSnapshot){
                helper.getOnePostReference(post).child(LIKES).child(emailAuth)
                        .setValue(false);

                int likesNum = 0;
                try {
                    likesNum = Integer.parseInt(dataSnapshot.child(LIKES_NUM).getValue().toString());
                }catch (NumberFormatException num){}

                helper.getOnePostReference(post).child(LIKES_NUM).setValue(likesNum - 1);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        helper.getOnePostReference(post).addListenerForSingleValueEvent(likeListener);
    }

    @Override
    public void destroyListener() {
        listener = null;
    }

    @Override
    public void subscribe() {
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                handlePost(dataSnapshot, PostsListEvent.onPostAdded);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                handlePost(dataSnapshot, PostsListEvent.onPostChanged);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                handlePost(dataSnapshot, PostsListEvent.onPostRemoved);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        helper.getUsersReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               try {
                   helper.getUserPostRefernce(dataSnapshot.getKey()).addChildEventListener(listener);
               }catch (NullPointerException nu){}
           }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        helper.getMyUserReference().child("posts").addChildEventListener(listener);
    }

    @Override
    public void unSubscribe() {

    }

    private void handlePost(DataSnapshot dataSnapshot,final int type){
        final Post post = dataSnapshot.getValue(Post.class);
        postEvent(type, post);

    }

    private void postEvent(int type, Post post) {
        PostsListEvent postListEvent = new PostsListEvent();
        postListEvent.setEventType(type);
        postListEvent.setPost(post);
        eventBus.post(postListEvent);
    }
}
