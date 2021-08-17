package throw

type ApiError struct {
	Reason string
}

func (e *ApiError) Error() string {
	return e.Reason
}

func MissingParam() *ApiError {
	return &ApiError{"Missing parameter!"}
}

func OldSecretError() *ApiError {
	return &ApiError{"Old Secret Error!"}
}
