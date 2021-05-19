package controllers

import (
	beego "github.com/beego/beego/v2/server/web"
)

type APIController struct {
	beego.Controller
}

type Response struct {
	Code int `json:"code"`
	Msg string `json:"msg"`
}

func (c *APIController) ResponseSuccess(content ...string) {
	msg := ""
	if len(content) != 0 {
		msg = content[0]
	}
	c.Data["json"] = Response{Code: 0, Msg: msg}
	_ = c.ServeJSON()
}

func (c *APIController) Hello() {
	c.Data["json"] = Response{Msg: "Welcome to Kininaru API platform"}
	_ = c.ServeJSON()
}
