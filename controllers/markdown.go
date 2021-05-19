package controllers

import (
	"github.com/gomarkdown/markdown"
)

func (c *APIController) MarkdownToHTML() {
	md := c.Ctx.Request.Form.Get("markdown")
	html := markdown.ToHTML([]byte(md), nil, nil)
	c.ResponseSuccess(string(html))
}
