package com.project.jw.controller;


import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.project.jw.config.CloudinaryConfig;
import com.project.jw.model.Videos;
import com.project.jw.repository.UserRepository;
import com.project.jw.repository.VideosRepository;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="users/videos")
public class VideosController
{
    @Autowired
    VideosRepository videosRepository;

    @Autowired
    CloudinaryConfig cloudc;


    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Iterable <Videos> findStatus(){

        return videosRepository.findByStatus(true);
    }

    @GetMapping("/all")
    public Iterable <Videos> findAll(){

        return videosRepository.findAll();
    }


//    @PutMapping("/upload/{video_id}")
//    public Videos updateVideo(@PathVariable(value = "video_id") Long video_id,
//                              @Valid @RequestBody Videos videoss) {
//
//        Videos vid = videosRepository.findById(video_id)
//                .orElseThrow(() -> new ResourceNotFoundException("Videos"+ "video_id"+ video_id));
//
//        vid.setTitle(videoss.getTitle());
//        vid.setDescription(videoss.getDescription());
//        vid.setRegion(videoss.getRegion());
//        vid.setCategory(videoss.getCategory());
//
//        Videos updatedVideo = videosRepository.save(vid);
//        return updatedVideo;
//    }



    @PutMapping("/upload/{id}/{video_id}")
    public Videos updateVideo(@PathVariable(value = "video_id") Long video_id,
                              @PathVariable(value = "id") Long id,
                              @Valid @RequestBody Videos videoss) {

        return userRepository.findById(id).map(savevid->{

            Videos vid = videosRepository.findById(video_id)
                .orElseThrow(() -> new ResourceNotFoundException("Videos"+ "video_id"+ video_id));

        vid.setTitle(videoss.getTitle());
        vid.setDescription(videoss.getDescription());
        vid.setRegion(videoss.getRegion());
        vid.setCategory(videoss.getCategory());
        vid.setUser(savevid);

        Videos updatedVideo = videosRepository.save(vid);

        return updatedVideo;


        }).orElseThrow(()-> new ResourceNotFoundException("id"+id +"not found"));

    }


    @PutMapping("/admin/upload/{video_id}")
    public Videos updateAdminVideo(@PathVariable(value = "video_id") Long video_id,
                                   @Valid @RequestBody Videos videoss) {

        Videos vid = videosRepository.findById(video_id)
                .orElseThrow(() -> new ResourceNotFoundException("Videos"+ "video_id"+ video_id));

        vid.setPrice(videoss.getPrice());
        vid.setStatus(videoss.isStatus());

        Videos updatedVideo = videosRepository.save(vid);
        return updatedVideo;
    }


    @DeleteMapping("/delete/{video_id}")
    public void deleteVideos(@PathVariable long video_id) {
        videosRepository.deleteById(video_id);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam(value="upload", required=true) MultipartFile file){

        try {
            File f= Files.createTempFile("temp", file.getOriginalFilename()).toFile();
            file.transferTo(f);
            Transformation transformation =
                    new Transformation()
                            .chain().overlay("jw_logo").opacity(30).flags("relative").width(0.5);
            Transformation transformation2 =
                    new Transformation().height(100).crop("scale");
            Map options = ObjectUtils.asMap(
                    "resource_type", "video", "eager", Arrays.asList(transformation, transformation2),
                    "eager_async", true
            );
//            Map options = ObjectUtils.asMap(
//                    "resource_type", "video", "transformation", transformation
//            );
            Map uploadResult = cloudc.upload(f, options);
            JSONObject json=new JSONObject(uploadResult);
            String url=json.getString("url");
            Videos videos = new Videos();
            videos.setUrl(url);



//            Map uploadResults = cloudc.upload(f, ObjectUtils.asMap("resource_type", "video", "transformation", new Transformation().height(300).crop("scale")));
            Map uploadResults = cloudc.upload(f, ObjectUtils.asMap("resource_type", "video"));
            JSONObject jsons=new JSONObject(uploadResults);
            String downloadUrl=jsons.getString("url");
            String newDownloadUrl = new String();
            String attach = "fl_attachment/";

            for (int i = 0; i < downloadUrl.length(); i++) {

                newDownloadUrl += downloadUrl.charAt(i);

                if (i == 47) {

                    newDownloadUrl += attach;
                }
            }
            videos.setStatus(false);

            videos.setDownloadUrl(newDownloadUrl);
            videosRepository.save(videos);
            return new ResponseEntity<Videos>(videos, HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/uploadLain")
    public ResponseEntity<?> uploadLain(@RequestParam(value="upload", required=true) MultipartFile file){

        try {
            File f= Files.createTempFile("temp", file.getOriginalFilename()).toFile();
            file.transferTo(f);
            Transformation transformation =
                    new Transformation()
                            .chain().overlay("jw_logo").opacity(30).flags("relative").width(0.5);
            Map options = ObjectUtils.emptyMap();
            Map uploadResult = cloudc.upload(f, options);
            JSONObject json=new JSONObject(uploadResult);
            String url=json.getString("url");
            Videos videos = new Videos();
            videos.setUrl(url);



            Map uploadResults = cloudc.upload(f, ObjectUtils.emptyMap());
            JSONObject jsons=new JSONObject(uploadResults);
            String downloadUrl=jsons.getString("url");
            String newDownloadUrl = new String();
            String attach = "fl_attachment/";

            for (int i = 0; i < downloadUrl.length(); i++) {

                newDownloadUrl += downloadUrl.charAt(i);

                if (i == 47) {

                    newDownloadUrl += attach;
                }
            }
            videos.setStatus(false);

            videos.setDownloadUrl(newDownloadUrl);
            videosRepository.save(videos);
            return new ResponseEntity<Videos>(videos, HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }


}
