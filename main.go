package main

import (
	"api/routers"
	"github.com/gin-gonic/gin"
)

func main() {
	gin.SetMode(gin.ReleaseMode)
	engine := gin.Default()

	routers.InitRouter(engine)

	err := engine.Run("0.0.0.0:23333")
	if err != nil {
		panic(err)
	}
}
