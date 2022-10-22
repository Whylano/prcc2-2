package com.example.prcc2.service;

import com.example.prcc2.dto.PostDetailResDto;
import com.example.prcc2.dto.PostReqDto;
import com.example.prcc2.dto.PostResDto;
import com.example.prcc2.entity.Member;
import com.example.prcc2.entity.Post;
import com.example.prcc2.repository.CommentRepository;
import com.example.prcc2.repository.MemberRepository;
import com.example.prcc2.repository.PostRepository;
import com.example.prcc2.utill.SecurityUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    //1.게시글 작성
    //2.게시글 전체 조회
    //3. 게시글 단건 조회
    //4. 게시글 수정
    //5. 게시글 삭제
    @Transactional
    //(Transactional)안에서 데이터 변경이 필요할 것같다 할때 안에서 써줘야함
    //게시글을 생성하기 위해서는 뭐가 필요할까요?-Post entity안에 생성자를 봅시다!!!- title, content, member
    public void create(PostReqDto dto){//회원가입을 할때 Controller에서 Member객체 타입 dto를 인자값으로 가져옵니다

        //(member가 없으니)현재 로그인한 멤버의 아이를 가져온다.//SecurityUill에게 가져옴
        Long memberId = SecurityUtill.getCurrentMemberId();

        Member member = memberRepository.findById(memberId).orElseThrow(//repository에서 멤버 아이디를 준비합니다..orElseThrow로 해당아이디가 아니면 던져라
                () -> new IllegalArgumentException("해당 아이디를 가진 멤버의 아이디가 없습니다.")
        );

        Post post = dto.toEntity(member);

        postRepository.save(post);
    }
    //게시글을 전부 가져와서 dto로 변환 후 반환
    public List<PostResDto> readAll(){

        List<Post> postList = postRepository.findAll();

        List<PostResDto> postResDtosList = postList.stream()
                .map(post -> new PostResDto(post))
                .collect(Collectors.toList()) ;

        return postResDtosList;
    }
    //게시글 단건 조회
    //게시글 Id를 통해 데이터베이스에서 데이터를 가져온 후 dto로 변환시켜 반환
    public PostDetailResDto readOneById(Long postId){
        
        Post post = postRepository.findById(postId).orElseThrow(  //Null이면 던저라
                ()->new IllegalArgumentException("해당 아이디를 가진 게시글이 존재하지 않습니다.")
        );
        return new PostDetailResDto(post);
    }

    //게시글 업데이트
    //자신의 게시글만 수정할 수 있다.
    //게시글 id + 수정할 정보가 필요하다.
    //1. 데이터베이스에 있는 기존 데이터를 가져온다.
    //2. 기존 데이터를 새로운 데이터로 수정합니다.
    //3. 변경된 데이터를 데이터베이스에 저장한다.
    @Transactional // 데이터 변경이 일어나기 때문에 Transactional을 써준다
    public void update(Long id, PostReqDto dto){

        //1.
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디를 가진 게시글이 존재하지 않습니다.")
        );

        checkOwner(post, SecurityUtill.getCurrentMemberId()); /***로그인한 사람의 아이디를 가져오는 역활****/

        //2.
        post.update(dto.getTitle(),dto.getContent());
        //3.
        postRepository.save(post);

    }
    //게시물 삭제
    //게시글 - 댓긇
    @Transactional
    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 아이디를 가진 게시글이 존재하지 않습니다.")
        );

        checkOwner(post, SecurityUtill.getCurrentMemberId());
        //댓글 삭제
        commentRepository.deleteAllByPostId(post.getId());
        //게시글 삭제
        postRepository.deleteById(id);//게시물을 삭제안한이유
    }
    //현재 이 post(게시물)의 주인이 해당 memberId를 가진 member와 같은지 확인한다.
    private void checkOwner(Post post, Long memberId){
        if(!post.checkOwnerByMemberId(memberId)){
            throw new IllegalArgumentException("회원님이 작성한 글이 아닙니다.");
        }
    }


}
