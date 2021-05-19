package main

import (
	"api/routers"
	_ "api/routers"
	beego "github.com/beego/beego/v2/server/web"
)

func main() {
	beego.InsertFilter("/*", beego.BeforeRouter, routers.Cors)
	beego.Run()
}

