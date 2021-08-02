package controllers

import (
	"github.com/gin-gonic/gin"
)

func Init(key string) {
	privateKey = key
}

type Response struct {
	Code int         `json:"code"`
	Msg  string      `json:"msg"`
	Data interface{} `json:"data"`
}

func getJsonResponse(code int, data ...interface{}) Response {
	var resp Response

	switch len(data) {
	default:
		panic("Too many arguments for getJsonResponse")
	case 2:
		resp.Data = data[1]
		fallthrough
	case 1:
		msg, _ := data[0].(string)
		resp.Msg = msg
		fallthrough
	case 0:
		resp.Code = code
	}

	return resp
}

var privateKey string

func auth(ctx *gin.Context) bool {
	authorization := ctx.Request.Header.Get("Authorization")
	if len(authorization) == 0 || authorization != privateKey {
		ctx.String(403, "Access denied")
		return false
	}
	return true
}
