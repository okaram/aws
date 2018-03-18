package aws
import "github.com/aws/aws-sdk-go/service/s3"
import "github.com/aws/aws-sdk-go/aws/session"
import "fmt"

func main() {
	sess, err := session.NewSession()
	if err != nil {
		fmt.Println("Error creating session ", err)
		return
	}
	svc := s3.New(sess)
}
