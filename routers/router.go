package routers

import (
	"api/controllers"
	beego "github.com/beego/beego/v2/server/web"
)

func init() {
    beego.Router("/", &controllers.APIController{}, "GET:Hello")
    beego.Router("/markdown-to-html", &controllers.APIController{}, "POST:MarkdownToHTML")
}
