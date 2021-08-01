package controllers

import "github.com/gin-gonic/gin"

func Index(ctx *gin.Context) {
	ctx.JSON(200, getJsonResponse(0, "Welcome to Kininaru's API server."))
}
