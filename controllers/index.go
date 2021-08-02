package controllers

import (
	"api/services"
	"github.com/gin-gonic/gin"
)

func Index(ctx *gin.Context) {
	ctx.JSON(200, getJsonResponse(0, "Welcome to Kininaru's API server."))
}

func Status(ctx *gin.Context) {
	ctx.JSON(200, getJsonResponse(0, services.GetRunTimeString(), services.GetRunTimeSecond()))
}

func Private(ctx *gin.Context) {
	if !auth(ctx) {
		return
	}

	ctx.String(200, "Welcome")
}
