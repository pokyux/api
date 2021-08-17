package services

func IsEmpty(str ...string) bool {
	for _, s := range str {
		if len(s) == 0 {
			return true
		}
	}
	return false
}
