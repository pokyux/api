package routers

import (
	"api/controllers"
	"github.com/gin-gonic/gin"
)

func InitRouter(engine *gin.Engine) {
	engine.GET("/", controllers.Index)
	engine.GET("/status", controllers.Status)
	engine.GET("/private", controllers.Private)
}
