package controllers

import (
	beego "github.com/beego/beego/v2/server/web"
)

type APIController struct {
	beego.Controller
}

type Response struct {
	Code int
	Msg string
}

func (c *APIController) Hello() {
	c.Data["json"] = Response{Msg: "Welcome to Kininaru API platform"}
	_ = c.ServeJSON()
}
