package main

import (
	"os"

	"api/controllers"
	"api/routers"
	"api/services"
	"github.com/gin-gonic/gin"
)

func main() {
	if len(os.Args) < 3 {
		panic("No enough args")
	}

	services.Init()
	controllers.Init(controllers.ControllerConfig{
		OldSecret: os.Args[1],
		NewSecret: os.Args[2],
	})

	gin.SetMode(gin.ReleaseMode)
	engine := gin.Default()

	routers.InitRouter(engine)

	err := engine.Run("0.0.0.0:23333")
	if err != nil {
		panic(err)
	}
}
