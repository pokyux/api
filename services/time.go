package services

import (
	"fmt"
	"time"
)

var startTimestamp int64

func initTime() {
	startTimestamp = time.Now().Unix()
}

func GetRunTimeString() string {
	s := time.Now().Unix() - startTimestamp

	d := s / (3600 * 24)
	s %= 3600 * 24

	h := s / 3600
	s %= 3600

	m := s / 60
	s %= 60

	return fmt.Sprintf("Server has been running for %dd %dh %dm %ds", d, h, m, s)
}
