#bin/bash
sleep 5s
for _ in {1..50} ; do
  curl -X GET --location "http://localhost:8080/hello" \
      -H "Accept: application/json" &
done

