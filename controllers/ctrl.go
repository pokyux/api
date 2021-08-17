package controllers

import (
	"os"

	"github.com/gin-gonic/gin"
)

func Stop(ctx *gin.Context) {
	if !auth(ctx) {
		return
	}

	ctx.JSON(200, getJsonResponse(0))

	os.Exit(0)
}
