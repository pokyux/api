package main

import (
	"api/routers"
	"api/services"
	"github.com/gin-gonic/gin"
)

func main() {
	services.Init()

	gin.SetMode(gin.ReleaseMode)
	engine := gin.Default()

	routers.InitRouter(engine)

	err := engine.Run("0.0.0.0:23333")
	if err != nil {
		panic(err)
	}
}
