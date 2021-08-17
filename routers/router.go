package routers

import (
	"api/controllers"
	"github.com/gin-gonic/gin"
)

func InitRouter(engine *gin.Engine) {
	InitNormal(engine)
	InitSecret(engine)
}

func InitNormal(engine *gin.Engine) {
	engine.GET("/", controllers.Index)
	engine.GET("/status", controllers.Status)
}

func InitSecret(engine *gin.Engine) {
	engine.GET("/private", controllers.Private)
	engine.GET("/stop", controllers.Stop)
}
