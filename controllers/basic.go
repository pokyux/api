package controllers

type Response struct {
	Code int         `json:"code"`
	Msg  string      `json:"msg"`
	Data interface{} `json:"data"`
}

func getJsonResponse(code int, data ...interface{}) Response {
	var resp Response

	switch len(data) {
	default:
		panic("Too many arguments for getJsonResponse")
	case 2:
		resp.Data = data[1]
		fallthrough
	case 1:
		msg, _ := data[0].(string)
		resp.Msg = msg
		fallthrough
	case 0:
		resp.Code = code
	}

	return resp
}
