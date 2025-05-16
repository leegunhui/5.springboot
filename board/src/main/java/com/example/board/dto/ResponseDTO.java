package com.example.board.dto;

import java.util.List;

public class ResponseDTO <T>{
	private String error;
	private List <T> data;
}
