package controllers

import (
	"net/http"

	"api/services"
	"api/throw"
	"github.com/gin-gonic/gin"
)

type ControllerConfig struct {
	OldSecret string
	NewSecret string
}

var config ControllerConfig

func Init(conf ControllerConfig) {
	if services.IsEmpty(conf.OldSecret, conf.NewSecret) {
		panic(throw.MissingParam())
	}

	// end the prev api progress
	_, _ = http.Get("http://localhost:23333/stop")

	config = conf
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

func auth(ctx *gin.Context) bool {
	authorization := ctx.Request.Header.Get("Authorization")
	if len(authorization) == 0 || authorization != config.NewSecret {
		ctx.String(403, "Access denied")
		return false
	}
	return true
}
