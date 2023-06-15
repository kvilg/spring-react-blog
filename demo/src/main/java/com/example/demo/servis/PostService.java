package com.example.demo.servis;

import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.model.moduleHttp.ExceptionModel;
import com.example.demo.repo.PostRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Service
public class PostService {

    @Autowired
    private PostRepo dataPost;

    @Autowired
    private UserRepo dataUser;
    @Autowired
    private AuthRestService service;


    @Transactional
    public ResponseEntity<?> createPost(String logo, String text, String img, String token) {
        try{
            User user = service.getUserByToken(token);
            if(user == null){
                return new ResponseEntity<>(new ExceptionModel("Неудалось авторизоваться"), HttpStatus.UNAUTHORIZED);
            }
            Post post = new Post(logo,text,img,user);
            user.addPost(post);
            dataUser.save(user);
            dataPost.save(post);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(new ExceptionModel("Ошибка записи картинки"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            return new ResponseEntity<>(new ExceptionModel("Ошибка создании поста"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getAllPost(Long id) {
        try{
            Post[] postArr = (Post[]) dataUser.getById(id).getPosts().toArray();

//            for (int i = 0; i < postArr.length -1; i++) {
//                if(postArr[i].getCreateDate().before(postArr[i+1].getCreateDate())){
//                    Post ref = postArr[i];
//                    postArr[i] = postArr[i+1];
//                    postArr[i+1] = ref;
//                }
//            }
            return new ResponseEntity<>(postArr,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(new ExceptionModel(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
