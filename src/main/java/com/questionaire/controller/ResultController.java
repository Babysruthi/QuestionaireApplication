package com.questionaire.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.questionaire.dto.Result;
import com.questionaire.entity.ResultEntity;
import com.questionaire.exception.NotFoundException;
import com.questionaire.exception.QuizIdNotFoundException;
import com.questionaire.exception.StudentIdNotFoundException;
import com.questionaire.exception.SubjectNotFoundException;
import com.questionaire.exception.ServiceException;
import com.questionaire.service.ResultService;
import com.questionaire.util.ResponseUtil;

@RestController
@RequestMapping("/api/result")
@CrossOrigin("http://localhost:4200")
public class ResultController {

	@Autowired
	private ResultService resultService;

	@PostMapping("/{rollNo}/{code}/{id}")
	public ResponseEntity<Response> addResult(@PathVariable("rollNo") Long rollNo,
			@PathVariable("code") String code, @PathVariable("id") Long id, @RequestBody Result result) {
		Long autoId = 0l;
		ResponseEntity<Response> responseEntity = null;
		try {
			autoId = resultService.addResult(rollNo, code, id, result);
			responseEntity = ResponseUtil.getResponse(200,
					"Result is added to the student " + rollNo + " in quiz " + id + " for subject " +code + " ",
					autoId);
		} catch (NotFoundException e) {

			if (e instanceof QuizIdNotFoundException || e instanceof StudentIdNotFoundException
					|| e instanceof SubjectNotFoundException) {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		} catch (ServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getResult(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		List<ResultEntity> result;
		try {
			result = resultService.getResult(id);
			responseEntity = ResponseUtil.getResponse(200, "Fetched result Details in quiz " + id, result);
		} catch (NotFoundException e) {

			if (e instanceof QuizIdNotFoundException) {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		} catch (ServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		}
		return responseEntity;
	}
	
	@GetMapping("/{rollNo}/{id}")
	public ResponseEntity<Response> getResultByRollNo(@PathVariable("rollNo") Long rollNo,@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		List<ResultEntity> result=null;
		try {
			result = resultService.getResultByRollNo(rollNo,id);
			responseEntity = ResponseUtil.getResponse(200, "Fetched result Details in quiz " + id, result);
		} catch (NotFoundException e) {

			if (e instanceof QuizIdNotFoundException) {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		} catch (ServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		}
		return responseEntity;
	}
	
	@GetMapping("/rollNo/{rollNo}/quiz/{quizIds}")
	public ResponseEntity<Response> getResultByQuizId(@PathVariable("rollNo") Long rollNo,@PathVariable("quizIds") List<Long> quizIds) {
		ResponseEntity<Response> responseEntity = null;
		List<Integer> result=new ArrayList<>();
		try {
			result = resultService.getResultByQuizId(rollNo,quizIds);
			responseEntity = ResponseUtil.getResponse(200, "Fetched result Details in quiz ", result);
		}  catch (ServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		}
		return responseEntity;
	}
	
	@GetMapping("/rollNo/{rollNo}/{code}")
	public ResponseEntity<Response> getResultByCode(@PathVariable("rollNo") Long rollNo,@PathVariable("code") String code) {
		ResponseEntity<Response> responseEntity = null;
		List<ResultEntity> result=null;
		try {
			result = resultService.getResultByCode(rollNo,code);
			responseEntity = ResponseUtil.getResponse(200, "Fetched result Details in quiz " + code, result);
		} catch (NotFoundException e) {

			if (e instanceof QuizIdNotFoundException) {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		} catch (ServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		}
		return responseEntity;
	}
}
